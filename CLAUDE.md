# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build Commands

```bash
# Build toàn bộ project
./gradlew build

# Build một module cụ thể
./gradlew :shared:build
./gradlew :servlet:build
./gradlew :webflux:build

# Chỉ compile (nhanh hơn, bỏ qua test)
./gradlew classes testClasses

# Chạy test
./gradlew test

# Dọn dẹp build artifacts
./gradlew clean
```

Root project name là `kotlin-spring-util`, group `dev.myrlennia237`, version `0.1.0-SNAPSHOT`. Toàn bộ code nằm dưới package gốc `dev.myrlennia237`.

## Architecture Overview

Đây là thư viện tiện ích đa module cho Spring Boot, cung cấp các lớp nền tảng (template) cho ứng dụng reactive và servlet. Thư viện hỗ trợ cả hai paradigm: **Java/Mono-based** và **Kotlin/suspend-based**.

### Ba Module

- **`shared`** — Tiện ích dùng chung cho cả hai module còn lại:
  - `Alias.kt` — type aliases cho Java interop (`JavaInstant`, `JavaSerializable`, `JavaDuration`, `Predicate`, `Function`).
  - `annotation/KotlinVariant.kt` — annotation `@KotlinVariant` đánh dấu API đặc thù Kotlin.
  - `annotation/spring/` — annotation Swagger/OpenAPI (`@ApiController`, `@ApiMethod`, `@ApiParameter`, `@ApiRequestBody`, `@ApiSchema`) và `@EnableReactiveSecurityCustomization`.
  - `component/ImmutableList.java` — danh sách bất biến (Java) với API kiểu Kotlin (`map`, `filter`, `sorted`, `distinct`, `take`, `drop`...).
  - `component/dto/PagedResponse.kt` — DTO phân trang tối giản + `Page.toPagedResponse()` (Kotlin variant).
  - `component/service/` — `I18nService` (dịch message qua `MessageSource` + `LocaleContextHolder`), `MailService` (gửi mail sync/async qua `JavaMailSender`).
  - `config/ServerWebExchangeContextFilter.kt` — `WebFilter` lưu `ServerWebExchange` vào Reactor Context.
  - `contract/` — `UserPrincipal` (expose `userId: UUID` cho auditing), `Cloneable<T>`.
  - `util/` — `CommonUtils` (`requireNonNull`, `toImmutableList`), `TimeUtils`.
- **`servlet`** — Template cho Spring MVC truyền thống (blocking, JPA).
- **`webflux`** — Module reactive đầy đủ tính năng: R2DBC, WebFlux, Redis reactive, Resilience4j, coroutine support.

### Dual API Pattern

Mỗi contract service/repository/entity có hai biến thể song song:
- **Java variant**: trả về `Mono<T>` / `Flux<T>`, dùng `java.util.UUID` và `java.time.Instant`.
- **Kotlin variant**: dùng `suspend` functions, trả về `T` trực tiếp, dùng `kotlin.uuid.Uuid` và `kotlin.time.Instant`, được đánh dấu `@KotlinVariant` + `@JvmSynthetic` (ẩn khỏi Java caller ở bytecode level).

Không kết hợp hai biến thể này trong cùng một class.

Cấu trúc package nội bộ của `webflux`:
- `internal/java/entity/` + `internal/java/service/java/` — contract Java variant (`Auditable`, `Conflictable`, `Restorable`, `Readable`, `Insertable`, `Modifiable`, `Deletable`, `Reversible`).
- `internal/kotlin/entity/` + `internal/kotlin/service/` — contract Kotlin variant (`KAuditable`, `KConflictable`, `KRestorable`, `KReadable`, `KInsertable`, `KModifiable`, `KDeletable`, `KReversible`).

### Template Layer

**`servlet` (`dev.myrlennia237.template.*`)**:
- `entity/BaseEntity` — `@MappedSuperclass` JPA: audit (`createdBy/By`, `createdDate/lastModifiedDate`), soft delete (`deleted: Short`, `deletedAt`), optimistic locking (`version`).
- `repository/ModifiedJpaRepository<T>` — kết hợp `ListCrudRepository` + `ListPagingAndSortingRepository` + `ListQuerydslPredicateExecutor` (QueryDSL thay cho Specification).
- `service/BaseService` → `service/CrudService<T, I1, I2>` — base CRUD service (inject sẵn `JPAQueryFactory`).
- `controller/BaseController` → `controller/CrudController` — controller base để extend.

**`webflux` (`dev.myrlennia237.template.*`)**:
- `entity/Entity` (Java variant) / `entity/KEntity` (Kotlin variant, `@Serializable`) — base entity R2DBC: audit, soft delete (`disabled`, `lastDisabledAt`, `lastDisabledBy`), optimistic locking (`version`).
- `repository/ModifiedR2dbcRepository<T>` (dùng `ReactiveQuerydslPredicateExecutor`) / `repository/CoroutineRepository<T>` (Kotlin, `Uuid` làm ID).
- `service/BaseReactiveService` → `service/java/AbstractCrudService` (Java) / `service/kotlin/CoroutineCrudService` (Kotlin) — implement toàn bộ CRUD contracts.
- `controller/ReactiveRestController` → `controller/java/AbstractCrudController` (Java) / `controller/kotlin/CoroutineRestController` (Kotlin) — controller base với các abstract endpoint (findAll, findById, create, update, delete, disable, enable).

