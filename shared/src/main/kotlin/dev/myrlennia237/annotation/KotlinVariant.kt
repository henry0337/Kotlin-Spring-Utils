package dev.myrlennia237.annotation

/**
 * Đánh dấu các **lớp**, **phương thức (hàm)** hoặc **thuộc tính của lớp** được cung cấp bởi thư viện này là API đặc thù
 * dành cho ngôn ngữ Kotlin.
 * @author <a href="https://github.com/henry0337">Muharux</a>
 */
@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY
)
@Retention(AnnotationRetention.RUNTIME)
annotation class KotlinVariant
