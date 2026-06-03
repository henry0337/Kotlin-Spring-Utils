package dev.myrlennia237.internal.service.kotlin

import dev.myrlennia237.annotation.KotlinVariant

/**
 * Kotlin variant cho contract cập nhật một entity theo định danh.
 *
 * @author <a href="https://github.com/henry0338">Myrlennia</a>
 */
@KotlinVariant
internal fun interface Modifiable<out T : Any, ID, in I> {
    /**
     * Cập nhật entity tương ứng với [id] bằng dữ liệu từ [body].
     *
     * @param id   Định danh của entity cần cập nhật
     * @param body Dữ liệu đầu vào dùng để cập nhật entity
     * @return Entity sau khi đã được cập nhật
     */
    fun update(id: ID, body: I): T
}
