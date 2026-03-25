# cncf-samples

CNCF の実行可能なサンプルパターン集を段階的に構築するリポジトリです。
各サンプルはドメインではなく構造パターンを示し、独立して build / run できることを前提にします。

## Overview

このリポジトリは、CNCF の構造パターンを小さなサンプルとして並べて比較しやすくするための土台です。
最初の目的はサンプルのカタログ化であり、複雑な業務ドメインの再現ではありません。

開発順序は `docs/journal/2026/03/cncf-samples-project.md` に記録された以下の段階に従います。

1. `01-minimal`
2. `02-crud`
3. `03-cqrs`
4. `04-event-driven`
5. `05-job`
6. `06-subsystem`
7. `07-subsystem-wiring`
8. `101-distributed`

## AI Directive

このプロジェクトは `ai/directive` を AI の共通契約として採用します。
`AGENT.md` と `RULE.md` は `ai/directive/core` へのシンボリックリンクとして公開します。

AI の優先順位は次の通りです。

1. `ai/directive/core`
2. 利用中プロファイル
   - `ai/directive/chatgpt-desktop`
   - `ai/directive/codex`
3. プロジェクト固有ドキュメント
   - `docs/rules`
   - `docs/spec`
   - `docs/design`
   - `docs/notes`
   - `docs/journal`

## Repository Layout

```text
.
├─ AGENT.md
├─ RULE.md
├─ ai/
│  └─ directive/
├─ docs/
│  ├─ architecture/
│  ├─ design/
│  ├─ journal/
│  ├─ notes/
│  ├─ patterns/
│  ├─ rules/
│  └─ spec/
├─ samples/
│  ├─ 01-minimal/
│  ├─ 02-crud/
│  ├─ 03-cqrs/
│  ├─ 04-event-driven/
│  ├─ 05-job/
│  ├─ 06-subsystem/
│  ├─ 07-subsystem-wiring/
│  └─ 101-distributed/
└─ shared/
   ├─ common-lib/
   └─ test-utils/
```

## Sample Standard

各 sample は次のレイアウトを基本とします。

```text
sample-name/
├─ README.md
├─ build.sbt
├─ component.d/
└─ src/main/scala/
```

`04-event-driven` 以降は必要に応じて `docker/` を追加します。

## How To Work

- 各 sample は他 sample に依存しません
- ルートの `build.sbt` はメタ情報のみを持ちます
- 実装と実行は各 sample ディレクトリ単位で進めます
- 共有コードは `shared/` に置けますが、sample の独立性を壊さない範囲に限定します

## Current Status

現時点では、実装より先にサンプルの配置と AI/ドキュメントの土台を整えています。
最初の実装対象は `samples/01-minimal` です。
