package internal.service.kotlin

import annotation.KotlinVariant

/**
 * Kotlin variant cho contract xóa một entity theo kiểu coroutine.
 *
 * @author <a href="https://github.com/henry0337">Myrlennia</a>
 */
@KotlinVariant
internal fun interface Deletable<in ID> {
    /**
     * Xóa entity theo định danh.
     *
     * @param id Định danh của entity cần xóa
     */
    suspend fun deleteById(id: ID)
}
