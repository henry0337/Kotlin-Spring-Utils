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
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.io.Serial
import java.util.UUID

/**
 * Khai báo một lớp mà bản thân nó không phải là **một Entity**, nhưng các cấu hình ánh xạ của nó sẽ được kế thừa bởi
 * các entity kế thừa từ nó.
 * @author <a href="https://github.com/henry0337">Muharux</a>
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseEntity(
    @Id
    var id: UUID? = null,

    @CreatedBy
    @Column(updatable = false)
    var createdBy: UUID? = null,

    @LastModifiedBy
    var lastModifiedBy: UUID? = null,

    var deleted: Short = 0,

    var deletedAt: JavaInstant? = null,

    @CreatedDate
    @Column(updatable = false)
    @get:JvmName("getCreatedDateValue")
    @set:JvmName("setCreatedDateValue")
    var createdDate: JavaInstant = JavaInstant.now(),

    @LastModifiedDate
    @get:JvmName("getLastModifiedDateValue")
    @set:JvmName("setLastModifiedDateValue")
    var lastModifiedDate: JavaInstant? = null,

    @Version
    @get:JvmName("getEntityVersion")
    @set:JvmName("setEntityVersion")
    var version: Long = 0
) : Auditable, Conflictable, Restorable, JavaSerializable {
    override fun getCreatedAuditor(): UUID? = createdBy
    override fun setCreatedAuditor(id: UUID?) {
        createdBy = id
    }

    override fun getCreatedDate(): JavaInstant = createdDate
    override fun setCreatedDate(creationDate: JavaInstant) {
        createdDate = creationDate
    }

    override fun getLastModifiedAuditor(): UUID? = lastModifiedBy
    override fun setLastModifiedAuditor(auditor: UUID?) {
        lastModifiedBy = auditor
    }

    override fun getLastModifiedDate(): JavaInstant? = lastModifiedDate
    override fun setLastModifiedDate(lastModifiedDate: JavaInstant?) {
        this.lastModifiedDate = lastModifiedDate
    }

    override fun getVersion(): Long = version
    override fun setVersion(version: Long) {
        this.version = version
    }

    override fun getRemovedState(): Short = deleted
    override fun setRemovedState(removed: Short) {
        deleted = removed
    }

    override fun getDeletedTimestamp(): JavaInstant? = deletedAt
    override fun setRemovedTimestamp(deletedAt: JavaInstant?) {
        this.deletedAt = deletedAt
    }

    companion object {
        @Serial
        private const val serialVersionUID: Long = 1L
    }
}
