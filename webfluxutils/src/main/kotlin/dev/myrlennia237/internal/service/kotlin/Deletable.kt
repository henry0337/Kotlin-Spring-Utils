package dev.myrlennia237.internal.service.kotlin

import dev.myrlennia237.annotation.KotlinVariant

/**
 * @author <a href="https://github.com/henry0337">Muharux</a>
 */
@KotlinVariant
internal fun interface Deletable<in ID> {
    /**
     * Thực hiện xóa dữ liệu dựa trên ID của chúng.
     * @param id ID của entity cần xóa
     */
    suspend fun deleteById(id: ID)
}
