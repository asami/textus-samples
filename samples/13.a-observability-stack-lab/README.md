# 13.a-observability-stack-lab

## Purpose

This sample is the full OB-06 observability stack lab.

CNCF sends OTLP HTTP to the OpenTelemetry Collector only. The Collector routes:

- traces to Jaeger;
- metrics to Prometheus;
- Grafana reads Prometheus and can also use Jaeger as a trace datasource.

## Ports

- Collector OTLP HTTP: <http://127.0.0.1:4318>
- Jaeger UI: <http://127.0.0.1:16686>
- Prometheus: <http://127.0.0.1:9090>
- Grafana: <http://127.0.0.1:3000>

Grafana default credentials are `admin` / `admin`.

## Run

```bash
bash start-stack.sh
bash run-workload.sh
```

`run-workload.sh` starts a local CNCF server with the minimal sample component,
calls `minimal.main.hello`, and then exports metrics from the same live runtime.

Then:

- open Jaeger and search for service `goldenport-cncf`;
- open Prometheus and query `cncf_web_request_requests_count` or
  `cncf_action_execution_executions_count`;
- open Grafana and use the preconfigured Prometheus datasource.

## Stop

```bash
docker compose down
```

## Notes

- CNCF does not call Jaeger, Prometheus, or Grafana APIs directly.
- OpenTelemetry export is disabled by default; this sample enables it explicitly.
- Payload bodies are not exported by default.
