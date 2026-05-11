# 13-observability-jaeger

## Purpose

This sample is the minimal OB-06 OpenTelemetry proof.

It starts one Jaeger all-in-one container and runs the `minimal.main.hello`
sample operation with
OTLP HTTP trace export enabled. CNCF remains the internal observability source;
Jaeger receives only the exported trace projection.

## Ports

- Jaeger UI: <http://127.0.0.1:16686>
- Jaeger OTLP HTTP receiver: <http://127.0.0.1:4318>

## Run

```bash
bash start-jaeger.sh
bash run-workload.sh
```

Open the Jaeger UI and search for service `goldenport-cncf`.

## Stop

```bash
docker compose down
```

## Notes

- The sample sends OTLP directly to Jaeger.
- Payload bodies are not exported by default. CNCF exports CallTree span facts
  and compact payload summary/reference metadata.
- Export failures do not fail the CNCF operation.
