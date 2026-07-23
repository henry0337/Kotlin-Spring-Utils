package dev.myrlennia237.annotation

/**
 * Đánh dấu các API của thư viện phụ thuộc vào những thành phần **experimental** của thư viện chuẩn Kotlin.
 *
 * @author <a href="https://github.com/henry0337">Muharux</a>
 */
@RequiresOptIn(
    message = "API này phụ thuộc vào kotlin.uuid.Uuid và kotlin.time.Instant còn ở trạng thái experimental, " +
        "có thể thay đổi trong các phiên bản Kotlin tương lai.",
    level = RequiresOptIn.Level.WARNING
)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.BINARY)
public annotation class ExperimentalKotlinVariantApi
