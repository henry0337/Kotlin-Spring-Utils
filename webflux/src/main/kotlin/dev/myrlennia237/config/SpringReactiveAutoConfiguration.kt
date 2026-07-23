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
import org.springframework.boot.autoconfigure.data.r2dbc.R2dbcDataAutoConfiguration
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
 * Auto-configuration cho ứng dụng reactive (WebFlux, R2DBC): bật R2DBC auditing, chạy trước
 * [R2dbcDataAutoConfiguration] và đăng ký sẵn các bean nền tảng. Mọi bean đều `@ConditionalOnMissingBean` nên
 * ứng dụng có thể ghi đè bằng bean của riêng mình.
 *
 * Bean cung cấp: [R2dbcCustomConversions] (kèm converter Kotlin `Uuid`/`Instant` ↔ Java), [AsyncAuditorAware],
 * [I18nService], [ReactiveHttpClient], [ReactiveRedisService], [ReactorHelper], [ResponseHelper], [MailService].
 *
 * @author <a href="https://github.com/henry0337">Muharux</a>
 */
@AutoConfiguration(before = [R2dbcDataAutoConfiguration::class])
@EnableR2dbcAuditing
@Import(WebClientConfig::class)
public class SpringReactiveAutoConfiguration {

    /** [R2dbcCustomConversions] kèm các converter chuyển đổi `kotlin.uuid.Uuid`/`kotlin.time.Instant` ↔ kiểu Java. */
    @Bean
    @ConditionalOnMissingBean
    public fun r2dbcCustomConversions(connectionFactory: ConnectionFactory): R2dbcCustomConversions =
        R2dbcCustomConversions.of(
            DialectResolver.getDialect(connectionFactory),
            listOf(
                KotlinUuidWritingConverter(),
                KotlinUuidReadingConverter(),
                KotlinInstantWritingConverter(),
                KotlinInstantReadingConverter()
            )
        )

    /** Cung cấp UUID người dùng hiện tại cho R2DBC auditing; chỉ đăng ký khi chưa có [ReactiveAuditorAware] nào. */
    @Bean
    @ConditionalOnMissingBean(ReactiveAuditorAware::class)
    public fun auditorAware(): AsyncAuditorAware = AsyncAuditorAware()

    /** Dịch message qua [MessageSource]; chỉ đăng ký khi có bean [MessageSource]. */
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean(MessageSource::class)
    public fun i18nService(messageSource: MessageSource): I18nService = I18nService(messageSource)

    /** HTTP client reactive (bọc [WebClient]) kèm circuit breaker + retry. */
    @Bean
    @ConditionalOnMissingBean
    public fun reactiveHttpClient(webClient: WebClient): ReactiveHttpClient = ReactiveHttpClient(webClient)

    /** Redis helper reactive; chỉ đăng ký khi [ReactiveStringRedisTemplate] có mặt trên classpath và trong context. */
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnClass(ReactiveStringRedisTemplate::class)
    @ConditionalOnBean(ReactiveStringRedisTemplate::class)
    public fun reactiveRedisHelper(redisTemplate: ReactiveStringRedisTemplate): ReactiveRedisService =
        ReactiveRedisService(redisTemplate)

    /** Helper tiện ích cho các thao tác Reactor thường dùng. */
    @Bean
    @ConditionalOnMissingBean
    public fun reactorHelper(): ReactorHelper = ReactorHelper()

    /** Helper dựng [ResponseEntity][org.springframework.http.ResponseEntity] cho các kết quả HTTP thường gặp. */
    @Bean
    @ConditionalOnMissingBean
    public fun responseHelper(): ResponseHelper = ResponseHelper()

    /** Dịch vụ gửi mail; chỉ đăng ký khi có bean [JavaMailSender]. */
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean(JavaMailSender::class)
    public fun mailService(mailSender: JavaMailSender): MailService = MailService(mailSender)
}
