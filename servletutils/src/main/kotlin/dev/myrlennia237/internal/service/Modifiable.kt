package dev.myrlennia237.internal.service

/**
 * Java variant cho contract cập nhật một entity theo định danh.
 *
 * @author <a href="https://github.com/henry0337">Myrlennia</a>
 */
internal fun interface Modifiable<T : Any, ID, in I> {
    /**
     * Cập nhật entity tương ứng với [id] bằng dữ liệu từ [body].
     *
     * @param id   Định danh của entity cần cập nhật
     * @param body Dữ liệu đầu vào dùng để cập nhật entity
     * @return Entity sau khi đã được cập nhật
     */
    fun update(id: ID, body: I): T
}
