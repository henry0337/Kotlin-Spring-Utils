package dev.myrlennia237.config

import dev.myrlennia237.component.I18nService
import dev.myrlennia237.service.ReactiveRedisService
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
import org.springframework.data.redis.core.ReactiveStringRedisTemplate
import org.springframework.web.reactive.function.client.WebClient

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
    fun i18nHelper(messageSource: MessageSource) = I18nService(messageSource)

    @Bean
    @ConditionalOnMissingBean
    fun reactiveRestClient(webClient: WebClient) = ReactiveRestClient(webClient)

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnClass(ReactiveRedisTemplate::class)
    @ConditionalOnBean(ReactiveRedisTemplate::class)
    fun reactiveRedisHelper(redisTemplate: ReactiveStringRedisTemplate) =
        ReactiveRedisService(redisTemplate)
}
