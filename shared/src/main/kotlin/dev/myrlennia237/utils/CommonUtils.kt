package dev.myrlennia237.utils

import dev.myrlennia237.exception.ImplementationNeededException
import org.springframework.util.Assert

/**
 * @author <a href="https://github.com/henry0337">Myrlennia</a>
 * @author <a href="https://github.com/AdorableDandelion25">Himekawa</a>
 */
public object CommonUtils {
    /**
     * Yêu cầu `instance` được chỉ định không thể chứa `null` làm giá trị hợp lệ.
     *
     * [IllegalArgumentException] sẽ được ném ra nếu như đối tượng được truyền vào có giá trị `null`.
     *
     * @param instance  Đối tượng cần kiểm tra
     * @param message   Message báo lỗi nếu như `instance` null
     * @return Chính instance đó nếu nó không `null`.
     * @throws IllegalArgumentException nếu [instance] là `null`.
     */
    @JvmStatic
    @JvmOverloads
    public fun requireNonNull(
        instance: Any?, 
        message: String = "${instance?.javaClass?.simpleName ?: "Tham số 'instance'"} không thể là null!"
    ): Unit = Assert.notNull(instance, message)
    
    
    /**
     * Trả về chính `instance` được cung cấp nếu nó không `null`, ngược lại trả về giá trị mặc định được cung cấp.
     *
     * @param instance      Đối tượng cần kiểm tra
     * @param defaultValue  Giá trị mặc định nếu [instance] là `null`
     * @param T             Kiểu dữ liệu của đối tượng
     * @return Chính instance đó nếu nó không `null` hoặc giá trị mặc định nếu [instance] là `null`.
     */
    @JvmStatic  
    public fun <T> requireNonNullOrDefault(instance: T?, defaultValue: T): T =
        instance ?: defaultValue
        
    
    /**
     * Luôn ném ra [ImplementationNeededException] để nhắc nhở lập trình viên rằng phương thức cần được triển khai.
     * 
     * @param message Message chi tiết cho exception trên
     * @throws ImplementationNeededException vì phương thức chưa được triển khai.
     */
    @JvmStatic
    @JvmOverloads
    @IgnorableReturnValue
    public fun <T> TODO(message: String = ""): T {
        throw ImplementationNeededException(message)
    }
}
