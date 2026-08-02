package dev.myrlennia237.config

import dev.myrlennia237.component.service.I18nService
import dev.myrlennia237.component.service.MailService
import dev.myrlennia237.internal.converter.KotlinInstantReadingConverter
import dev.myrlennia237.internal.converter.KotlinInstantWritingConverter
import dev.myrlennia237.internal.converter.KotlinUuidReadingConverter
import dev.myrlennia237.internal.converter.KotlinUuidWritingConverter
import dev.myrlennia237.service.ReactiveRedisService
import dev.myrlennia237.service.ReactiveHttpClient
import dev.myrlennia237.helper.ReactorHelper
import dev.myrlennia237.helper.ResponseHelper
import io.r2dbc.spi.ConnectionFactory
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.data.r2dbc.autoconfigure.DataR2dbcAutoConfiguration
import org.springframework.boot.data.redis.autoconfigure.DataRedisReactiveAutoConfiguration
import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.data.domain.ReactiveAuditorAware
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions
import org.springframework.data.r2dbc.dialect.DialectResolver
import org.springframework.data.redis.core.ReactiveStringRedisTemplate
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.web.reactive.function.client.WebClient

/**
 * Auto-configuration cho ứng dụng reactive.
 *
 * @author <a href="https://github.com/henry0337">Myrlennia</a>
 */
@AutoConfiguration(before = [DataR2dbcAutoConfiguration::class], after = [DataRedisReactiveAutoConfiguration::class])
@EnableR2dbcAuditing
@Import(WebClientConfig::class)
public class SpringReactiveAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public fun r2dbcCustomConversions(connectionFactory: ConnectionFactory): R2dbcCustomConversions =
        R2dbcCustomConversions.of(
            DialectResolver.getDialect(connectionFactory),
            listOf(
                KotlinInstantWritingConverter(),
                KotlinInstantReadingConverter(),
                KotlinUuidWritingConverter(),
                KotlinUuidReadingConverter()
            )
        )

    @Bean
    @ConditionalOnMissingBean(ReactiveAuditorAware::class)
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
