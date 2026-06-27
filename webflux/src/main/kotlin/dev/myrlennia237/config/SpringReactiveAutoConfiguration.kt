package dev.myrlennia237.config

import dev.myrlennia237.component.service.I18nService
import dev.myrlennia237.component.service.MailService
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
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.web.reactive.function.client.WebClient

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

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean(JavaMailSender::class)
    public fun mailService(mailSender: JavaMailSender): MailService = MailService(mailSender)
}
