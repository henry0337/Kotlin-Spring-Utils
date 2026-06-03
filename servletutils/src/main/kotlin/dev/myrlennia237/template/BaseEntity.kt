package dev.myrlennia237.template

import dev.myrlennia237.internal.entity.Auditable
import dev.myrlennia237.internal.entity.Conflictable
import dev.myrlennia237.internal.entity.Restorable
import dev.myrlennia237.utils.JavaLocalDateTime
import dev.myrlennia237.utils.JavaSerializable
import jakarta.persistence.*
import kotlin.jvm.JvmName
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

/**
 * Base entity JPA với đầy đủ tính năng audit, soft delete và optimistic locking.
 *
 * Extend class này thay vì tự implement thủ công các trường audit:
 * - **Audit tự động**: `createdBy`, `createdDate`, `lastModifiedBy`, `lastModifiedDate`
 *   được điền bởi Spring Data JPA kết hợp với
 *   [dev.myrlennia237.config.AuditorAwareImpl].
 * - **Soft delete**: Gọi [markAsDeleted] thay vì xóa trực tiếp; dùng [restore] để khôi phục.
 *   Truy vấn cần tự lọc theo `deleted = 0` — thư viện không tự động áp filter này.
 * - **Optimistic locking**: Field `version` được JPA tự tăng khi update,
 *   ngăn race condition khi nhiều transaction cùng chỉnh sửa một bản ghi.
 *
 * @param ID Kiểu của primary key (ví dụ: [Long], [java.util.UUID])
 *
 * @author <a href="https://github.com/henry0338">Myrlennia</a>
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseEntity<ID>(
    @Id
    open var id: ID? = null,

    @CreatedBy
    @Column(updatable = false)
    open var createdBy: String = "",

    @LastModifiedBy
    open var lastModifiedBy: String? = null,

    open var deleted: Short = 0,

    open var deletedAt: JavaLocalDateTime? = null,
) : Auditable, Conflictable, Restorable, JavaSerializable {

    // Khai báo ở class body để dùng @JvmName — tránh clash với getter/setter
    // được sinh tự động từ tên property (getCreatedDate, setLastModifiedDate, getVersion).
    @get:JvmName("getCreatedDateValue")
    @set:JvmName("setCreatedDateValue")
    @CreatedDate
    @Column(updatable = false)
    var createdDate: JavaLocalDateTime = LocalDateTime.now()

    @get:JvmName("getLastModifiedDateValue")
    @set:JvmName("setLastModifiedDateValue")
    @LastModifiedDate
    var lastModifiedDate: JavaLocalDateTime? = null

    @get:JvmName("getEntityVersion")
    @set:JvmName("setEntityVersion")
    @Version
    var version: Long = 0

    override fun getCreatedAuditor(): String = createdBy
    override fun setCreatedAuditor(id: String) { createdBy = id }
    override fun getCreatedDate(): JavaLocalDateTime = createdDate
    override fun setCreatedDate(creationDate: JavaLocalDateTime) { createdDate = creationDate }
    override fun getLastModifiedAuditor(): String? = lastModifiedBy
    override fun setLastModifiedAuditor(auditor: String?) { lastModifiedBy = auditor }
    override fun getLastModifiedDate(): JavaLocalDateTime? = lastModifiedDate
    override fun setLastModifiedDate(lastModifiedDate: JavaLocalDateTime?) { this.lastModifiedDate = lastModifiedDate }
    override fun getVersion(): Long = version
    override fun setVersion(version: Long) { this.version = version }
    override fun getRemovedState(): Short = deleted
    override fun setRemovedState(removed: Short) { deleted = removed }
    override fun getDeletedTimestamp(): JavaLocalDateTime? = deletedAt
    override fun setRemovedTimestamp(deletedAt: JavaLocalDateTime?) { this.deletedAt = deletedAt }

    companion object {
        @java.io.Serial
        private const val serialVersionUID: Long = 1L
    }
}
