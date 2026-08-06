package dev.myrlennia237.exception

/**
 * Được ném ra để chỉ định rằng một phương thức được **override/implement** từ một **lớp abstract** hoặc **interface** cần phải được
 * triển khai.
 *
 * Một usecase phổ biến là hàm [TODO][dev.myrlennia237.utils.CommonUtils.TODO] của lớp utility [dev.myrlennia237.utils.CommonUtils],
 * luôn ném ra exception này khi được gọi tới.
 *
 * @param message Message mô tả chi tiết hơn về lỗi này.
 * @param cause Nguyên nhân gây ra lỗi này.
 * @author <a href="https://github.com/AdorableDandelion25">Himekawa</a>
 */
public class ImplementationNeededException(
    message: String = "Phương thức này cần được triển khai!",
    cause: Throwable? = null
) : RuntimeException(message, cause)