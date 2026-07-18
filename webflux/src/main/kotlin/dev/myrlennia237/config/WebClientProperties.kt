package dev.myrlennia237.config

import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * Thuộc tính cấu hình cho [WebClient][org.springframework.web.reactive.function.client.WebClient] mặc định của thư viện.
 *
 * Khai báo trong `application.yml` dưới prefix `spring-utils.web-client`:
 * ```yaml
 * spring-utils:
 *   web-client:
 *     accept-languages: ["vi-VN", "en-US"]
 *     accept-encodings: ["gzip, deflate"]
 *     default-content-type: "application/json"
 * ```
 *
 * @param acceptLanguages    Locale ưu tiên gửi trong header `Accept-Language`; ảnh hưởng đến ngôn ngữ phản hồi từ server
 * @param acceptEncodings    Encoding được client hỗ trợ, gửi trong header `Accept-Encoding`
 * @param defaultContentType Giá trị header `Content-Type` cho các request không chỉ định rõ
 */
@ConfigurationProperties(prefix = "spring-utils.web-client")
public class WebClientProperties(
    public val acceptLanguages: List<String> = listOf("en-US"),
    public val acceptEncodings: List<String> = listOf("gzip, deflate"),
    public val defaultContentType: String = "application/json"
) {
    override fun toString(): String =
        "WebClientProperties(acceptLanguages=$acceptLanguages, acceptEncodings=$acceptEncodings, " +
            "defaultContentType=$defaultContentType)"
}
