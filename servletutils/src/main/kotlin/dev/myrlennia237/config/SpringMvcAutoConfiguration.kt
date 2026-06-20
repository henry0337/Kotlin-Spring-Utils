package dev.myrlennia237.config

import com.querydsl.jpa.impl.JPAQueryFactory
import dev.myrlennia237.component.I18nService
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
    fun httpClient(restClient: RestClient) = HttpClient(restClient)

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnClass(StringRedisTemplate::class)
    @ConditionalOnBean(StringRedisTemplate::class)
    fun redisService(redisTemplate: StringRedisTemplate) = RedisService(redisTemplate)

    @Bean
    fun jpaQueryFactory(entityManager: EntityManager): JPAQueryFactory = JPAQueryFactory(entityManager)
}
