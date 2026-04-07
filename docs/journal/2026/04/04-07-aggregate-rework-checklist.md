# 04-07-aggregate-rework-checklist

- [x] replace same-JVM demo-first framing with shell-first aggregate sample framing
- [x] keep aggregate-specific application logic in sample-specific impl factory
- [x] verify `create-order-record -> await-job-result -> add-line -> load-order-aggregate`
- [x] update README to `Setup` / `Run The Whole Scenario` / `Command Walkthrough`
- [x] narrow the stable first line to `create -> await -> add-line -> load`
- [x] treat `search-order-aggregate` in a later visibility-focused step
- [x] relocate internal same-JVM proof to `cozy` scripted
- [x] close sample and reflect completion in the parent plan
