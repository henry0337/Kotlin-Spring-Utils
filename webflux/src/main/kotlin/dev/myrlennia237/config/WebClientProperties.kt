package dev.myrlennia237.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "spring-utils.web-client")
data class WebClientProperties(
    val acceptLanguages: List<String> = listOf("en-US"),
    val acceptEncodings: List<String> = listOf("gzip, deflate"),
    val defaultContentType: String = "application/json"
)
