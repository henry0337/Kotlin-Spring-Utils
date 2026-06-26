package dev.myrlennia237.config

import com.querydsl.jpa.impl.JPAQueryFactory
import dev.myrlennia237.component.I18nService
import dev.myrlennia237.service.RedisService
import dev.myrlennia237.service.HttpClient
import jakarta.persistence.EntityManager
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.web.client.RestClient

/**
 * Auto-configuration cho module `servlet` — đăng ký các bean cốt lõi khi thư viện được
 * import vào dự án Spring Boot MVC (Servlet).
 *
 * Tất cả bean đều dùng điều kiện [@ConditionalOnMissingBean][org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean],
 * cho phép consumer override bất kỳ bean nào bằng cách khai báo bean cùng kiểu.
 *
 * Các bean được cung cấp:
 * - [AuditorAwareImpl] — lấy UUID người dùng hiện tại cho JPA auditing
 * - [I18nService][dev.myrlennia237.component.I18nService] — dịch message từ [MessageSource][org.springframework.context.MessageSource]
 * - [HttpClient] — HTTP client blocking tích hợp Resilience4j
 * - [RedisService] — Redis helper blocking (chỉ khi có bean [StringRedisTemplate][org.springframework.data.redis.core.StringRedisTemplate])
 * - [JPAQueryFactory][com.querydsl.jpa.impl.JPAQueryFactory] — factory cho QueryDSL JPA
 *
 * @see AuditorAwareImpl
 * @see HttpClient
 */
@AutoConfiguration
@EnableJpaAuditing
@Import(RestClientConfig::class)
public class SpringMvcAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public fun auditorAware(): AuditorAwareImpl = AuditorAwareImpl()

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean(MessageSource::class)
    public fun i18nHelper(messageSource: MessageSource): I18nService = I18nService(messageSource)

    @Bean
    @ConditionalOnMissingBean
    public fun httpClient(restClient: RestClient): HttpClient = HttpClient(restClient)

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnClass(StringRedisTemplate::class)
    @ConditionalOnBean(StringRedisTemplate::class)
    public fun redisService(redisTemplate: StringRedisTemplate): RedisService = RedisService(redisTemplate)

    @Bean
    @ConditionalOnMissingBean
    public fun jpaQueryFactory(entityManager: EntityManager): JPAQueryFactory = JPAQueryFactory(entityManager)
}
