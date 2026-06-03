package dev.myrlennia237.config

import dev.myrlennia237.helper.BlockingRedisHelper
import dev.myrlennia237.helper.I18nHelper
import dev.myrlennia237.service.RestClientHelper
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
 * Auto-configuration trung tâm của module servletutils — tự động đăng ký các bean tiện ích.
 *
 * Các bean được cung cấp:
 * - [AuditorAwareImpl] — điền `createdBy`/`lastModifiedBy` tự động qua Spring Security
 * - [I18nHelper] — chỉ đăng ký khi có [MessageSource] trong context
 * - [RestClientHelper] — HTTP client blocking với Retry và Circuit Breaker của Resilience4j
 * - [BlockingRedisHelper] — chỉ đăng ký khi có [StringRedisTemplate] trong context
 *
 * Tất cả bean đều dùng `@ConditionalOnMissingBean` — consumer có thể override bất kỳ bean nào
 * bằng cách khai báo bean cùng kiểu trong application context.
 *
 * @author <a href="https://github.com/henry0338">Myrlennia</a>
 */
@AutoConfiguration
@EnableJpaAuditing
@Import(RestClientConfig::class)
class SpringMvcAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    fun auditorAware() = AuditorAwareImpl()

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean(MessageSource::class)
    fun i18nHelper(messageSource: MessageSource) = I18nHelper(messageSource)

    @Bean
    @ConditionalOnMissingBean
    fun restClientHelper(restClient: RestClient) = RestClientHelper(restClient)

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnClass(StringRedisTemplate::class)
    @ConditionalOnBean(StringRedisTemplate::class)
    fun blockingRedisHelper(redisTemplate: StringRedisTemplate) = BlockingRedisHelper(redisTemplate)
}
