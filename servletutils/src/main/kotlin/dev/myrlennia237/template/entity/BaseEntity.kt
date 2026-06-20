package dev.myrlennia237.template.entity

import dev.myrlennia237.JavaInstant
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
import java.time.Instant

/**
 * @author <a href="https://github.com/henry0338">Myrlennia</a>
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

    open var deletedAt: JavaInstant? = null,
) : Auditable, Conflictable, Restorable, JavaSerializable {
    @get:JvmName("getCreatedDateValue")
    @set:JvmName("setCreatedDateValue")
    @CreatedDate
    @Column(updatable = false)
    var createdDate: JavaInstant = Instant.now()

    @get:JvmName("getLastModifiedDateValue")
    @set:JvmName("setLastModifiedDateValue")
    @LastModifiedDate
    var lastModifiedDate: JavaInstant? = null

    @get:JvmName("getEntityVersion")
    @set:JvmName("setEntityVersion")
    @Version
    var version: Long = 0

    override fun getCreatedAuditor(): String = createdBy
    override fun setCreatedAuditor(id: String) { createdBy = id }
    override fun getCreatedDate(): JavaInstant = createdDate
    override fun setCreatedDate(creationDate: JavaInstant) { createdDate = creationDate }
    override fun getLastModifiedAuditor(): String? = lastModifiedBy
    override fun setLastModifiedAuditor(auditor: String?) { lastModifiedBy = auditor }
    override fun getLastModifiedDate(): JavaInstant? = lastModifiedDate
    override fun setLastModifiedDate(lastModifiedDate: JavaInstant?) { this.lastModifiedDate = lastModifiedDate }
    override fun getVersion(): Long = version
    override fun setVersion(version: Long) { this.version = version }
    override fun getRemovedState(): Short = deleted
    override fun setRemovedState(removed: Short) { deleted = removed }
    override fun getDeletedTimestamp(): JavaInstant? = deletedAt
    override fun setRemovedTimestamp(deletedAt: JavaInstant?) { this.deletedAt = deletedAt }

    companion object {
        @Serial
        private const val serialVersionUID: Long = 1L
    }
}
