package org.sample.viewcache

import org.goldenport.Consequence
import org.goldenport.record.Record
import org.goldenport.cncf.directive.Query
import org.goldenport.cncf.entity.view.{Browser, ViewBuilder, ViewCollection}
import org.goldenport.cncf.metrics.EntityAccessMetricsRegistry
import org.simplemodeling.model.datatype.EntityId

object ViewCacheDemo:
  private val QueryChunkSize = 2

  private final case class PersonSummary(
    id: String,
    name: String,
    city: String,
    title: String
  )

  private final case class QueryStats(
    var invocations: Int = 0
  )

  private val People = Vector(
    PersonSummary("tokyo-sales-entity-person-1742198400000-aa01", "Alice", "Tokyo", "Reader"),
    PersonSummary("tokyo-sales-entity-person-1742198400000-aa02", "Bella", "Tokyo", "Analyst"),
    PersonSummary("tokyo-sales-entity-person-1742198400000-aa03", "Chloe", "Tokyo", "Planner"),
    PersonSummary("tokyo-sales-entity-person-1742198400000-aa04", "Diana", "Tokyo", "Designer"),
    PersonSummary("tokyo-sales-entity-person-1742198400000-aa05", "Emma", "Tokyo", "Lead"),
    PersonSummary("tokyo-sales-entity-person-1742198400000-bb01", "Bob", "Osaka", "Editor")
  )

  def main(args: Array[String]): Unit =
    EntityAccessMetricsRegistry.shared.clear()
    val stats = QueryStats()
    val browser = _browser(stats)

    println(s"queryChunkSize=$QueryChunkSize")
    println()

    _show_page(browser, stats, "page 1", 0, 2)
    _show_page(browser, stats, "page 2", 1, 2)
    _show_page(browser, stats, "page 3", 2, 2)
    _show_small_query(browser, stats)
    println()
    println("--- metrics")
    _metrics_lines().foreach(println)

  private def _browser(stats: QueryStats): Browser[PersonSummary] =
    val builder = new ViewBuilder[PersonSummary] {
      def build(id: EntityId): Consequence[PersonSummary] =
        People.find(_.id == id.value) match
          case Some(v) => Consequence.success(v)
          case None => Consequence.failure(s"Person not found: ${id.value}")
    }
    val collection = new ViewCollection[PersonSummary](
      builder = builder,
      queryChunkSize = QueryChunkSize,
      metricsName = "person-summary",
      metricsRegistry = Some(EntityAccessMetricsRegistry.shared)
    )
    Browser.from(collection, q => _query_backend(q, stats))

  private def _query_backend(
    q: Query[_],
    stats: QueryStats
  ): Consequence[Vector[PersonSummary]] = {
    stats.invocations += 1
    val filtered = People.filter(v => Query.matches(q, v))
    val sliced = Query.sliceValues(filtered, q.offset, q.limit)
    Consequence.success(sliced)
  }

  private def _show_page(
    browser: Browser[PersonSummary],
    stats: QueryStats,
    label: String,
    offset: Int,
    limit: Int
  ): Unit = {
    println(s"--- $label")
    val result = browser.query(
      Query.plan(
        Record.data("city" -> "Tokyo"),
        limit = Some(limit),
        offset = Some(offset)
      )
    ).TAKE
    result.foreach(v => println(s"${v.name} | ${v.city} | ${v.title}"))
    println(s"backend-query-count=${stats.invocations}")
    println()
  }

  private def _show_small_query(
    browser: Browser[PersonSummary],
    stats: QueryStats
  ): Unit = {
    println("--- small unbounded query")
    val query = Query(Record.data("city" -> "Osaka"))
    val first = browser.query(query).TAKE
    val second = browser.query(query).TAKE
    println(s"first=${first.map(_.name).mkString(",")}")
    println(s"second=${second.map(_.name).mkString(",")}")
    println(s"backend-query-count=${stats.invocations}")
  }

  private def _metrics_lines(): Vector[String] =
    EntityAccessMetricsRegistry.shared.snapshot().map { x =>
      s"${x.name} | entity=${x.entity.getOrElse("-")} | count=${x.count}"
    }
