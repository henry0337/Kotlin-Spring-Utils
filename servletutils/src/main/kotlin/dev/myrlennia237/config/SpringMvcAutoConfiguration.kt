package dev.myrlennia237.config

import dev.myrlennia237.component.I18nService
import dev.myrlennia237.helper.RedisHelper
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
    fun i18nHelper(messageSource: MessageSource) = I18nService(messageSource)

    @Bean
    @ConditionalOnMissingBean
    fun restClientHelper(restClient: RestClient) = RestClientHelper(restClient)

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnClass(StringRedisTemplate::class)
    @ConditionalOnBean(StringRedisTemplate::class)
    fun redisHelper(redisTemplate: StringRedisTemplate) = RedisHelper(redisTemplate)
}
