package dev.myrlennia237.internal.service

import java.util.UUID

/**
 * @param T Dữ liệu đầu ra, thường là Aggregate Root.
 * @param I Dữ liệu đầu vào, thường là DTO, Projection hoặc tương tự.
 * @author <a href="https://github.com/henry0337">Muharux</a>
 */
internal fun interface Modifiable<T : Any, in I> {
    /**
     * Cập nhật dữ liệu của một entity theo ID.
     * @param id ID của entity cần cập nhật
     * @param body Dữ liệu mới để thay thế
     */
    fun update(id: UUID, body: I): T
}
