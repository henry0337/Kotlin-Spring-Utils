package dev.myrlennia237.config

import com.querydsl.jpa.impl.JPAQueryFactory
import dev.myrlennia237.component.service.I18nService
import dev.myrlennia237.component.service.MailService
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
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.web.client.RestClient

/**
 * Auto-configuration cho ứng dụng Spring MVC (blocking, JPA): bật JPA auditing và đăng ký sẵn các bean nền tảng
 * của thư viện. Mọi bean đều `@ConditionalOnMissingBean` nên ứng dụng có thể ghi đè bằng bean của riêng mình.
 *
 * Bean cung cấp: [BlockingAuditorAware], [I18nService], [HttpClient], [RedisService], [JPAQueryFactory], [MailService].
 *
 * @author <a href="https://github.com/henry0337">Muharux</a>
 */
@AutoConfiguration
@EnableJpaAuditing
@Import(RestClientConfig::class)
public class SpringMvcAutoConfiguration {

    /** Cung cấp thông tin người dùng hiện tại cho JPA auditing (`createdBy`/`lastModifiedBy`). */
    @Bean
    @ConditionalOnMissingBean
    public fun auditorAware(): BlockingAuditorAware = BlockingAuditorAware()

    /** Dịch message qua [MessageSource]; chỉ đăng ký khi có bean [MessageSource]. */
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean(MessageSource::class)
    public fun i18nHelper(messageSource: MessageSource): I18nService = I18nService(messageSource)

    /** HTTP client blocking (bọc [RestClient]) kèm circuit breaker + retry. */
    @Bean
    @ConditionalOnMissingBean
    public fun httpClient(restClient: RestClient): HttpClient = HttpClient(restClient)

    /** Redis helper blocking; chỉ đăng ký khi [StringRedisTemplate] có mặt trên classpath và trong context. */
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnClass(StringRedisTemplate::class)
    @ConditionalOnBean(StringRedisTemplate::class)
    public fun redisService(redisTemplate: StringRedisTemplate): RedisService = RedisService(redisTemplate)

    /** [JPAQueryFactory] của QueryDSL, dựng từ [EntityManager]. */
    @Bean
    @ConditionalOnMissingBean
    public fun jpaQueryFactory(entityManager: EntityManager): JPAQueryFactory = JPAQueryFactory(entityManager)

    /** Dịch vụ gửi mail; chỉ đăng ký khi có bean [JavaMailSender]. */
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean(JavaMailSender::class)
    public fun mailService(mailSender: JavaMailSender): MailService = MailService(mailSender)
}
