package dev.myrlennia237.config

import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * Cấu hình WebClient qua prefix `spring-utils.web-client` trong `application.yml`.
 *
 * Ví dụ:
 * ```yaml
 * spring-utils:
 *   web-client:
 *     accept-languages: ["vi-VN", "en-US"]
 *     accept-encodings: ["gzip, deflate"]
 *     default-content-type: "application/json"
 * ```
 *
 * @param acceptLanguages    Danh sách giá trị của header `Accept-Language`; mặc định `["en-US"]`
 * @param acceptEncodings    Danh sách giá trị của header `Accept-Encoding`; mặc định `["gzip, deflate"]`
 * @param defaultContentType Giá trị mặc định của header `Content-Type`; mặc định `"application/json"`
 *
 * @author <a href="https://github.com/henry0337">Myrlennia</a>
 */
@ConfigurationProperties(prefix = "spring-utils.web-client")
data class WebClientProperties(
    val acceptLanguages: List<String> = listOf("en-US"),
    val acceptEncodings: List<String> = listOf("gzip, deflate"),
    val defaultContentType: String = "application/json"
)
