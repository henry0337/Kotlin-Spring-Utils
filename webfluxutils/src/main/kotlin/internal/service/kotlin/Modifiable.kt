package internal.service.kotlin

import annotation.KotlinVariant

/**
 * Kotlin variant cho contract cập nhật một entity theo kiểu coroutine.
 *
 * @author <a href="https://github.com/henry0337">Myrlennia</a>
 */
@KotlinVariant
internal fun interface Modifiable<out T, in ID, in I> {
    /**
     * Cập nhật một entity theo định danh.
     *
     * @param id Định danh của entity cần cập nhật
     * @param body Dữ liệu cập nhật
     * @return Entity sau khi cập nhật
     */
    suspend fun update(id: ID, body: I): T
}
