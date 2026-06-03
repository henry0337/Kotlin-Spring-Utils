# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build Commands

```bash
# Build toàn bộ project
./gradlew build

# Build một module cụ thể
./gradlew :shared:build
./gradlew :servletutils:build
./gradlew :webfluxutils:build

# Chạy test
./gradlew test

# Dọn dẹp build artifacts
./gradlew clean
```

## Architecture Overview

Đây là thư viện tiện ích đa module cho Spring Boot, cung cấp các lớp nền tảng (template) cho ứng dụng reactive và servlet. Thư viện hỗ trợ cả hai paradigm: **Java/Mono-based** và **Kotlin/suspend-based**.

### Ba Module

- **`shared`** — Tiện ích dùng chung: type aliases cho Java interop (`Alias.kt`), annotation `@KotlinVariant`, I18n helper, custom `LocalDateTime` serializer.
- **`servletutils`** — Template cho Spring MVC truyền thống: `BaseEntity` cho JPA.
- **`webfluxutils`** — Module chính với đầy đủ tính năng reactive: base entity R2DBC, repository, service, controller templates, HTTP client, Redis helper, Resilience4j integration, Spring Boot auto-configuration.

### Dual API Pattern

Mỗi interface (service, repository) có hai biến thể song song:
- **Java variant** (`internal/service/java/`, `template/service/java/`): trả về `Mono<T>` / `Flux<T>`
- **Kotlin variant** (`internal/service/kotlin/`, `template/service/kotlin/`): dùng `suspend` functions, trả về `T` trực tiếp, được đánh dấu `@KotlinVariant`

Không kết hợp hai biến thể này trong cùng một class.

### Template Layer (`webfluxutils/dev/myrlennia237/template/`)

Các class nền tảng để extend:
- `BaseEntity<ID>` — Entity R2DBC có sẵn: audit fields (created/modified by+date), soft delete (`deleted`, `deletedAt`), optimistic locking (`version`)
- `ReactiveRepository<T, ID>` / `CoroutineRepository<T, ID>` — Repository base
- `BaseReactiveCrudService` (Java) / `BaseCoroutineService` (Kotlin) — Implement toàn bộ CRUD contracts
- `BaseReactiveCrudController` / `BaseCoroutineController` — Controller base trống để extend

### Auto-Configuration

`webfluxutils` đăng ký `dev.myrlennia237.config.SpringUtilsAutoConfiguration` qua `META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports`. Auto-config này cung cấp: `AsyncAuditorAware`, `I18nHelper`, `ReactiveRestClient`, `ReactiveRedisHelper`.

### ReactiveRestClient

HTTP client trong `dev.myrlennia237.service.ReactiveRestClient` bọc WebClient với:
- Circuit breaker và retry (Resilience4j) — cần khai báo instances `unwrapGet`/`unwrapPost` trong `application.yml` của consumer (hoặc dùng defaults của Resilience4j)
- Coroutine extensions: `awaitGet<T>()`, `awaitPost<T>()` với reified types
- JSpecify `@NullMarked` cho null safety
- Fallback methods là `protected` — có thể override khi extend class

### WebClientProperties

Cấu hình WebClient qua `spring-utils.web-client.*` trong `application.yml`:
```yaml
spring-utils:
  web-client:
    accept-languages: ["en-US"]        # default
    accept-encodings: ["gzip, deflate"] # default
    default-content-type: "application/json" # default
```

## Tech Stack

| Component | Version |
|-----------|---------|
| Kotlin | 2.3.21 |
| Java Toolchain | 25 |
| Spring Boot | 4.0.6 |
| Spring WebFlux / R2DBC | 4.0.6 |
| Kotlinx Coroutines | via BOM |
| Kotlinx Serialization | 1.10.0 |
| Kotlinx DateTime | 0.7.1 |
| Resilience4j | 2.4.0 |
| Gradle | 9.4.1 |

## Key Conventions

- Kotlin compiler flag `-Xjsr305=strict` được bật trong `webfluxutils` — tuân thủ null annotations của JSpecify.
- Mỗi module tạo JAR thường (`jar.enabled = true`), không tạo fat JAR (`bootJar.enabled = false`).
- Repository được khai báo tập trung trong `settings.gradle.kts` (`FAIL_ON_PROJECT_REPOS`); không thêm repo trong `build.gradle.kts` của submodule.
- `TYPESAFE_PROJECT_ACCESSORS` được bật — dùng `projects.shared` thay vì `project(":shared")`.
