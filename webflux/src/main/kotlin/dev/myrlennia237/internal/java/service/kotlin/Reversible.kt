package dev.myrlennia237.internal.java.service.kotlin

import dev.myrlennia237.annotation.KotlinVariant
import kotlin.uuid.Uuid

/**
 * Contract vô hiệu hóa và kích hoạt lại entity theo cơ chế xóa mềm — Kotlin coroutine variant.
 *
 * @author <a href="https://github.com/henry0337">Muharux</a>
 */
@KotlinVariant
internal interface Reversible {
    /**
     * Vô hiệu hóa một bản ghi được chỉ định theo ID.
     * @param id Định danh của entity cần vô hiệu hóa
     */
    suspend fun disable(id: Uuid)

    /**
     * Kích hoạt lại một bản ghi được chỉ định theo ID.
     * @param id Định danh của entity cần kích hoạt lại
     */
    suspend fun enable(id: Uuid)
}
