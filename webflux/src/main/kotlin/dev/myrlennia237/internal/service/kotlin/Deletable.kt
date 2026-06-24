package dev.myrlennia237.internal.service.kotlin

import dev.myrlennia237.annotation.KotlinVariant
import kotlin.uuid.Uuid

/**
 * Contract xóa vĩnh viễn entity khỏi cơ sở dữ liệu theo ID (Kotlin coroutine variant).
 *
 * @author <a href="https://github.com/henry0337">Muharux</a>
 */
@KotlinVariant
internal fun interface Deletable {
    /**
     * Thực hiện xóa dữ liệu dựa trên ID của chúng.
     * @param id ID của entity cần xóa
     */
    suspend fun deleteById(id: Uuid)
}
