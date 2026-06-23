package dev.myrlennia237.util

object CommonUtils {
    /**
     * Đảm bảo đối tượng không thể `null`.
     *
     * [IllegalStateException] sẽ được ném ra nếu như đối tượng được truyền vào có giá trị `null`.
     *
     * @param T Kiểu dữ liệu của đối tượng
     * @param instance Đối tượng cần kiểm tra
     * @return Chính instance đó nếu nó không `null`.
     * @throws IllegalStateException nếu [instance] là `null`.
     * @author <a href="https://github.com/henry0337">Muharux</a>
     */
    @JvmStatic
    fun <T : Any> assertNonNull(instance: T?): T = requireNotNull(instance)

    /**
     * Trả về chính `instance` được cung cấp nếu nó không `null`, ngược lại trả về giá trị mặc định được cung cấp.
     *
     * @param T Kiểu dữ liệu của đối tượng
     * @param instance Đối tượng cần kiểm tra
     * @param defaultValue Giá trị mặc định nếu [instance] là `null`
     * @return Chính instance đó nếu nó không `null` hoặc giá trị mặc định nếu [instance] là `null`.
     */
    @JvmStatic
    fun <T : Any> assertNonNull(instance: T?, defaultValue: T): T = instance ?: defaultValue
}
