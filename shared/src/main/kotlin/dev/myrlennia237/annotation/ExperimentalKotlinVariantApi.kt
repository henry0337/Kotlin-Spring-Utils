package dev.myrlennia237.annotation

/**
 * Đánh dấu các API thuộc biến thể "K" trong thư viện này là experimental, nghĩa là chức năng mà chúng cung cấp không ổn định về
 * mặt hành vi/cú pháp và sẽ có thể bị thay đổi/xóa bất cứ lúc nào bởi thư viện Kotlin chuẩn mà không có thông báo trước.
 *
 * Hiện tại, đối tượng có thể đánh dấu bằng annotation này bao gồm:
 * - Lớp, giao diện, lớp object hoặc annotation
 * - Phương thức/hàm
 * - Thuộc tính
 *
 * Để sử dụng các API được đánh dấu bằng annotation này thì bắt buộc đối tượng sử dụng phải opt-in, thông qua ví dụ khai báo sau:
 * ```kt
 * // Lớp
 * @OptIn(ExperimentalKotlinVariantApi::class)
 * class Foo
 * // hoặc phương thức/hàm
 * @OptIn(ExperimentalKotlinVariantApi::class)
 * fun foo() {}
 * ```
 * 
 * @author <a href="https://github.com/henry0337">Muharux</a>
 * @see KotlinVariant
 */
@RequiresOptIn(
    message = """
        Đối tượng được đánh dấu này sử dụng các API được đánh dấu là experimental từ các thư viện chuẩn Kotlin, 
        hành vi của chúng có thể bị thay đổi bất cứ lúc nào mà không báo trước.
    """,
    level = RequiresOptIn.Level.WARNING
)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY)
@MustBeDocumented
@Retention(AnnotationRetention.BINARY)
public annotation class ExperimentalKotlinVariantApi
