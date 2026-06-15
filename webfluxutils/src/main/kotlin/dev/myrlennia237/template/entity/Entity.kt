@file:UseSerializers(OffsetDateTimeSerializer::class)
@file:NullMarked

package dev.myrlennia237.template.entity

import dev.myrlennia237.internal.entity.Auditable
import dev.myrlennia237.internal.entity.Conflictable
import dev.myrlennia237.internal.entity.Restorable
import dev.myrlennia237.serializer.OffsetDateTimeSerializer
import dev.myrlennia237.JavaOffsetDateTime
import dev.myrlennia237.JavaSerializable
import java.time.OffsetDateTime
import java.time.ZoneOffset
import kotlin.jvm.JvmName
import kotlin.uuid.ExperimentalUuidApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import org.jspecify.annotations.NullMarked
import org.jspecify.annotations.Nullable
import org.springframework.data.annotation.*
import org.springframework.data.relational.core.mapping.InsertOnlyProperty

/**
 * @author <a href="https://github.com/henry0337">Ademia</a>
 */
@Serializable
@OptIn(ExperimentalUuidApi::class)
abstract class Entity<ID>(
    @Id var id: ID? = null,
    @CreatedBy @InsertOnlyProperty var createdBy: String = "",
    @get:JvmName("getCreatedDateValue")
    @set:JvmName("setCreatedDateValue")
    @CreatedDate @InsertOnlyProperty var createdDate: JavaOffsetDateTime = OffsetDateTime.now(ZoneOffset.UTC),
    @LastModifiedBy var lastModifiedBy: @Nullable String? = null,
    @get:JvmName("getLastModifiedDateValue")
    @set:JvmName("setLastModifiedDateValue")
    @LastModifiedDate var lastModifiedDate: @Nullable JavaOffsetDateTime? = null,
    var deleted: Short = 0,
    var deletedAt: @Nullable JavaOffsetDateTime? = null,
    @get:JvmName("getEntityVersion")
    @set:JvmName("setEntityVersion")
    @Version var version: Long = 0,
) : Auditable, Conflictable, Restorable, JavaSerializable {
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
        @java.io.Serial
        private const val serialVersionUID: Long = 1L
    }
}
