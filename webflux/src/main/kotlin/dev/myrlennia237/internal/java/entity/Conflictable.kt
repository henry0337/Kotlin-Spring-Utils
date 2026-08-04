package dev.myrlennia237.internal.java.entity

/**
 * Chỉ định một **Entity** sẽ sử dụng cơ chế **optimistic locking** lên các dữ liệu mà nó quản lý.
 * 
 * @author <a href="https://github.com/henry0337">Myrlennia</a>
 * @see <a href="https://docs.spring.io/spring-data/relational/reference/jdbc/entity-persistence.html#jdbc.entity-persistence.optimistic-locking">
 *     Lưu trữ dữ liệu lên Entity
 *     </a>
 */
internal interface Conflictable {
/**
     * Phiên bản dữ liệu hiện tại của bản ghi.
     */
    fun getVersion(): Long

    /**
     * Cập nhật phiên bản hiện tại của bản ghi.
     * @param version Giá trị version mới
     */
    fun setVersion(version: Long)
    
    /**
     * Tăng phiên bản hiện tại lên 1.
     */
    fun increaseVersion() {
        setVersion(getVersion() + 1)
    }
}
