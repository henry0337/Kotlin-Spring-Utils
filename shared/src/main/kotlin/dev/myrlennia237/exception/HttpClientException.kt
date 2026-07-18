package dev.myrlennia237.exception

/**
 * Ném ra khi một lời gọi HTTP qua HTTP client của thư viện thất bại (kể cả sau khi đã retry, hoặc khi circuit
 * breaker đang mở).
 *
 * Đây là biến thể "convert" của chiến lược xử lý lỗi: exception thô từ Spring
 * (`RestClientResponseException`/`WebClientResponseException`) hay Resilience4j (`CallNotPermittedException`) được
 * bọc lại thành kiểu này để nhận diện nguồn gốc thư viện, đồng thời giữ nguyên nguyên nhân gốc qua [cause].
 *
 * @param message Mô tả lỗi
 * @param cause   Nguyên nhân gốc (exception của Spring/Resilience4j), hoặc `null`
 * @author <a href="https://github.com/henry0337">Muharux</a>
 */
public class HttpClientException(
    message: String? = null,
    cause: Throwable? = null,
) : SpringUtilsException(message, cause)
