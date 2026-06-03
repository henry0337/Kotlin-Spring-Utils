package dev.myrlennia237.config

import dev.myrlennia237.helper.I18nHelper
import dev.myrlennia237.helper.ReactiveRedisHelper
import dev.myrlennia237.service.ReactiveRestClient
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.web.reactive.function.client.WebClient

/**
 * Auto-configuration trung tâm của thư viện — tự động đăng ký các bean tiện ích.
 *
 * Các bean được cung cấp:
 * - [AsyncAuditorAware] — điền `createdBy`/`lastModifiedBy` tự động qua Spring Security
 * - [I18nHelper] — chỉ đăng ký khi có [MessageSource] trong context
 * - [ReactiveRestClient] — HTTP client reactive với Retry và Circuit Breaker của Resilience4j
 * - [ReactiveRedisHelper] — chỉ đăng ký khi có [ReactiveRedisTemplate] trong context
 *
 * Tất cả bean đều dùng `@ConditionalOnMissingBean` — consumer có thể override bất kỳ bean nào
 * bằng cách khai báo bean cùng kiểu trong application context.
 *
 * @author <a href="https://github.com/henry0337">Myrlennia</a>
 */
@AutoConfiguration
@EnableR2dbcAuditing
@Import(WebClientConfig::class)
class SpringUtilsAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    fun auditorAware() = AsyncAuditorAware()

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean(MessageSource::class)
    fun i18nHelper(messageSource: MessageSource) = I18nHelper(messageSource)

    @Bean
    @ConditionalOnMissingBean
    fun reactiveRestClient(webClient: WebClient) = ReactiveRestClient(webClient)

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnClass(ReactiveRedisTemplate::class)
    @ConditionalOnBean(ReactiveRedisTemplate::class)
    fun reactiveRedisHelper(redisTemplate: ReactiveRedisTemplate<String, String>) =
        ReactiveRedisHelper(redisTemplate)
}
