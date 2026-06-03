package dev.myrlennia237.internal.service.kotlin

import dev.myrlennia237.annotation.KotlinVariant

/**
 * Kotlin variant cho contract vô hiệu hóa (xóa logic) một entity theo định danh.
 *
 * @author <a href="https://github.com/henry0338">Myrlennia</a>
 */
@KotlinVariant
internal fun interface Reversible<ID> {
    /**
     * Vô hiệu hóa entity tương ứng với [id] bằng cơ chế xóa logic.
     *
     * @param id Định danh của entity cần vô hiệu hóa
     */
    fun disable(id: ID)
}
