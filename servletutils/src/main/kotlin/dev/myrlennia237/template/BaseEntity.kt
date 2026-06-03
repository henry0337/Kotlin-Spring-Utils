package dev.myrlennia237.template

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.io.Serializable
import java.time.LocalDateTime

/**
 * Base entity JPA với đầy đủ tính năng audit, soft delete và optimistic locking.
 *
 * Phiên bản servlet (blocking) của
 * [dev.myrlennia237.template.entity.BaseEntity][webfluxutils BaseEntity] —
 * dùng cho ứng dụng Spring MVC truyền thống với JPA thay vì R2DBC.
 *
 * Để audit tự động hoạt động, cần bật `@EnableJpaAuditing` trong application
 * và cung cấp bean [org.springframework.data.domain.AuditorAware].
 *
 * @param ID Kiểu của primary key (ví dụ: [Long], [java.util.UUID])
 *
 * @author <a href="https://github.com/henry0337">Myrlennia</a>
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseEntity<ID>(
    @Id
    open var id: ID? = null,

    @CreatedBy
    @Column(updatable = false)
    open var createdBy: String = "",

    @CreatedDate
    @Column(updatable = false)
    open var createdDate: LocalDateTime = LocalDateTime.now(),

    @LastModifiedBy
    open var lastModifiedBy: String? = null,

    @LastModifiedDate
    open var lastModifiedDate: LocalDateTime? = null,

    open var deleted: Short = 0,

    open var deletedAt: LocalDateTime? = null,

    @Version
    open var version: Long = 0
) : Serializable {

    /**
     * Kiểm tra entity hiện tại có đang bị xóa logic hay không.
     *
     * @return `true` nếu trạng thái xóa là `1`, ngược lại `false`
     */
    fun isDeleted(): Boolean = deleted.toInt() == 1

    /**
     * Đánh dấu entity hiện tại là đã xóa logic.
     *
     * Đặt `deleted = 1` và gán `deletedAt` là thời điểm hiện tại.
     */
    fun markAsDeleted() {
        deleted = 1
        deletedAt = LocalDateTime.now()
    }

    /**
     * Khôi phục entity về trạng thái chưa xóa.
     *
     * Đặt `deleted = 0` và xóa `deletedAt`.
     */
    fun restore() {
        deleted = 0
        deletedAt = null
    }

    companion object {
        @java.io.Serial
        private const val serialVersionUID: Long = 1L
    }
}
