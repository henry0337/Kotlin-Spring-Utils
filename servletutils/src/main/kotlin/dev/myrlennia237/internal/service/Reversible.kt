package dev.myrlennia237.internal.service

/**
 * @param ID Kiểu của ID dùng để tìm kiếm dữ liệu đầu ra.
 * @author <a href="https://github.com/henry0337">Muharux</a>
 */
internal fun interface Reversible<ID> {
    /**
     * Vô hiệu hóa một bản ghi được chỉ định theo ID.
     * @param id Định danh của entity cần vô hiệu hóa
     */
    fun disable(id: ID)
}