### Auto-Configuration

- **`servlet`** đăng ký `dev.myrlennia237.config.SpringMvcAutoConfiguration` (`@EnableJpaAuditing`, import `RestClientConfig`). Cung cấp bean: `AuditorAwareImpl`, `I18nService`, `HttpClient`, `RedisService`, `JPAQueryFactory`, `MailService`.
- **`webflux`** đăng ký `dev.myrlennia237.config.SpringReactiveAutoConfiguration` (`@EnableR2dbcAuditing`, chạy `before` `DataR2dbcAutoConfiguration`, import `WebClientConfig`). Cung cấp bean: `R2dbcCustomConversions` (với các converter Kotlin `Uuid`/`Instant` ↔ Java), `AsyncAuditorAware`, `I18nService`, `ReactiveHttpClient`, `ReactiveRedisService`, `ReactorHelper`, `ResponseHelper`, `MailService`.

Cả hai được khai báo qua `META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports`. Auditing lấy UUID người dùng qua interface `UserPrincipal` — implement nó trên `UserDetails` của ứng dụng. Kotlin variant dùng `KAsyncAuditorAware` (`Uuid`), khai báo thủ công nếu entity kế thừa `KEntity`.

### HTTP Client

- `servlet`: `dev.myrlennia237.service.HttpClient` bọc `RestClient`, trả về `T?` trực tiếp (blocking).
- `webflux`: `dev.myrlennia237.service.ReactiveHttpClient` bọc `WebClient`, trả về `Mono<T>`.

Cả hai có:
- Circuit breaker + retry (Resilience4j) — instances `unwrapGet`/`unwrapPost` khai báo trong `application.yml` của consumer (hoặc dùng defaults của Resilience4j). Fallback method throw lại exception.
- Reified extensions cho Kotlin: `get<T>()`/`post<T>()` (servlet), `awaitGet<T>()`/`awaitPost<T>()` (webflux, suspend) — inject `self` (`@Lazy`) để giữ proxy Resilience4j.
- `HttpClient` (servlet) dùng JSpecify `@NullMarked`.

### HTTP Client / WebClient Config

Cấu hình default headers qua `application.yml`:
```yaml
spring-utils:
  web-client:      # webflux — WebClientProperties
    accept-languages: ["en-US"]           # default
    accept-encodings: ["gzip, deflate"]   # default
    default-content-type: "application/json"
  rest-client:     # servlet — RestClientProperties (cùng cấu trúc)
    accept-languages: ["en-US"]
```

## Tech Stack

| Component               | Version           |
|-------------------------|-------------------|
| Kotlin                  | 2.3.21            |
| Java Toolchain          | 25                |
| Spring Boot             | 4.0.7             |
| Spring WebFlux / R2DBC  | (theo Boot BOM)   |
| Kotlinx Coroutines      | via BOM           |
| Kotlinx Serialization   | 1.11.0            |
| Kotlinx DateTime        | 0.7.1             |
| Resilience4j            | 2.4.0             |
| QueryDSL (OpenFeign fork) | 7.4.0           |
| ArchUnit (test)         | 1.4.2             |
| Gradle (wrapper)        | 9.6.0             |

## Key Conventions

- **Explicit API mode** (`explicitApi()`) được bật cho cả ba module — mọi khai báo public/protected phải ghi rõ visibility và kiểu trả về.
- Compiler flags chung: `-Xjsr305=strict`, `-Xannotation-default-target=param-property`, `-Xreturn-value-checker=full`. `webflux` thêm `-opt-in=kotlin.uuid.ExperimentalUuidApi` và `-opt-in=kotlin.time.ExperimentalTime` (dùng `kotlin.uuid.Uuid`, `kotlin.time.Instant`).
- `@KotlinVariant` API phải là synthetic — có test **ArchUnit** (`KotlinVariantEnforcementTest`) ở cả `servlet` và `webflux` enforce việc này. Khi thêm Kotlin variant, luôn kèm `@JvmSynthetic` (hoặc `@file:JvmSynthetic`/`@file:KotlinVariant`).
- Mỗi module tạo JAR thường (`jar.enabled = true`), không tạo fat JAR (`bootJar.enabled = false`). Có `withSourcesJar()` và `maven-publish`.
- Repository được khai báo tập trung trong `settings.gradle.kts` (`FAIL_ON_PROJECT_REPOS`, gồm `mavenCentral` + `jitpack`); không thêm repo trong `build.gradle.kts` của submodule.
- `TYPESAFE_PROJECT_ACCESSORS` được bật — dùng `projects.shared` thay vì `project(":shared")`.
- `servlet` và `webflux` dùng `kapt` cho `spring-boot-configuration-processor` (và QueryDSL APT ở `servlet`).
