package dev.myrlennia237.annotation

/**
 * Đánh dấu các **file**, **lớp** hoặc **phương thức (hàm)** được cung cấp bởi thư viện này là API đặc thù dành cho
 * ngôn ngữ Kotlin.
 * @author <a href="https://github.com/henry0337">Muharux</a>
 */
@Target(AnnotationTarget.FILE, AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
public annotation class KotlinVariant
