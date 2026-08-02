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
 * Khai báo một contract cho các **Entity** mà bản thân nó không phải là một **Entity**, nhưng các cấu hình ánh xạ của
 * nó sẽ được kế thừa thông qua các entity sử dụng nó.
 *
 * @author <a href="https://github.com/henry0337">Myrlennia</a>
 * @see Auditable
 * @see Conflictable
 * @see Restorable
 * @see java.io.Serializable
 * @sample dev.myrlennia237.sample.Foo
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
public abstract class BaseEntity protected constructor(
    @Id
    public var id: UUID? = null,

    @CreatedBy
    @Column(updatable = false)
    public var createdBy: UUID? = null,

    @LastModifiedBy
    public var lastModifiedBy: UUID? = null,

    public var disabled: Boolean = false,

    public var lastDisabledAt: JavaInstant? = null,

    public var lastDisabledBy: UUID? = null,

    @CreatedDate
    @Column(updatable = false)
    @get:JvmName("getCreatedDateValue")
    @set:JvmName("setCreatedDateValue")
    public var createdDate: JavaInstant = JavaInstant.now(),

    @LastModifiedDate
    @get:JvmName("getLastModifiedDateValue")
    @set:JvmName("setLastModifiedDateValue")
    public var lastModifiedDate: JavaInstant? = null,

    @Version
    @get:JvmName("getEntityVersion")
    @set:JvmName("setEntityVersion")
    public var version: Long = 0
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

    override fun getDisabledState(): Boolean = disabled
    override fun setDisabledState(disabled: Boolean) {
        this.disabled = disabled
    }

    override fun getDisabledTimestamp(): JavaInstant? = lastDisabledAt
    override fun setDisabledTimestamp(disabledAt: JavaInstant?) {
        this.lastDisabledAt = disabledAt
    }

    override fun getDisabledBy(): UUID? = lastDisabledBy
    override fun setDisabledBy(by: UUID?) {
        this.lastDisabledBy = by
    }

    override fun toString(): String =
        "${this::class.simpleName}(id=$id, version=$version, disabled=$disabled)"

    public companion object {
        @Serial
        private const val serialVersionUID: Long = 1L
    }
}
