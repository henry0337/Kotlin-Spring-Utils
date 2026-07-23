package dev.myrlennia237.annotation

/**
 * Đánh dấu các API của thư viện phụ thuộc vào những thành phần **experimental** của thư viện chuẩn Kotlin.
 *
 * @author <a href="https://github.com/henry0337">Muharux</a>
 */
@RequiresOptIn(
    message = "Đây là API thử nghiệm dành cho phong cách lập trình thuần Kotlin (coroutines), " +
        "có thể thay đổi trong các phiên bản tương lai của thư viện.",
    level = RequiresOptIn.Level.WARNING
)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.BINARY)
public annotation class ExperimentalKotlinVariantApi
