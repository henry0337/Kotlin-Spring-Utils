package dev.myrlennia237.template.entity

import dev.myrlennia237.JavaOffsetDateTime
import dev.myrlennia237.JavaSerializable
import dev.myrlennia237.internal.entity.Auditable
import dev.myrlennia237.internal.entity.Conflictable
import dev.myrlennia237.internal.entity.Restorable
import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass
import jakarta.persistence.Version
import org.hibernate.annotations.SQLDelete
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.io.Serial
import java.time.OffsetDateTime
import java.time.ZoneOffset

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
 * @author <a href="https://github.com/henry0337">Myrlennia</a>
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
@SQLDelete(sql = "deleted = 0")
abstract class BaseEntity<ID>(
    @Id
    open var id: ID? = null,

    @CreatedBy
    @Column(updatable = false)
    open var createdBy: String = "",

    @LastModifiedBy
    open var lastModifiedBy: String? = null,

    open var deleted: Short = 0,

    open var deletedAt: JavaOffsetDateTime? = null,
) : Auditable, Conflictable, Restorable, JavaSerializable {
    @get:JvmName("getCreatedDateValue")
    @set:JvmName("setCreatedDateValue")
    @CreatedDate
    @Column(updatable = false)
    var createdDate: JavaOffsetDateTime = OffsetDateTime.now(ZoneOffset.UTC)

    @get:JvmName("getLastModifiedDateValue")
    @set:JvmName("setLastModifiedDateValue")
    @LastModifiedDate
    var lastModifiedDate: JavaOffsetDateTime? = null

    @get:JvmName("getEntityVersion")
    @set:JvmName("setEntityVersion")
    @Version
    var version: Long = 0

    override fun getCreatedAuditor(): String = createdBy
    override fun setCreatedAuditor(id: String) { createdBy = id }
    override fun getCreatedDate(): JavaOffsetDateTime = createdDate
    override fun setCreatedDate(creationDate: JavaOffsetDateTime) { createdDate = creationDate }
    override fun getLastModifiedAuditor(): String? = lastModifiedBy
    override fun setLastModifiedAuditor(auditor: String?) { lastModifiedBy = auditor }
    override fun getLastModifiedDate(): JavaOffsetDateTime? = lastModifiedDate
    override fun setLastModifiedDate(lastModifiedDate: JavaOffsetDateTime?) { this.lastModifiedDate = lastModifiedDate }
    override fun getVersion(): Long = version
    override fun setVersion(version: Long) { this.version = version }
    override fun getRemovedState(): Short = deleted
    override fun setRemovedState(removed: Short) { deleted = removed }
    override fun getDeletedTimestamp(): JavaOffsetDateTime? = deletedAt
    override fun setRemovedTimestamp(deletedAt: JavaOffsetDateTime?) { this.deletedAt = deletedAt }

    companion object {
        @Serial
        private const val serialVersionUID: Long = 1L
    }
}
