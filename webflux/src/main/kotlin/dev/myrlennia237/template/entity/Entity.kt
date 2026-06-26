@file:NullMarked

package dev.myrlennia237.template.entity

import dev.myrlennia237.JavaInstant
import dev.myrlennia237.JavaSerializable
import dev.myrlennia237.internal.entity.Auditable
import dev.myrlennia237.internal.entity.Conflictable
import dev.myrlennia237.internal.entity.Restorable
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

/**
 * Đánh dấu mỗi lớp kế thừa lớp này là một **Entity** - đại diện cho một bảng trong cơ sở dữ liệu.
 *
 * Hỗ trợ:
 * - Xóa mềm (thông qua [Restorable])
 * - Auditing (thông qua [Auditable])
 * - Bảo vệ tính toàn vẹn của dữ liệu (thông qua [Conflictable])
 *
 * @author <a href="https://github.com/henry0337">Muharux</a>
 */
public abstract class Entity(

    /**
     * Identifier duy nhất cho bản ghi hiện tại.
     */
    @Id
    public var id: @Nullable UUID? = null,

    /**
     * Đối tượng thực hiện tạo bản ghi này.
     */
    @CreatedBy @InsertOnlyProperty
    public var createdBy: @Nullable UUID? = null,

    /**
     * Thời gian bản ghi này được tạo ra.
     */
    @get:JvmName("getCreatedDateValue")
    @set:JvmName("setCreatedDateValue")
    @CreatedDate @InsertOnlyProperty
    public var createdDate: JavaInstant = JavaInstant.now(),

    /**
     * Đối tượng cuối cùng thực hiện chỉnh sửa bản ghi này.
     */
    @LastModifiedBy
    public var lastModifiedBy: @Nullable UUID? = null,

    /**
     * Thời gian lần cuối bản ghi này được chỉnh sửa.
     */
    @get:JvmName("getLastModifiedDateValue")
    @set:JvmName("setLastModifiedDateValue")
    @LastModifiedDate
    public var lastModifiedDate: @Nullable JavaInstant? = null,

    /**
     * Trạng thái sử dụng hiện tại của bản ghi.
     */
    public var disabled: Boolean = false,

    /**
     * Thời gian lần cuối vô hiệu hóa bản ghi này.
     */
    public var lastDisabledAt: @Nullable JavaInstant? = null,

    /**
     * Đối tượng gần đây nhất thực hiện vô hiệu hóa bản ghi này.
     */
    public var lastDisabledBy: @Nullable UUID? = null,

    /**
     * Phiên bản hiện tại của dữ liệu.
     *
     * Dùng để kiểm tra tính toàn vẹn của dữ liệu khi được tương tác từ nhiều nguồn.
     */
    @get:JvmName("getEntityVersion")
    @set:JvmName("setEntityVersion")
    @Version public var version: Long = 0
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
    override fun setDisabledState(removed: Boolean) {
        disabled = removed
    }

    override fun getDisabledTimestamp(): JavaInstant? = lastDisabledAt
    override fun setDisabledTimestamp(deletedAt: JavaInstant?) {
        this.lastDisabledAt = deletedAt
    }

    override fun getDisabledBy(): UUID? = lastDisabledBy
    override fun setDisabledBy(by: UUID?) {
        this.lastDisabledBy = by
    }

    public companion object {
        @java.io.Serial
        private const val serialVersionUID: Long = 1L
    }
}
