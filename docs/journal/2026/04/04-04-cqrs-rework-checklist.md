# 04 Cqrs Rework Checklist

- [x] Keep the sample focused on the visible CQRS split
- [x] Use the generated `sbt-cozy` line
- [x] Expose the sample as shell-first `bin/cncf` commands
- [x] Verify command-side help
- [x] Verify query-side help
- [x] Verify metadata output
- [x] Verify write-side job submission
- [x] Verify `await-job-result`
- [x] Verify read-side load after the write
- [x] Remove the unnecessary seed import line
- [x] Add a `cozy` scripted fixture
- [x] Confirm the scripted fixture passes
