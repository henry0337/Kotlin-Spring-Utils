package dev.myrlennia237.internal.kotlin.service

import dev.myrlennia237.annotation.KotlinVariant
import java.util.UUID

/**
 * @param T Dữ liệu đầu ra, thường là Aggregate Root.
 * @param I Dữ liệu đầu vào, thường là DTO, Projection hoặc tương tự.
 * @author <a href="https://github.com/henry0337">Muharux</a>
 */
@KotlinVariant
internal fun interface KModifiable<out T, in I> {
    /**
     * Cập nhật dữ liệu của một entity theo ID.
     * @param id ID của entity cần cập nhật
     * @param body Dữ liệu mới để thay thế
     */
    suspend fun update(id: UUID, body: I): T
}
