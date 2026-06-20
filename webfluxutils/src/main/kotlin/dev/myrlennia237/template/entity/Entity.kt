@file:UseSerializers(InstantSerializer::class)
@file:NullMarked

package dev.myrlennia237.template.entity

import dev.myrlennia237.JavaInstant
import dev.myrlennia237.JavaSerializable
import java.time.Instant
import dev.myrlennia237.config.InstantSerializer
import dev.myrlennia237.internal.entity.Auditable
import dev.myrlennia237.internal.entity.Conflictable
import dev.myrlennia237.internal.entity.Restorable
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import org.jspecify.annotations.NullMarked
import org.jspecify.annotations.Nullable
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.annotation.Version
import org.springframework.data.relational.core.mapping.InsertOnlyProperty
import java.util.UUID
import kotlin.uuid.ExperimentalUuidApi

/**
 * @author <a href="https://github.com/henry0337">Muharux</a>
 */
@Serializable
@OptIn(ExperimentalUuidApi::class)
abstract class Entity(
    @Id @Contextual
    var id: UUID? = null,

    @CreatedBy @InsertOnlyProperty
    var createdBy: String = "",

    @get:JvmName("getCreatedDateValue")
    @set:JvmName("setCreatedDateValue")
    @CreatedDate @InsertOnlyProperty
    var createdDate: JavaInstant = Instant.now(),

    @LastModifiedBy
    var lastModifiedBy: @Nullable String? = null,

    @get:JvmName("getLastModifiedDateValue")
    @set:JvmName("setLastModifiedDateValue")
    @LastModifiedDate
    var lastModifiedDate: @Nullable JavaInstant? = null,

    var deleted: Short = 0,

    var deletedAt: @Nullable JavaInstant? = null,

    @get:JvmName("getEntityVersion")
    @set:JvmName("setEntityVersion")
    @Version var version: Long = 0,
) : Auditable, Conflictable, Restorable, JavaSerializable {
    override fun getCreatedAuditor(): String = createdBy
    override fun setCreatedAuditor(id: String) {
        createdBy = id
    }

    override fun getCreatedDate(): JavaInstant = createdDate
    override fun setCreatedDate(creationDate: JavaInstant) {
        createdDate = creationDate
    }

    override fun getLastModifiedAuditor(): String? = lastModifiedBy
    override fun setLastModifiedAuditor(auditor: String?) {
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
        @java.io.Serial
        private const val serialVersionUID: Long = 1L
    }
}
