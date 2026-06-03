package dev.myrlennia237.internal.service.kotlin

import dev.myrlennia237.annotation.KotlinVariant

/**
 * Kotlin variant cho contract xóa vĩnh viễn một entity theo định danh.
 *
 * @author <a href="https://github.com/henry0338">Myrlennia</a>
 */
@KotlinVariant
internal fun interface Deletable<ID> {
    /**
     * Xóa vĩnh viễn entity tương ứng với [id] khỏi cơ sở dữ liệu.
     *
     * @param id Định danh của entity cần xóa
     */
    fun deleteById(id: ID)
}
