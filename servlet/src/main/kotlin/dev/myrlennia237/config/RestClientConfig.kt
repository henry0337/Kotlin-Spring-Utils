package dev.myrlennia237.config

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.web.client.RestClient

/**
 * @author <a href="https://github.com/henry0337">Myrlennia</a>
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(RestClientProperties::class)
public class RestClientConfig(private val properties: RestClientProperties) {
    /**
     * Tạo bean [RestClient] với các header mặc định đã cấu hình.
     *
     * @param builder [RestClient.Builder] do Spring Boot cung cấp
     * @return [RestClient] đã áp header mặc định
     */
    @Bean
    public fun restClient(builder: RestClient.Builder): RestClient = builder
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
