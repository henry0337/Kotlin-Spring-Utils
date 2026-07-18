package dev.myrlennia237.util

import dev.myrlennia237.component.ImmutableList

/**
 * @author <a href="https://github.com/henry0337">Muharux</a>
 */
public object CommonUtils {
    /**
     * Yêu cầu `instance` được chỉ định không thể chứa `null` làm giá trị hợp lệ.
     *
     * [IllegalArgumentException] sẽ được ném ra nếu như đối tượng được truyền vào có giá trị `null`.
     *
     * @param instance  Đối tượng cần kiểm tra
     * @param T         Kiểu dữ liệu của đối tượng cần kiểm tra
     * @return Chính instance đó nếu nó không `null`.
     * @throws IllegalArgumentException nếu [instance] là `null`.
     */
    @JvmStatic
    public fun <T> requireNonNull(instance: T?): T & Any = requireNotNull(instance)

    /**
     * Trả về chính `instance` được cung cấp nếu nó không `null`, ngược lại trả về giá trị mặc định được cung cấp.
     *
     * @param instance      Đối tượng cần kiểm tra
     * @param defaultValue  Giá trị mặc định nếu [instance] là `null`
     * @param T             Kiểu dữ liệu của đối tượng
     * @return Chính instance đó nếu nó không `null` hoặc giá trị mặc định nếu [instance] là `null`.
     */
    @JvmStatic
    public fun <T> requireNonNull(instance: T?, defaultValue: T): T = instance ?: defaultValue

    /**
     * Trả về chính `instance` được cung cấp nếu nó không `null`, ngược lại hiển thị ra thông báo lỗi.
     *
     * @param instance  Đối tượng cần kiểm tra
     * @param error     Thông báo lỗi sẽ hiển thị ở console
     * @param T         Kiểu dữ liệu của đối tượng cần kiểm tra
     * @return Chính instance đó nếu nó không `null` hoặc hiển thị thông báo lỗi nếu [instance] là `null`.
     * @throws IllegalArgumentException nếu [instance] là `null`.
     */
    @JvmStatic
    public fun <T> requireNonNull(
        instance: T?,
        error: () -> String = { "`instance` không thể chứa giá trị `null`." }
    ): T & Any = requireNotNull(instance, error)

    /**
     * Đánh dấu một [List] sẽ không thể thực hiện các tác vụ liên quan tới chính sửa dữ liệu bên trong chúng
     * sau khi được chuyển đổi.
     *
     * @return Một phiên bản bất biến sau chuyển đổi của [List].
     */
    @JvmStatic
    public fun <E : Any> toImmutableList(list: List<E>): ImmutableList<E> = ImmutableList.copyFrom(list)
}
