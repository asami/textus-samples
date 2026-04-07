# Port Wiring Guide for CNCF Samples (2026-04-07)

status=draft
created_at=2026-04-07
tag=cncf-samples, wiring, port, extension-point, variation-point, sample-guide

---

# Purpose

This note explains how sample applications should wire the current CNCF
`Port` model.

It is intended for sample authors.

The goal is to make sample wiring explicit while keeping the framework-side
execution boundary unchanged.

---

# Important Rule

`Port` is **not** a runtime invocation boundary.

In current CNCF, the canonical execution boundary remains:

- `OperationCall`

Therefore, sample wiring must follow this interpretation:

- `PortApi` resolves a requirement into a service contract
- `VariationPoint` exposes and injects variation settings
- `ExtensionPoint` provides the concrete service/provider/adapter
- `Binding.install(...)` injects the resolved provider into `Component.Port`
- operation logic reads the injected service from `Component.Port`

Do not implement `Port` as a runtime client with `invoke(...)`.

---

# Minimal Wiring Flow

A sample should wire ports in this order:

1. define a service trait
2. define a requirement type
3. implement `PortApi`
4. implement `VariationPoint`
5. implement one or more `ExtensionPoint`s
6. create `Component.Binding`
7. install the binding into the component
8. use `component.port.get[T]` from operation logic

---

# Step 1: Define the Service Trait

The service trait is the abstract contract used by operation logic.

Example:

```scala
trait GenerateService {
  def generate(prompt: String): Consequence[String]
}
```

This is the type that should finally be injected into `Component.Port`.

---

# Step 2: Define the Requirement Type

The requirement type carries the information needed to resolve a service
contract and variation.

Example:

```scala
final case class GenerateRequirement(
  capability: String,
  provider: Option[String] = None,
  mode: Option[String] = None,
  engine: Option[String] = None
)
```

Keep this small.
Do not mix low-level backend transport state into this type unless really needed.

---

# Step 3: Implement `PortApi`

`PortApi` resolves a requirement into a service contract.

Example:

```scala
val api = new PortApi[GenerateRequirement, GenerateService] {
  def resolve(req: GenerateRequirement): Consequence[ServiceContract[GenerateService]] =
    req.capability match {
      case "generate" =>
        Consequence.success(
          ServiceContract(
            name = "generate-service",
            runtimeClass = classOf[GenerateService]
          )
        )
      case other =>
        Consequence.failure(s"unsupported capability: $other")
    }
}
```

`PortApi` should not create concrete providers.
It only resolves the abstract contract.

---

# Step 4: Implement `VariationPoint`

`VariationPoint` is the configuration-facing boundary.

Responsibilities:

- expose current variation state
- accept injected variation settings
- normalize variation state for binding

Example:

```scala
val variation = new VariationPoint[GenerateRequirement] {
  def current(req: GenerateRequirement)(using ExecutionContext): Consequence[VariationSelection] =
    Consequence.success(
      VariationSelection(
        provider = req.provider,
        mode = req.mode,
        engine = req.engine
      )
    )

  def inject(
    req: GenerateRequirement,
    selection: VariationSelection
  )(using ExecutionContext): Consequence[GenerateRequirement] =
    Consequence.success(
      req.copy(
        provider = selection.provider,
        mode = selection.mode,
        engine = selection.engine
      )
    )
}
```

Do not reduce `VariationPoint` to route selection only.
It must support current/inject semantics.

---

# Step 5: Implement `ExtensionPoint`

`ExtensionPoint` is where adapter/provider construction belongs.

Example:

```scala
val localgemma = new ExtensionPoint[GenerateService] {
  def supports(
    contract: ServiceContract[GenerateService],
    variation: VariationSelection
  )(using ExecutionContext): Boolean =
    contract.name == "generate-service" &&
      variation.provider.contains("gemma") &&
      variation.mode.contains("local") &&
      variation.engine.contains("ollama")

  def provide(
    contract: ServiceContract[GenerateService],
    variation: VariationSelection
  )(using ExecutionContext): Consequence[GenerateService] =
    Consequence.success(new LocalGemmaGenerateService())
}
```

This is the canonical place for adapter realization.

Do not make `ExtensionPoint` return another runtime `Port`.
It should return the concrete service/provider implementation.

---

# Step 6: Assemble the Port Definition

Create the port definition from the three facets.

Example:

```scala
val generateport = org.goldenport.cncf.component.Port(
  api = api,
  spi = Vector(localgemma),
  variation = variation
)
```

Then wrap it as a binding.

```scala
val binding = Component.Binding(generateport)
```

---

# Step 7: Install the Binding into the Component

Register the binding under a stable name.

Example:

```scala
component.withBinding("generate", binding)
```

Then install it using the requirement.

```scala
component.install_binding[GenerateRequirement, GenerateService](
  name = "generate",
  req = GenerateRequirement(
    capability = "generate",
    provider = Some("gemma"),
    mode = Some("local"),
    engine = Some("ollama")
  )
)
```

This injects the resolved `GenerateService` into `Component.Port`.

---

# Step 8: Use the Injected Service from Operation Logic

Operation logic should read the resolved service from `Component.Port`.

Example:

```scala
component.port.get[GenerateService] match {
  case Some(service) => service.generate("hello")
  case None => Consequence.failure("generate service is not installed")
}
```

This keeps runtime execution on the injected service, not on `Port` itself.

---

# Recommended Sample Structure

For a sample, the following structure is recommended:

- `GenerateService` / `ChatService`
- `GenerateRequirement` / `ChatRequirement`
- `GeneratePortApi` / `ChatPortApi`
- `GenerateVariationPoint` / `ChatVariationPoint`
- `Local...ExtensionPoint` / `Remote...ExtensionPoint`
- component bootstrap wiring using `withBinding` + `install_binding`
- operations using `component.port.get[T]`

---

# What Samples Must Avoid

Do not do the following in sample code:

- define `Port.invoke(...)`
- treat `Port` as a runtime client
- return a `Port` from `ExtensionPoint`
- collapse `VariationPoint` into `select(...)` only
- let operation logic branch directly on backend implementation

These patterns follow the obsolete handoff model and should not be used.

---

# Practical Goal for Sample Authors

The sample should demonstrate this separation clearly:

- requirement resolution
- variation exposure/injection
- adapter realization
- provider injection
- operation execution through injected service

That is the intended meaning of the current CNCF port model.

