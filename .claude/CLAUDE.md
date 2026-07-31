# CLAUDE.md

Hướng dẫn cho Claude Code khi làm việc trong repo này.

## Build

```bash
./gradlew build                    # build toàn bộ
./gradlew :shared:build            # build 1 module (:servlet, :webflux tương tự)
./gradlew classes testClasses      # compile nhanh, bỏ test
./gradlew test
./gradlew clean
```

Root project `kotlin-spring-util`, groupId `dev.myrlennia237`, version `0.1.0-SNAPSHOT`. Package gốc: `dev.myrlennia237`.

## Publishing

`maven-publish` áp cho mọi subproject (publication `maven` từ `components["java"]` + `withSourcesJar()`). Docs API: Dokka.

- `./gradlew publishToMavenLocal` — cài vào `~/.m2`, test consumer cục bộ.
- Jitpack: cấu hình ở `jitpack.yml` (Gradle/JDK 17, JDK 25 qua foojay-resolver). Consumer dùng `com.github.henry0337.Kotlin-Spring-Utils:<module>:<tag>`.
- **Chưa** publish Maven Central (không có `com.vanniktech.maven.publish`, không ký GPG). Cần thì thêm lại plugin publish + GPG signing, và đăng ký namespace `dev.myrlennia237` trên Central Portal.

## Kiến trúc

Thư viện tiện ích đa module cho Spring Boot, hỗ trợ song song 2 paradigm: **Java/Mono-based** và **Kotlin/suspend-based**.

### 3 module

- **`shared`** — dùng chung:
  - `Alias.kt` — type alias Java interop (`JavaInstant`, `JavaSerializable`, `JavaDuration`, `Predicate`, `Function`).
  - `annotation/KotlinVariant.kt` — đánh dấu API đặc thù Kotlin.
  - `annotation/spring/` — annotation Swagger/OpenAPI + `@EnableReactiveSecurityCustomization`.
  - `component/ImmutableList.java` — list bất biến, API kiểu Kotlin.
  - `component/dto/PagedResponse.kt` — DTO phân trang + `Page.toPagedResponse()`.
  - `component/service/` — `I18nService`, `MailService`.
  - `config/ServerWebExchangeContextFilter.kt` — lưu `ServerWebExchange` vào Reactor Context.
  - `contract/` — `UserPrincipal` (expose `userId: UUID`), `Cloneable<T>`.
  - `util/` — `CommonUtils`, `TimeUtils`.
- **`servlet`** — template Spring MVC (blocking, JPA).
- **`webflux`** — reactive đầy đủ: R2DBC, WebFlux, Redis reactive, Resilience4j, coroutine.

### Dual API Pattern

Mỗi contract service/repository/entity có 2 biến thể song song, **không trộn lẫn trong cùng class**:

- **Java**: trả `Mono<T>`/`Flux<T>`, dùng `java.util.UUID` + `java.time.Instant`.
- **Kotlin**: `suspend fun` trả `T` trực tiếp, dùng `kotlin.uuid.Uuid` + `kotlin.time.Instant`, đánh dấu `@KotlinVariant` + `@JvmSynthetic` (ẩn khỏi Java caller ở bytecode).

Package nội bộ `webflux`:

- `internal/java/entity/` + `internal/java/service/java/` — contract Java (`Auditable`, `Conflictable`, `Restorable`, `Readable`, `Insertable`, `Modifiable`, `Deletable`, `Reversible`).
- `internal/kotlin/entity/` + `internal/kotlin/service/` — contract Kotlin (`KAuditable`, `KConflictable`, `KRestorable`, `KReadable`, `KInsertable`, `KModifiable`, `KDeletable`, `KReversible`).

### Template Layer

**`servlet` (`dev.myrlennia237.template.*`)**:

- `entity/BaseEntity` — `@MappedSuperclass` JPA: audit, soft delete (`disabled`, `lastDisabledAt`, `lastDisabledBy`), optimistic lock (`version`). Đồng bộ mô hình với webflux `Entity`/`KEntity`.
- `repository/ModifiedJpaRepository<T>` — `ListCrudRepository` + `ListPagingAndSortingRepository` + `ListQuerydslPredicateExecutor` (QueryDSL thay Specification).
- `service/BaseService` → `service/CrudService<T, I1, I2>` (inject sẵn `JPAQueryFactory`).
- `controller/BaseController` → `controller/CrudController`.

**`webflux` (`dev.myrlennia237.template.*`)**:

