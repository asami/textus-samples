# 13 / 13a Observability Article Material

Date: 2026-05-12

This note collects article material for the CNCF observability samples:

- `samples/13-observability-jaeger`
- `samples/13.a-observability-stack-lab`

The intended article theme is: CNCF keeps its own structured observability model
as the source of truth, and OpenTelemetry is an export boundary.

## Story Line

The two samples should be introduced as two steps.

`13-observability-jaeger` is the smallest proof. It starts only Jaeger
all-in-one and exports CNCF traces directly to Jaeger over OTLP HTTP. This is
the quickest way to verify that CNCF action execution and CallTree-derived span
facts are visible outside CNCF.

`13.a-observability-stack-lab` is the standard observability-stack lab. CNCF
sends OTLP HTTP only to the OpenTelemetry Collector. The Collector routes traces
to Jaeger and metrics to Prometheus. Grafana reads Prometheus and may also use
Jaeger as a trace datasource. This sample is closer to an operational topology,
but it is intentionally still a lab, not production hardening.

The article should emphasize the difference:

- Sample 13 answers: "Can CNCF export a trace to an external backend with the
  minimum moving parts?"
- Sample 13a answers: "Can CNCF feed a normal Collector-centered stack for
  traces and metrics?"

## CNCF Observability Comes First

Both samples should ask the reader to inspect CNCF first, before opening Jaeger,
Prometheus, or Grafana.

This order is deliberate. CNCF-native observability remains authoritative for:

- CallTree
- action / UoW / space / I/O execution facts
- structured diagnostics
- `Conclusion` detail codes and previous-chain source errors
- diagnostic payload summaries and payload references
- runtime metrics snapshots
- Job / Task / Saga context when present

OpenTelemetry receives a projection of those facts. It is useful for integration
with common tools, but it is not CNCF's primary diagnostic store.

## Safety Policy To Mention

The article should explicitly mention the payload policy:

- raw request / response / result payload bodies are not exported by default;
- CNCF exports compact summary/reference metadata instead;
- redaction and confidentiality filtering happen before display, storage, or
  export;
- export failures are non-fatal and do not fail the business operation.

This is important because observability data can easily become a second
database of sensitive application data. These samples are meant to show the
boundary, not to encourage raw payload export.

## Sample 13 Details

Directory:

```text
samples/13-observability-jaeger
```

Purpose:

- minimal OB-06 proof;
- one Jaeger all-in-one container;
- CNCF exports traces directly to Jaeger using OTLP HTTP;
- metrics export is disabled.

Configuration:

```hocon
textus.observability.otel.enabled = true
textus.observability.otel.endpoint = "http://127.0.0.1:4318"
textus.observability.otel.traces.enabled = true
textus.observability.otel.metrics.enabled = false
textus.debug.calltree = true
textus.runtime.component = minimal
```

Docker service:

- `jaegertracing/all-in-one:1.57`
- `COLLECTOR_OTLP_ENABLED=true`
- ports:
  - `16686` Jaeger UI
  - `4318` OTLP HTTP receiver

Run flow:

```bash
bash start-jaeger.sh
bash run-server.sh
bash run-operation.sh
```

The server runs the `01.c-builtin-and-help-lab` minimal component with the
sample-specific `.textus.conf`.

`run-operation.sh` uses the CNCF client operation path:

```bash
client minimal.main.hello --baseurl http://127.0.0.1:19613
```

The script should not call `/form-api`. CNCF client operation execution uses
the canonical REST route internally.

Expected operation output:

```text
Hello CNCF
```

Inspection order:

1. CNCF Observability UI:
   <http://127.0.0.1:19613/web/system/admin/observability>
2. Jaeger UI:
   <http://127.0.0.1:16686>
3. Search Jaeger for service `goldenport-cncf`.

Article screenshot candidates:

- CNCF Observability UI showing local diagnostics.
- Jaeger trace search result for `goldenport-cncf`.
- Jaeger trace detail showing CNCF action span metadata.

External tool verification:

- Jaeger is used to confirm trace export.
- In Jaeger, search service `goldenport-cncf`.
- Open the trace created by `minimal.main.hello`.
- Confirm that the CNCF action span is present.
- Inspect span attributes for CNCF metadata such as component, service,
  operation, and compact payload summary/reference facts.
- Confirm that raw payload bodies are not exported by default.

## Sample 13a Details

Directory:

```text
samples/13.a-observability-stack-lab
```

Purpose:

- full OB-06 observability stack lab;
- CNCF sends OTLP HTTP to the Collector only;
- Collector routes traces to Jaeger and metrics to Prometheus;
- Grafana is available as the dashboard frontend.

Configuration:

```hocon
textus.observability.otel.enabled = true
textus.observability.otel.endpoint = "http://127.0.0.1:4318"
textus.observability.otel.traces.enabled = true
textus.observability.otel.metrics.enabled = true
textus.debug.calltree = true
textus.runtime.component = minimal
```

Docker services:

- OpenTelemetry Collector `otel/opentelemetry-collector-contrib:0.100.0`
- Jaeger all-in-one `jaegertracing/all-in-one:1.57`
- Prometheus `prom/prometheus:v2.52.0`
- Grafana `grafana/grafana:10.4.2`

Ports:

- CNCF Observability UI:
  <http://127.0.0.1:19614/web/system/admin/observability>
- CNCF Metrics UI:
  <http://127.0.0.1:19614/web/system/admin/observability/metrics>
