package dev.myrlennia237.config

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.web.reactive.function.client.WebClient

/**
 * Cấu hình [WebClient] mặc định cho thư viện.
 *
 * Default headers được áp dụng theo [WebClientProperties] — consumer có thể override
 * từng giá trị qua `spring-utils.web-client.*` trong `application.yml` mà không cần
 * khai báo lại toàn bộ bean.
 *
 * @author <a href="https://github.com/henry0337">Myrlennia</a>
 * @see WebClientProperties
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(WebClientProperties::class)
class WebClientConfig(private val properties: WebClientProperties) {
    @Bean
    fun webClient(builder: WebClient.Builder): WebClient = builder
        .defaultHeaders {
            val headers = HttpHeaders()
            headers.add(HttpHeaders.ACCEPT, "*/*")
            headers.add(HttpHeaders.CONTENT_TYPE, properties.defaultContentType)
            headers.addAll(HttpHeaders.ACCEPT_LANGUAGE, properties.acceptLanguages)
            headers.addAll(HttpHeaders.ACCEPT_ENCODING, properties.acceptEncodings)
            it.addAll(headers)
        }
        .build()
}
