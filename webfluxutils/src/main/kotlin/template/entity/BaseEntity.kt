@file:UseSerializers(LocalDateTimeSerializer::class)
@file:NullMarked

package template.entity

import utils.JavaLocalDateTime
import utils.JavaSerializable
import internal.entity.Auditable
import internal.entity.Conflictable
import internal.entity.Restorable
import serializer.LocalDateTimeSerializer
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import org.jspecify.annotations.NullMarked
import org.jspecify.annotations.Nullable
import org.springframework.data.annotation.*
import org.springframework.data.relational.core.mapping.InsertOnlyProperty
import kotlin.time.Clock
import kotlin.uuid.ExperimentalUuidApi

@Serializable
@OptIn(ExperimentalUuidApi::class)
abstract class BaseEntity<ID>(
    @Id var id: ID? = null,
    @CreatedBy @InsertOnlyProperty var createdBy: String = "",
    @CreatedDate @InsertOnlyProperty var createdDate: JavaLocalDateTime = Clock.System.now()
        .toLocalDateTime(TimeZone.currentSystemDefault()).toJavaLocalDateTime(),
    @LastModifiedBy var lastModifiedBy: @Nullable String? = null,
    @LastModifiedDate var lastModifiedDate: @Nullable JavaLocalDateTime? = null,
    var deleted: Short = 0,
    var deletedAt: @Nullable JavaLocalDateTime? = null,
    @Version var version: Long = 0,
) : Auditable, Conflictable, Restorable, JavaSerializable {
    override fun getCreatedAuditor(): String = createdBy

    override fun setCreatedAuditor(id: String) {
        this.createdBy = id
    }

    override fun getCreatedDate(): JavaLocalDateTime = createdDate

    override fun setCreatedDate(creationDate: JavaLocalDateTime) {
        this.createdDate = creationDate
    }

    override fun getLastModifiedAuditor(): String? = lastModifiedBy

    override fun setLastModifiedAuditor(auditor: String?) {
        this.lastModifiedBy = auditor
    }

    override fun getLastModifiedDate(): JavaLocalDateTime? = lastModifiedDate

    override fun setLastModifiedDate(lastModifiedDate: JavaLocalDateTime?) {
        this.lastModifiedDate = lastModifiedDate
    }

    override fun getVersion(): Long = version

    override fun setVersion(version: Long) {
        this.version = version
    }

    override fun getRemovedState(): Short = deleted

    override fun setRemovedState(removed: Short) {
        this.deleted = removed
    }

    override fun getDeletedTimestamp(): JavaLocalDateTime? = deletedAt

    override fun setRemovedTimestamp(deletedAt: JavaLocalDateTime?) {
        this.deletedAt = deletedAt
    }

    companion object {
        @java.io.Serial
        private const val serialVersionUID: Long = 1L
    }
}
