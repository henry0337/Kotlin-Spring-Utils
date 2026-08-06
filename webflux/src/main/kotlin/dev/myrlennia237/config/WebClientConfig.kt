package dev.myrlennia237.config

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.web.reactive.function.client.WebClient

/**
 * @author <a href="https://github.com/henry0337">Myrlennia</a>
 * @see WebClientProperties
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(WebClientProperties::class)
public class WebClientConfig(private val properties: WebClientProperties) {

    @Bean
    public fun webClient(builder: WebClient.Builder): WebClient = builder
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
