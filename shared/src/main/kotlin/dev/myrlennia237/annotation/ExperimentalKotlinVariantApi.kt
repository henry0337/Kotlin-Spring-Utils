package dev.myrlennia237.annotation

/**
 * Đánh dấu các API của thư viện phụ thuộc vào những thành phần **experimental** của thư viện chuẩn Kotlin —
 * cụ thể là [kotlin.uuid.Uuid] và [kotlin.time.Instant].
 *
 * Các API mang annotation này có thể thay đổi (chữ ký, hành vi) khi những type experimental nói trên được
 * ổn định hóa trong các phiên bản Kotlin sau. Để sử dụng, hãy opt-in một cách tường minh bằng
 * `@OptIn(ExperimentalKotlinVariantApi::class)`, hoặc lan truyền (propagate) annotation này lên khai báo của bạn.
 *
 * Đây là biến thể Kotlin của thư viện (song hành với [KotlinVariant]); phía Java không chịu ảnh hưởng vì
 * không truy cập được các API này ở mức bytecode.
 *
 * @author <a href="https://github.com/henry0337">Muharux</a>
 */
@RequiresOptIn(
    message = "API này phụ thuộc vào kotlin.uuid.Uuid và kotlin.time.Instant còn ở trạng thái experimental, " +
        "có thể thay đổi trong các phiên bản Kotlin tương lai.",
    level = RequiresOptIn.Level.WARNING
)
@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY)
public annotation class ExperimentalKotlinVariantApi
