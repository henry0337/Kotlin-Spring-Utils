package dev.myrlennia237.annotation

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity

/**
 * Đánh dấu một lớp [Configuration] có thể được sử dụng để cấu hình **Spring Security WebFlux** thông qua các **Bean**
 * loại [ServerHttpSecurity].
 * @author <a href="https://github.com/henry0337">Muharux</a>
 */
@Configuration
@EnableWebFluxSecurity
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class EnableReactiveSecurityCustomization
