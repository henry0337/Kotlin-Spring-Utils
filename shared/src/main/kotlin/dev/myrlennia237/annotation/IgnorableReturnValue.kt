package dev.myrlennia237.annotation

/**
 * Đánh dấu **dữ liệu trả về** của một hàm/phương thức có thể được **bỏ qua**.
 * 
 * Trình biên dịch của Java sẽ tự động suppress cảnh báo trên mà không phải dùng annotation [SuppressWarnings] thủ công.
 * 
 * @author <a href="https://github.com/AdorableDandelion25">Himekawa</a>
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
public annotation class IgnorableReturnValue