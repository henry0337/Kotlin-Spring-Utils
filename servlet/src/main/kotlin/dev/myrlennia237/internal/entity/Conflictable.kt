package dev.myrlennia237.internal.entity

/**
 * Chỉ định một **Aggregate Root** sẽ sử dụng cơ chế **khóa lạc quan (optimistic locking)** lên các dữ liệu mà nó quản lý.
 * @author <a href="https://github.com/henry0337">Myrlennia</a>
 * @see <a href="https://docs.spring.io/spring-data/relational/reference/jdbc/entity-persistence.html#jdbc.entity-persistence.optimistic-locking">
 *     Lưu trữ dữ liệu lên Entity
 *     </a>
 */
internal interface Conflictable {
    /**
     * @return Một số nguyên 64-bit đại diện cho phiên bản chỉnh sửa mới nhất của dữ liệu hiện tại.
     */
    fun getVersion(): Long

    /**
     * Cập nhật phiên bản hiện tại của entity.
     * @param version Giá trị version mới
     */
    fun setVersion(version: Long)
}
