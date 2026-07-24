package dev.myrlennia237.internal.kotlin.service

import dev.myrlennia237.annotation.KotlinVariant
import java.util.UUID

/**
 * Contract vô hiệu hóa và kích hoạt lại entity theo cơ chế xóa mềm — Kotlin coroutine variant.
 *
 * @author <a href="https://github.com/henry0337">Muharux</a>
 */
@KotlinVariant
internal interface KReversible {
    /**
     * Vô hiệu hóa một bản ghi được chỉ định theo ID.
     * @param id Định danh của entity cần vô hiệu hóa
     */
    suspend fun disable(id: UUID)

    /**
     * Kích hoạt lại một bản ghi được chỉ định theo ID.
     * @param id Định danh của entity cần kích hoạt lại
     */
    suspend fun enable(id: UUID)
}
