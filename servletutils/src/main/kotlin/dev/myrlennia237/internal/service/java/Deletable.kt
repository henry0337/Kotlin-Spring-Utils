package dev.myrlennia237.internal.service.java

/**
 * Java variant cho contract xóa vĩnh viễn một entity theo định danh.
 *
 * @author <a href="https://github.com/henry0338">Myrlennia</a>
 */
internal fun interface Deletable<ID> {
    /**
     * Xóa vĩnh viễn entity tương ứng với [id] khỏi cơ sở dữ liệu.
     *
     * @param id Định danh của entity cần xóa
     */
    fun deleteById(id: ID)
}
