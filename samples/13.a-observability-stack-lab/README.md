# 13.a-observability-stack-lab

## Purpose

This sample is the full OB-06 observability stack lab.

CNCF sends OTLP HTTP to the OpenTelemetry Collector only. The Collector routes:

- traces to Jaeger;
- metrics to Prometheus;
- Grafana reads Prometheus and can also use Jaeger as a trace datasource.

## Ports

- CNCF Observability UI: <http://127.0.0.1:19614/web/system/admin/observability>
- CNCF Metrics UI: <http://127.0.0.1:19614/web/system/admin/observability/metrics>
- Collector OTLP HTTP: <http://127.0.0.1:4318>
- Jaeger UI: <http://127.0.0.1:16686>
- Prometheus: <http://127.0.0.1:9090>
- Grafana: <http://127.0.0.1:3000>
- Grafana CNCF dashboard: <http://127.0.0.1:3000/d/cncf-observability/cncf-observability>

Grafana default credentials are `admin` / `admin`.

## Run

Terminal 1: start the observability backend stack.

```bash
bash start-stack.sh
```

Terminal 2: start the CNCF sample server.

```bash
bash run-server.sh
```

Terminal 3: run a sample operation through the CNCF client, then export metrics
from the same live CNCF runtime.

```bash
bash run-operation.sh
bash export-metrics.sh
```

Inspect CNCF first:

- open CNCF Observability UI:
  <http://127.0.0.1:19614/web/system/admin/observability>
- open CNCF Metrics UI:
  <http://127.0.0.1:19614/web/system/admin/observability/metrics>
- confirm CNCF-local action, calltree, diagnostics, and metrics before checking
  exported data.

After the CNCF-local check, inspect the external stack:

- open Jaeger and search for service `goldenport-cncf`;
- open a Jaeger trace and confirm the exported CNCF action span for
  `minimal.main.hello`;
- open Prometheus and query `cncf_web_request_requests_count_total` or
  `cncf_action_execution_executions_count_total`;
- confirm Prometheus receives CNCF runtime metrics through the Collector, not
  directly from CNCF;
- open Grafana and use Explore with the preconfigured Prometheus datasource to
  query CNCF metrics;
- open the provisioned `CNCF Observability` dashboard to inspect collector
  health, CNCF action/web counters, and discovered CNCF metric families;
- optionally use the preconfigured Jaeger datasource in Grafana Explore to look
  up the same `goldenport-cncf` traces.

The dashboard is provisioned from
`grafana/provisioning/dashboards/cncf-observability.json`. Stack health panels
show data as soon as Prometheus is scraping the Collector. CNCF metric panels
show data after `run-operation.sh` and `export-metrics.sh` have populated the
runtime metrics snapshot and exported it through OTLP.

## Jaeger Usage

Jaeger is used to inspect exported trace projection. CNCF remains the
authoritative source for action, calltree, and diagnostic facts.

Open Jaeger:

```text
http://127.0.0.1:16686
```

Search for traces:

- select service `goldenport-cncf`;
- keep operation as `all` for the first check;
- set a recent lookback window such as `Last 1 Hour`;
- click `Find Traces`.

Open the trace created by the sample operation and check:

- the action span for `minimal.main.hello`;
- CNCF span attributes for component, service, operation, and outcome;
- span timing as the exported trace view of the same execution observed in the
  CNCF Observability UI.

If no service appears, run the operation again and then refresh Jaeger:

```bash
bash run-operation.sh
```

## Prometheus Usage

Prometheus is used to inspect exported metric series. It does not automatically
show CNCF metrics on the top page; enter a PromQL expression and execute it.

Open Prometheus:

```text
http://127.0.0.1:9090
```

First confirm that Prometheus is scraping the Collector:

```promql
up
```

Expected result:

- `job="otel-collector"` is present;
- the value is `1`.

Then query CNCF metric series:

```promql
cncf_action_execution_executions_count_total
```

```promql
cncf_web_request_requests_count_total
```

For totals across labels:

```promql
sum(cncf_action_execution_executions_count_total)
```

```promql
sum(cncf_web_request_requests_count_total)
```

To list available CNCF metric families:

```promql
count by (__name__)({__name__=~"cncf_.*"})
```

If only scrape metrics such as `up`, `scrape_duration_seconds`, and
`scrape_samples_scraped` are visible, the stack is running but CNCF metrics have
not been exported yet. Run:

```bash
bash run-operation.sh
bash export-metrics.sh
```

Metric names may include OpenTelemetry exporter suffixes. Counters are exposed
with `_total`, for example `cncf_action_execution_executions_count_total`.

## Grafana Provisioning

Grafana is configured automatically when `start-stack.sh` starts the
observability stack. No manual datasource or dashboard setup is required before
or during the `Run` steps.

The provisioning files are:

- `grafana/provisioning/datasources/datasources.yml`
- `grafana/provisioning/dashboards/dashboards.yml`
- `grafana/provisioning/dashboards/cncf-observability.json`

The datasource provisioning creates:

- `Prometheus`, with UID `prometheus`, pointing to `http://prometheus:9090`
- `Jaeger`, with UID `jaeger`, pointing to `http://jaeger:16686`

The dashboard provisioning creates the `CNCF` folder and installs the
`CNCF Observability` dashboard.

If Grafana is already running and these files have changed, recreate only the
Grafana container to re-apply provisioning:

```bash
docker compose up -d --force-recreate grafana
```

Then open Grafana:

```text
http://127.0.0.1:3000
```

Log in with:

```text
user: admin
password: admin
```

Open the provisioned dashboard:

```text
http://127.0.0.1:3000/d/cncf-observability/cncf-observability
```

If the dashboard is not visible, check provisioning logs:

```bash
docker compose logs grafana
```

Look for datasource and dashboard provisioning messages. The expected log lines
include `inserting datasource from configuration` and
`finished to provision dashboards`.

Some panels show data immediately after `start-stack.sh` because they use
Prometheus scrape metrics for the Collector. CNCF metric panels show `No data`
until the CNCF server has run an operation and exported the runtime metrics:

```bash
bash run-operation.sh
bash export-metrics.sh
```

## What To Verify

- CNCF operation output is `Hello CNCF`.
- CNCF Observability UI shows local action/calltree/diagnostic facts.
- CNCF Metrics UI shows the runtime metrics snapshot before external export is
  inspected.
- Jaeger is used for trace inspection: service `goldenport-cncf`, action span,
  and CNCF span attributes.
- Prometheus is used for metric inspection: CNCF counter series exposed by the
  OpenTelemetry Collector.
- Grafana is used as the dashboard/query frontend over Prometheus, and can also
  use the Jaeger datasource for trace lookup.
- The provisioned `CNCF Observability` dashboard appears under the `CNCF`
  Grafana folder.

## Stop

```bash
docker compose down
```

Stop the CNCF server with `Ctrl-C` in Terminal 2.

## Notes

- CNCF does not call Jaeger, Prometheus, or Grafana APIs directly.
- OpenTelemetry export is disabled by default; this sample enables it explicitly.
- Payload bodies are not exported by default.
