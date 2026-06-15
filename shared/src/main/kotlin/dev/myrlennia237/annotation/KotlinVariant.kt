package dev.myrlennia237.annotation

/**
 * Đánh dấu API chỉ dành cho Kotlin — không khả dụng từ Java.
 *
 * @author <a href="https://github.com/henry0337">Myrlennia</a>
 */
@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY
)
@Retention(AnnotationRetention.RUNTIME)
annotation class KotlinVariant
