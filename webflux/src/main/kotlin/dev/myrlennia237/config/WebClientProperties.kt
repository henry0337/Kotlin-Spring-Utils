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
 * @author <a href="https://github.com/henry0337">Myrlennia</a>
 */
@ConfigurationProperties(prefix = "spring-utils.web-client")
public class WebClientProperties(
    public val acceptLanguages: List<String> = listOf("en-US"),
    public val acceptEncodings: List<String> = listOf("gzip, deflate"),
    public val defaultContentType: String = "application/json"
)
