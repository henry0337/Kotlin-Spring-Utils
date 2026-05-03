package config

import helper.I18nHelper
import helper.ReactiveRedisHelper
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing
import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.web.reactive.function.client.WebClient
import service.ReactiveRestClient

@AutoConfiguration
@ConditionalOnClass(R2dbcRepository::class)
@EnableR2dbcAuditing
@Import(WebClientConfig::class)
class SpringUtilsAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    fun auditorAware() = AsyncAuditorAware()

    @Bean
    @ConditionalOnMissingBean
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
