package dev.myrlennia237.exception

/**
 * Lớp cơ sở cho mọi exception do thư viện này chủ động ném ra.
 *
 * Mục đích: giúp phân biệt lỗi phát sinh từ thư viện với lỗi từ Spring/Resilience4j hay dependency khác — bắt
 * `SpringUtilsException` là bắt mọi lỗi có nguồn gốc từ thư viện. Khi bọc (wrap) exception của dependency, nguyên
 * nhân gốc luôn được giữ qua [cause].
 *
 * @param message Mô tả lỗi
 * @param cause   Nguyên nhân gốc (exception của dependency), hoặc `null`
 * @author <a href="https://github.com/henry0337">Muharux</a>
 */
public open class SpringUtilsException(
    message: String? = null,
    cause: Throwable? = null,
) : RuntimeException(message, cause)
