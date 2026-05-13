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
- open Prometheus and query `cncf_web_request_requests_count` or
  `cncf_action_execution_executions_count`;
- confirm Prometheus receives CNCF runtime metrics through the Collector, not
  directly from CNCF;
- open Grafana and use Explore with the preconfigured Prometheus datasource to
  query CNCF metrics;
- optionally use the preconfigured Jaeger datasource in Grafana Explore to look
  up the same `goldenport-cncf` traces.

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

## Stop

```bash
docker compose down
```

Stop the CNCF server with `Ctrl-C` in Terminal 2.

## Notes

- CNCF does not call Jaeger, Prometheus, or Grafana APIs directly.
- OpenTelemetry export is disabled by default; this sample enables it explicitly.
- Payload bodies are not exported by default.
