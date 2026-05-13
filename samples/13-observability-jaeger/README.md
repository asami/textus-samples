# 13-observability-jaeger

## Purpose

This sample is the minimal OB-06 OpenTelemetry proof.

It starts one Jaeger all-in-one container and runs the `minimal.main.hello`
sample operation with
OTLP HTTP trace export enabled. CNCF remains the internal observability source;
Jaeger receives only the exported trace projection.

## Ports

- CNCF Web UI: <http://127.0.0.1:19613/web/system/admin/observability>
- Jaeger UI: <http://127.0.0.1:16686>
- Jaeger OTLP HTTP receiver: <http://127.0.0.1:4318>

## Run

Terminal 1: start Jaeger.

```bash
bash start-jaeger.sh
```

Terminal 2: start the CNCF sample server.

```bash
bash run-server.sh
```

Terminal 3: run a sample operation through the CNCF client.

```bash
bash run-operation.sh
```

Then inspect CNCF first:

- open the CNCF Observability UI:
  <http://127.0.0.1:19613/web/system/admin/observability>
- use the CNCF observability pages to confirm the local action/calltree/export
  diagnostics.

After the CNCF-local check, inspect the external backend:

- open the Jaeger UI: <http://127.0.0.1:16686>
- search for service `goldenport-cncf`;
- open a trace and confirm the exported CNCF action span for
  `minimal.main.hello`;
- check span attributes for CNCF metadata such as component/service/operation
  and compact payload summary/reference facts. Raw payload bodies should not be
  exported by default.

## What To Verify

- CNCF operation output is `Hello CNCF`.
- CNCF Observability UI shows the local execution facts first.
- Jaeger receives an exported trace projection for service `goldenport-cncf`.
- Jaeger is used to inspect external trace propagation and span attributes, not
  as the authoritative CNCF diagnostic store.

## Stop

```bash
docker compose down
```

Stop the CNCF server with `Ctrl-C` in Terminal 2.

## Notes

- The sample sends OTLP directly to Jaeger.
- Payload bodies are not exported by default. CNCF exports CallTree span facts
  and compact payload summary/reference metadata.
- Export failures do not fail the CNCF operation.
