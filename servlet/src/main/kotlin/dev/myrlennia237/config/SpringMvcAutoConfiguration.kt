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

@AutoConfiguration
@EnableJpaAuditing
@Import(RestClientConfig::class)
public class SpringMvcAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public fun auditorAware(): AuditorAwareImpl = AuditorAwareImpl()

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean(MessageSource::class)
    public fun i18nHelper(messageSource: MessageSource): I18nService = I18nService(messageSource)

    @Bean
    @ConditionalOnMissingBean
    public fun httpClient(restClient: RestClient): HttpClient = HttpClient(restClient)

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnClass(StringRedisTemplate::class)
    @ConditionalOnBean(StringRedisTemplate::class)
    public fun redisService(redisTemplate: StringRedisTemplate): RedisService = RedisService(redisTemplate)

    @Bean
    @ConditionalOnMissingBean
    public fun jpaQueryFactory(entityManager: EntityManager): JPAQueryFactory = JPAQueryFactory(entityManager)

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean(JavaMailSender::class)
    public fun mailService(mailSender: JavaMailSender): MailService = MailService(mailSender)

    @Bean
    @ConditionalOnMissingBean
    public fun applicationContextProvider(): ApplicationContextProvider = ApplicationContextProvider()
}
