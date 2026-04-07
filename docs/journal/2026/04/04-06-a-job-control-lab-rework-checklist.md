# 06.a-job-control-lab Rework Checklist

- [x] Replace the same-JVM demo path with a shell-first user path
- [x] Keep the sample focused on job control rather than generic job observation
- [x] Add a minimal impl factory that exposes a control-ready submit operation
- [x] Document `submit -> suspend -> resume -> await -> history -> events`
- [x] Document `submit -> cancel -> history -> events`
- [x] Keep `run.sh` as the batch form of the documented shell sequence
- [x] Verify the documented commands against a running server
- [x] Move the runtime control proof into `cozy` scripted
- [x] Confirm matching `cozy` scripted coverage
- [x] Mark `06.a-job-control-lab` complete in the parent plan
