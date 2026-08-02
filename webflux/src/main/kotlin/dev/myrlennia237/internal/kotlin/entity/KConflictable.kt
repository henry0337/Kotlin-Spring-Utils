package dev.myrlennia237.internal.kotlin.entity

import dev.myrlennia237.annotation.KotlinVariant

/**
 * Chỉ định một **Aggregate Root** sẽ sử dụng cơ chế **khóa lạc quan (optimistic locking)** lên các dữ liệu mà nó quản lý.
 * @author <a href="https://github.com/henry0337">Myrlennia</a>
 * @see <a href="https://docs.spring.io/spring-data/relational/reference/jdbc/entity-persistence.html#jdbc.entity-persistence.optimistic-locking">
 *     Lưu trữ dữ liệu lên Entity
 *     </a>
 */
@KotlinVariant
internal interface KConflictable {
    /**
     * Một số nguyên 64-bit đại diện cho phiên bản chỉnh sửa mới nhất của dữ liệu hiện tại.
     */
    var version: Long

    /**
     * Tăng phiên bản hiện tại lên 1.
     */
    fun increaseVersion() {
        version++
    }
}
