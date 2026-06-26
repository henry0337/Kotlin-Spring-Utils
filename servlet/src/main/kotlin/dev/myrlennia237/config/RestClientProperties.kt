package dev.myrlennia237.config

import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * Thuộc tính cấu hình cho [RestClient][org.springframework.web.client.RestClient] mặc định của thư viện.
 *
 * Khai báo trong `application.yml` dưới prefix `spring-utils.rest-client`:
 * ```yaml
 * spring-utils:
 *   rest-client:
 *     accept-languages: ["vi-VN", "en-US"]
 *     accept-encodings: ["gzip, deflate"]
 *     default-content-type: "application/json"
 * ```
 *
 * @param acceptLanguages    Locale ưu tiên gửi trong header `Accept-Language`; ảnh hưởng đến ngôn ngữ phản hồi từ server
 * @param acceptEncodings    Encoding được client hỗ trợ, gửi trong header `Accept-Encoding`
 * @param defaultContentType Giá trị header `Content-Type` cho các request không chỉ định rõ
 */
@ConfigurationProperties(prefix = "spring-utils.rest-client")
public data class RestClientProperties(
    val acceptLanguages: List<String> = listOf("en-US"),
    val acceptEncodings: List<String> = listOf("gzip, deflate"),
    val defaultContentType: String = "application/json"
)
