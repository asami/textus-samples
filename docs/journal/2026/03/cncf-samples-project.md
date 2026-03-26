CNCF Samples Project — Design & Development Instruction

[Overview]
This project provides a catalog of executable sample patterns for CNCF.
Each sample demonstrates a structural pattern (Subsystem / Component / Service / Operation).
The goal is pattern demonstration, not domain modeling.

--------------------------------------------------

[Repository Structure]

cncf-samples/
  README.md

  samples/
    01-minimal/
    01.a-minimal-lab/
    01.b-component-script/
    02-crud/
    03-cqrs/
    04-event-driven/
    05-job/
    06-subsystem/
    07-subsystem-wiring/
    101-distributed/

  shared/
    common-lib/
    test-utils/

  docs/
    patterns/
    architecture/

  guide/
    invocation/

--------------------------------------------------

[General Rules]

1. Independence
- Each sample MUST be independently buildable and executable
- MUST NOT depend on other samples

2. Dependency Direction
- cncf-samples → cncf
- Reverse dependency is forbidden

3. Structure Priority
- Organize by architectural pattern
- NOT by domain

4. Minimal Complexity
- One main concept per sample
- Avoid unnecessary abstraction

5. Executability
- CLI execution required
- Server mode optional
- Docker from level 04+

6. Documentation Split
- `docs/` is reserved for sample development documentation
- user-facing documentation belongs under `guide/`

--------------------------------------------------

[Sample Standard Layout]

Each sample MUST follow:

sample-name/
  README.md
  build.sbt
  component.d/
  src/main/scala/
  docker/ (optional)

--------------------------------------------------

[Sample Definitions]

----------------------------------------
01-minimal — Minimal Component

Purpose:
- Smallest executable CNCF unit

Structure:
- 1 Component
- 1 Service (main)
- 1 Operation (hello)

Behavior:
./run.sh
→ "Hello CNCF"

Note:
- `01-minimal` depends on the published `org.goldenport %% goldenport-cncf % 0.3.14-SNAPSHOT` artifact.
- In this workspace, the artifact is also available locally via `publishLocal` from the CNCF framework repo.

Concepts:
- Component / Service / Operation

----------------------------------------
01.a-minimal-lab — Guided Learning Lab

Purpose:
- Learn the execution model by exercising `01-minimal`

Structure:
- Small lab sample or companion material
- Focused on observation and manual experimentation

Concepts:
- selector practice
- run vs invoke
- class discovery vs repository loading
- observation through hands-on steps

----------------------------------------
01.b-component-script — Component Script Example

Purpose:
- Learn how to build small management commands on top of Component operations

Structure:
- Script-oriented companion sample
- Focused on thin shell wrappers over command paths

Concepts:
- script-style management command
- thin wrapper design
- command path as operational contract

----------------------------------------
02-crud — Entity & Repository

Purpose:
- Basic data handling

Structure:
- Entity
- Repository
- Command + Query

Operations:
- createItem
- getItem
- listItems

Concepts:
- Entity lifecycle
- Repository
- Command/Query

----------------------------------------
03-cqrs — CQRS

Purpose:
- Separate command and query

Structure:
- Command → async (Job)
- Query → sync

Operations:
- createItem (async)
- getItem (sync)

Concepts:
- async command
- job integration

----------------------------------------
04-event-driven — Event

Purpose:
- Event-based execution

Structure:
- Event emission
- Event handler

Concepts:
- decoupling
- sync/async events

----------------------------------------
05-job — Job Management

Purpose:
- Job lifecycle

Operations:
- submitJob
- getJobStatus

Concepts:
- async lifecycle
- retry / failure

----------------------------------------
06-subsystem — Minimum Subsystem

Purpose:
- Show the minimum subsystem structure

Structure:
- 1 subsystem
- 1 component

Concepts:
- subsystem minimum
- subsystem boundary
- minimal composition

----------------------------------------
07-subsystem-wiring — Two-Component Subsystem

Purpose:
- Show how one subsystem wires two components

Structure:
- 1 subsystem
- 2 components
- Wiring between components

Concepts:
- subsystem assembly
- component wiring
- composition boundary

----------------------------------------
101-distributed — Distributed

Purpose:
- Multi-subsystem deployment

Structure:
- Docker-based separation

Concepts:
- network boundary
- component communication

--------------------------------------------------

[README Requirements]

Each sample MUST include:

- Overview
- Structure
- How to Run
- Example Commands
- Key Learnings

--------------------------------------------------

[Build & Execution]

CLI:
sbt run command <component>.<service>.<operation>

Docker (optional):
docker compose up

--------------------------------------------------

[Development Order]

1. 01-minimal
2. 01.a-minimal-lab
3. 01.b-component-script
4. 02-crud
5. 03-cqrs
6. 04-event-driven
7. 05-job
8. 06-subsystem
9. 07-subsystem-wiring
10. 101-distributed

--------------------------------------------------

[Completion Criteria]

- Build success
- CLI execution works
- Expected output confirmed
- README completed

--------------------------------------------------

END
