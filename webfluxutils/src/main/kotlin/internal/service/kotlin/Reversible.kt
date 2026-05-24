package internal.service.kotlin

import annotation.KotlinVariant

/**
 * Kotlin variant cho contract vô hiệu hóa entity theo kiểu coroutine.
 *
 * @author <a href="https://github.com/henry0337">Myrlennia</a>
 */
@KotlinVariant
internal fun interface Reversible<in ID> {
    /**
     * Vô hiệu hóa entity theo định danh.
     *
     * @param id Định danh của entity cần vô hiệu hóa
     */
    suspend fun disable(id: ID)
}
