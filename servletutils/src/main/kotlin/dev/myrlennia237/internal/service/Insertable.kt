package dev.myrlennia237.internal.service

/**
 * Java variant cho contract tạo mới một entity theo dữ liệu đầu vào.
 *
 * @author <a href="https://github.com/henry0337">Myrlennia</a>
 */
internal fun interface Insertable<T : Any, in I> {
    /**
     * Tạo mới một entity từ dữ liệu đầu vào.
     *
     * @param item Dữ liệu đầu vào dùng để tạo entity
     * @return Entity đã được lưu
     */
    fun insert(item: I): T
}
