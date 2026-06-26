package dev.myrlennia237.config

import dev.myrlennia237.component.I18nService
import dev.myrlennia237.service.ReactiveRedisService
import dev.myrlennia237.service.ReactiveHttpClient
import dev.myrlennia237.util.ReactorHelper
import dev.myrlennia237.util.ResponseHelper
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing
import org.springframework.data.redis.core.ReactiveStringRedisTemplate
import org.springframework.web.reactive.function.client.WebClient

/**
 * Auto-configuration cho module `webflux` — đăng ký các bean cốt lõi khi thư viện được
 * import vào dự án Spring Boot Reactive.
 *
 * Tất cả bean đều dùng điều kiện [@ConditionalOnMissingBean][org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean],
 * cho phép consumer override bất kỳ bean nào bằng cách khai báo bean cùng kiểu.
 *
 * Các bean được cung cấp:
 * - [AsyncAuditorAware] — lấy UUID người dùng hiện tại cho R2DBC auditing
 * - [I18nService][dev.myrlennia237.component.I18nService] — dịch message từ [MessageSource][org.springframework.context.MessageSource]
 * - [ReactiveHttpClient] — HTTP client reactive tích hợp Resilience4j
 * - [ReactiveRedisService] — Redis helper reactive (chỉ khi có bean [ReactiveStringRedisTemplate][org.springframework.data.redis.core.ReactiveStringRedisTemplate])
 * - [ReactorHelper][dev.myrlennia237.util.ReactorHelper] — tiện ích Reactor
 * - [ResponseHelper][dev.myrlennia237.util.ResponseHelper] — tiện ích bọc HTTP response
 *
 * @see AsyncAuditorAware
 * @see ReactiveHttpClient
 */
@AutoConfiguration
@EnableR2dbcAuditing
@Import(WebClientConfig::class)
public class SpringReactiveAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public fun auditorAware(): AsyncAuditorAware = AsyncAuditorAware()

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean(MessageSource::class)
    public fun i18nService(messageSource: MessageSource): I18nService = I18nService(messageSource)

    @Bean
    @ConditionalOnMissingBean
    public fun reactiveHttpClient(webClient: WebClient): ReactiveHttpClient = ReactiveHttpClient(webClient)

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnClass(ReactiveStringRedisTemplate::class)
    @ConditionalOnBean(ReactiveStringRedisTemplate::class)
    public fun reactiveRedisHelper(redisTemplate: ReactiveStringRedisTemplate): ReactiveRedisService =
        ReactiveRedisService(redisTemplate)

    @Bean
    @ConditionalOnMissingBean
    public fun reactorHelper(): ReactorHelper = ReactorHelper()

    @Bean
    @ConditionalOnMissingBean
    public fun responseHelper(): ResponseHelper = ResponseHelper()
}
