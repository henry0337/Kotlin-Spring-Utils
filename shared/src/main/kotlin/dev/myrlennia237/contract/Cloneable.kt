package dev.myrlennia237.contract

/**
 * Contract cho phép các đối tượng tạo ra phiên bản mới bằng cách nhân bản bản thân chúng, thay thế cho
 * [java.lang.Cloneable].
 *
 * Nên tạo **shallow copy** khi tất cả các field là immutable, và **deep copy** khi có field
 * là mutable object — để tránh chia sẻ tham chiếu ngoài ý muốn.
 *
 * @param T Kiểu của đối tượng được sao chép — thường là chính lớp triển khai mô hình này.
 * @author <a href="https://github.com/henry0337">Muharux</a>
 */
public fun interface Cloneable<T> {
    /**
     * Trả về một bản sao độc lập của đối tượng hiện tại.
     *
     * @return Bản sao của đối tượng hiện tại với kiểu `T`.
     */
    public fun copy(): T
}
