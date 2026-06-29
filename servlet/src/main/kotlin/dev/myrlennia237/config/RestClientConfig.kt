package dev.myrlennia237.config

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.web.client.RestClient

@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(RestClientProperties::class)
public class RestClientConfig(private val properties: RestClientProperties) {
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
