package config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.web.reactive.function.client.WebClient

@Configuration(proxyBeanMethods = false)
class WebClientConfig {
    private val acceptedLanguages = mutableListOf("vi-VN", "en-US")
    private val acceptedEncodings = mutableListOf("gzip, deflate")
    private val defaultContentType = "application/json"

    @Bean
    fun webClient(builder: WebClient.Builder): WebClient = builder
        .defaultHeaders {
            val headers = HttpHeaders()
            headers.add(HttpHeaders.ACCEPT, "*/*")
            headers.add(HttpHeaders.CONTENT_TYPE, defaultContentType)
            headers.addAll(HttpHeaders.ACCEPT_LANGUAGE, acceptedLanguages)
            headers.addAll(HttpHeaders.ACCEPT_ENCODING, acceptedEncodings)

            it.addAll(headers)
        }
        .build()
}
