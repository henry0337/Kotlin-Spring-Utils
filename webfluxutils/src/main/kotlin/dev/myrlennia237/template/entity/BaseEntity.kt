@file:UseSerializers(LocalDateTimeSerializer::class)
@file:NullMarked

package dev.myrlennia237.template.entity

import dev.myrlennia237.internal.entity.Auditable
import dev.myrlennia237.internal.entity.Conflictable
import dev.myrlennia237.internal.entity.Restorable
import dev.myrlennia237.serializer.LocalDateTimeSerializer
import dev.myrlennia237.utils.JavaLocalDateTime
import dev.myrlennia237.utils.JavaSerializable
import kotlin.jvm.JvmName
import kotlin.time.Clock
import kotlin.uuid.ExperimentalUuidApi
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import org.jspecify.annotations.NullMarked
import org.jspecify.annotations.Nullable
import org.springframework.data.annotation.*
import org.springframework.data.relational.core.mapping.InsertOnlyProperty

/**
 * Base entity R2DBC với đầy đủ tính năng audit, soft delete và optimistic locking.
 *
 * Extend class này thay vì tự implement thủ công các trường audit:
 * - **Audit tự động**: `createdBy`, `createdDate`, `lastModifiedBy`, `lastModifiedDate`
 *   được điền bởi Spring Data R2DBC kết hợp với
 *   [dev.myrlennia237.config.AsyncAuditorAware].
 * - **Soft delete**: Gọi [markAsDeleted] thay vì xóa trực tiếp; dùng [restore] để khôi phục.
 *   Truy vấn cần tự lọc theo `deleted = 0` — thư viện không tự động áp filter này.
 * - **Optimistic locking**: Field `version` được Spring Data R2DBC tự tăng khi update,
 *   ngăn race condition khi nhiều transaction cùng chỉnh sửa một bản ghi.
 *
 * @param ID Kiểu của primary key (ví dụ: [Long], [java.util.UUID])
 *
 * @author <a href="https://github.com/henry0337">Myrlennia</a>
 */
@Serializable
@OptIn(ExperimentalUuidApi::class)
abstract class BaseEntity<ID>(
    @Id var id: ID? = null,
    @CreatedBy @InsertOnlyProperty var createdBy: String = "",
    @get:JvmName("getCreatedDateValue")
    @set:JvmName("setCreatedDateValue")
    @CreatedDate @InsertOnlyProperty var createdDate: JavaLocalDateTime = Clock.System.now()
        .toLocalDateTime(TimeZone.currentSystemDefault()).toJavaLocalDateTime(),
    @LastModifiedBy var lastModifiedBy: @Nullable String? = null,
    @get:JvmName("getLastModifiedDateValue")
    @set:JvmName("setLastModifiedDateValue")
    @LastModifiedDate var lastModifiedDate: @Nullable JavaLocalDateTime? = null,
    var deleted: Short = 0,
    var deletedAt: @Nullable JavaLocalDateTime? = null,
    @get:JvmName("getEntityVersion")
    @set:JvmName("setEntityVersion")
    @Version var version: Long = 0,
) : Auditable, Conflictable, Restorable, JavaSerializable {
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
