package dev.myrlennia237.internal.service

/**
 * @author <a href="https://github.com/henry0337">Muharux</a>
 */
internal fun interface Deletable<ID> {
    /**
     * Thực hiện xóa dữ liệu dựa trên ID của chúng.
     * @param id ID của entity cần xóa
     */
    fun deleteById(id: ID)
}
