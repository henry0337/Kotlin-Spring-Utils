package dev.myrlennia237.internal.service.kotlin

import dev.myrlennia237.annotation.KotlinVariant

/**
 * @param ID Kiểu của ID dùng để tìm kiếm dữ liệu đầu ra.
 * @author <a href="https://github.com/henry0337">Ademia</a>
 */
@KotlinVariant
internal fun interface Reversible<in ID> {
    /**
     * Vô hiệu hóa một bản ghi được chỉ định theo ID.
     * @param id Định danh của entity cần vô hiệu hóa
     */
    suspend fun disable(id: ID)
}