- Collector OTLP HTTP:
  <http://127.0.0.1:4318>
- Jaeger UI:
  <http://127.0.0.1:16686>
- Prometheus:
  <http://127.0.0.1:9090>
- Grafana:
  <http://127.0.0.1:3000>

Grafana credentials:

```text
admin / admin
```

Collector routing:

```yaml
traces:
  receivers: [otlp]
  processors: [batch]
  exporters: [otlp/jaeger, debug]

metrics:
  receivers: [otlp]
  processors: [batch]
  exporters: [prometheus, debug]
```

Prometheus scrapes the Collector's Prometheus exporter:

```yaml
scrape_configs:
  - job_name: otel-collector
    static_configs:
      - targets: ["collector:8889"]
```

Run flow:

```bash
bash start-stack.sh
bash run-server.sh
bash run-operation.sh
bash export-metrics.sh
```

`run-operation.sh` uses the CNCF client operation path:

```bash
client minimal.main.hello --baseurl http://127.0.0.1:19614
```

`export-metrics.sh` is separate from operation execution. It is a demo helper
that asks the live CNCF runtime to export the current metrics snapshot. In the
article, present it as a verification step for metrics export, not as the way
normal application operations are executed.

Inspection order:

1. CNCF Observability UI:
   <http://127.0.0.1:19614/web/system/admin/observability>
2. CNCF Metrics UI:
   <http://127.0.0.1:19614/web/system/admin/observability/metrics>
3. Jaeger:
   <http://127.0.0.1:16686>
4. Prometheus:
   <http://127.0.0.1:9090>
5. Grafana:
   <http://127.0.0.1:3000>

Prometheus query candidates:

```promql
cncf_web_request_requests_count
cncf_action_execution_executions_count
```

Article screenshot candidates:

- CNCF Metrics UI showing runtime metrics scopes.
- Jaeger trace for the sample action.
- Prometheus query result for CNCF metrics.
- Grafana datasource or dashboard screen.

External tool verification:

- Jaeger:
  - search for service `goldenport-cncf`;
  - open the trace for `minimal.main.hello`;
  - check action span attributes and CNCF correlation attributes.
- Prometheus:
  - confirm that metrics come from the Collector's Prometheus exporter;
  - query `cncf_web_request_requests_count`;
  - query `cncf_action_execution_executions_count`;
  - confirm the series appear only after CNCF has run the operation and metrics
    export has been triggered.
- Grafana:
  - log in with `admin / admin`;
  - open Explore;
  - use the preconfigured Prometheus datasource to query CNCF metric series;
  - optionally use the preconfigured Jaeger datasource to search traces for
    `goldenport-cncf`;
  - treat Grafana as the operator-facing dashboard/query frontend, while CNCF
    remains the local source of structured diagnostic truth.

## Explanation Points

### Why split 13 and 13a?

The minimal Jaeger sample keeps the first experience small. It proves traces
without forcing readers to understand Collector, Prometheus, and Grafana.

The 13a lab introduces the production-like topology: CNCF exports to the
Collector, and backend-specific routing is owned by Collector configuration.
This keeps CNCF independent of Jaeger, Prometheus, and Grafana APIs.

### Why start CNCF server and operation separately?

The server must remain alive so operators can inspect CNCF's Web UI, metrics,
and external observability tools after one or more operations. A single
test-style script that starts a server, runs an operation, and exits hides that
runtime inspection step.

The article should show three terminals:

1. observability backend;
2. CNCF server;
3. operation / export trigger.

### Why use CNCF client for operation execution?

The sample operation is intentionally invoked through CNCF client:

```bash
cncf client minimal.main.hello
```

This demonstrates the same server/client operation path used by application
clients. The script should not know Web-only paths such as `/form-api`.

### What should readers verify?

For sample 13:

- CNCF operation returns `Hello CNCF`;
- CNCF Observability UI has local execution facts;
- Jaeger has a trace for service `goldenport-cncf`.

For sample 13a:

- CNCF operation returns `Hello CNCF`;
- CNCF Observability UI has local execution facts;
- CNCF Metrics UI shows runtime metrics;
- Jaeger has trace data;
- Prometheus has CNCF metric series;
- Grafana can read the Prometheus datasource.

## Article Outline Draft

1. Problem statement:
   CNCF needs internal observability and external observability integration.
2. Design stance:
   CNCF-native observability is authoritative; OpenTelemetry is export.
3. Minimal proof:
   run `13-observability-jaeger`.
4. Local-first inspection:
   check CNCF Observability UI.
5. External trace inspection:
   check Jaeger.
6. Full stack lab:
   run `13.a-observability-stack-lab`.
7. Metrics path:
   export CNCF runtime metrics and inspect Prometheus/Grafana.
8. Safety:
   no raw payload export by default; summaries/references only.
9. Future work:
   production retention, cleanup, authorization hardening, richer dashboards,
   and OpenTelemetry log export.

## Caveats / Follow-up Notes

- `export-metrics.sh` currently uses the Web/Form API surface as a demo trigger
  for the built-in metrics operation. This is acceptable for a lab helper, but
  article wording should avoid presenting it as the primary application
  operation path.
- `run-operation.sh` should remain on the CNCF client operation path and should
  not embed `/form-api`.
- The samples use local ports that can conflict if both 13 and 13a stacks are
  running at the same time. Run one sample at a time unless ports are changed.
- These samples are demos. Production use still needs explicit policy for
  retention, cleanup, authorization, storage destination, and backend operation.
