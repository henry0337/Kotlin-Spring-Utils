package dev.myrlennia237.internal.service.kotlin

import dev.myrlennia237.annotation.KotlinVariant

/**
 * @param T Dữ liệu đầu ra, thường là Aggregate Root.
 * @param ID Kiểu của ID dùng để tìm kiếm dữ liệu đầu ra.
 * @param I Dữ liệu đầu vào, thường là DTO, Projection hoặc tương tự.
 * @author <a href="https://github.com/henry0337">Ademia</a>
 */
@KotlinVariant
internal fun interface Modifiable<out T, in ID, in I> {
    /**
     * Cập nhật dữ liệu của một entity theo ID.
     * @param id ID của entity cần cập nhật
     * @param body Dữ liệu mới để thay thế
     */
    suspend fun update(id: ID, body: I): T
}