- `entity/Entity` (Java) / `entity/KEntity` (Kotlin, `@Serializable`) — base entity R2DBC: audit, soft delete, optimistic lock.
- `repository/ModifiedR2dbcRepository<T>` (`ReactiveQuerydslPredicateExecutor`) / `repository/CoroutineRepository<T>` (Kotlin, ID `Uuid`).
- `service/BaseReactiveService` → `service/java/AbstractCrudService` / `service/kotlin/CoroutineCrudService`.
- `controller/ReactiveRestController` → `controller/java/AbstractCrudController` / `controller/kotlin/CoroutineRestController` (endpoint: findAll, findById, create, update, delete, disable, enable).

### Auto-Configuration

- **`servlet`**: `dev.myrlennia237.config.SpringMvcAutoConfiguration` (`@EnableJpaAuditing`, import `RestClientConfig`). Bean: `AuditorAwareImpl`, `I18nService`, `HttpClient`, `RedisService`, `JPAQueryFactory`, `MailService`.
- **`webflux`**: `dev.myrlennia237.config.SpringReactiveAutoConfiguration` (`@EnableR2dbcAuditing`, `before DataR2dbcAutoConfiguration`, import `WebClientConfig`). Bean: `R2dbcCustomConversions` (converter Kotlin `Uuid`/`Instant` ↔ Java), `AsyncAuditorAware`, `I18nService`, `ReactiveHttpClient`, `ReactiveRedisService`, `ReactorHelper`, `ResponseHelper`, `MailService`.

Cả hai khai báo qua `META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports`. Auditing lấy UUID user qua `UserPrincipal` — implement trên `UserDetails` của app. Kotlin variant dùng `KAsyncAuditorAware` (`Uuid`), khai báo thủ công nếu entity kế thừa `KEntity`.

### HTTP Client

- `servlet`: `dev.myrlennia237.service.HttpClient` bọc `RestClient`, trả `T?` (blocking).
- `webflux`: `dev.myrlennia237.service.ReactiveHttpClient` bọc `WebClient`, trả `Mono<T>`.

Cả hai: circuit breaker + retry (Resilience4j, instance `unwrapGet`/`unwrapPost` khai báo ở `application.yml` consumer hoặc default Resilience4j); fallback `private` bọc exception gốc thành `dev.myrlennia237.exception.HttpClientException` (giữ `cause`). Kotlin reified ext: `get<T>()`/`post<T>()` (servlet), `awaitGet<T>()`/`awaitPost<T>()` (webflux, suspend) — inject `self` (`@Lazy`) giữ proxy Resilience4j. `HttpClient` dùng JSpecify `@NullMarked`.

## Tech Stack

Kotlin 2.4.0 · Java Toolchain 21 · Spring Boot 4.0.7 (WebFlux/R2DBC theo BOM) · Kotlinx Coroutines (BOM) · Kotlinx Serialization 1.11.0 · Kotlinx DateTime 0.7.1 · Resilience4j 2.4.0 · QueryDSL (OpenFeign fork) 7.4.0 · ArchUnit 1.4.2 (test) · Gradle wrapper 9.6.0

## Key Conventions

- `explicitApi()` bật ở cả 3 module — mọi khai báo public/protected phải ghi rõ visibility + kiểu trả về. Compiler flag chung: `-Xjsr305=strict`.
- `@KotlinVariant` API phải synthetic — ArchUnit test `KotlinVariantEnforcementTest` (servlet + webflux) enforce. Thêm Kotlin variant luôn kèm `@JvmSynthetic` (hoặc `@file:JvmSynthetic`/`@file:KotlinVariant`).
- Kotlin variant dùng thẳng `kotlin.uuid.Uuid`/`kotlin.time.Instant` (stable từ Kotlin 2.4, không cần opt-in). `@ExperimentalKotlinVariantApi` đã bỏ.
- Mỗi module: JAR thường (`jar.enabled = true`), không fat JAR (`bootJar.enabled = false`), có `withSourcesJar()` + `maven-publish`.
- **ABI validation**: `abiValidation()` built-in Kotlin Gradle plugin (opt-in `@OptIn(ExperimentalAbiValidation::class)`) ở cả 3 module. Baseline: `<module>/api/<module>.api` (commit VCS). `./gradlew updateKotlinAbi` cập nhật baseline khi đổi API có chủ đích; `./gradlew checkKotlinAbi` (chạy trong `check`/`build`) chặn đổi ABI ngoài ý muốn. Hàm `@JvmSynthetic` tự loại khỏi dump.
- Repo khai báo tập trung ở `settings.gradle.kts` (`FAIL_ON_PROJECT_REPOS`, `mavenCentral` + `jitpack`) — không thêm repo trong `build.gradle.kts` submodule.
- `TYPESAFE_PROJECT_ACCESSORS` bật — dùng `projects.shared` thay vì `project(":shared")`.
- `servlet`/`webflux` dùng `kapt` cho `spring-boot-configuration-processor` (và QueryDSL APT ở `servlet`).
