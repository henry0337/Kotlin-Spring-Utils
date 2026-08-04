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

    /**
     * ID duy nhất cho bản ghi hiện tại.
     */
    @Id
    public var id: UUID? = null,

    /**
     * Thời gian bản ghi này được **tạo mới** lần đầu.
     * 
     * Giá trị mặc định: `java.util.Instant.now()`
     */
    @CreatedDate
    @Column(updatable = false)
    @get:JvmSynthetic
    @set:JvmSynthetic
    public var createdAt: JavaInstant = JavaInstant.now(),

    /**
     * Đối tượng thực hiện **tạo mới** bản ghi này.
     */
    @CreatedBy
    @Column(updatable = false)
    @get:JvmSynthetic
    @set:JvmSynthetic
    public var createdBy: UUID? = null,

    /**
     * Thời gian lần cuối bản ghi này được **chỉnh sửa**.
     */
    @LastModifiedDate
    @get:JvmSynthetic
    @set:JvmSynthetic
    public var lastModifiedAt: JavaInstant? = null,

    /**
     * Đối tượng cuối cùng thực hiện **chỉnh sửa** bản ghi này.
     */
    @LastModifiedBy
    @get:JvmSynthetic
    @set:JvmSynthetic
    public var lastModifiedBy: UUID? = null,

    /**
     * Liệu bản ghi có thể được sử dụng ở hiện tại không?
     * 
     * Giá trị mặc định: `true`
     */
    @get:JvmSynthetic
    @set:JvmSynthetic
    public var enabled: Boolean = true,

    /**
     * Thời gian lần cuối **vô hiệu hóa** bản ghi này.
     */
    @get:JvmSynthetic
    @set:JvmSynthetic
    public var lastDisabledAt: JavaInstant? = null,

    /**
     * Đối tượng cuối cùng thực hiện **vô hiệu hóa** bản ghi này.
     */
    @get:JvmSynthetic
    @set:JvmSynthetic
    public var lastDisabledBy: UUID? = null,

    /**
     * Phiên bản hiện tại của dữ liệu.
     *
     * Dùng để kiểm tra tính toàn vẹn của dữ liệu khi được tương tác từ nhiều nguồn.
     */
    @Version
    @get:JvmName("getRecordVersion")
    @set:JvmName("setRecordVersion")
    @get:JvmSynthetic
    @set:JvmSynthetic
    public var version: Long = 0
) : Auditable, Conflictable, Restorable, JavaSerializable {
    override fun getCreatedAuditor(): UUID? = createdBy
    override fun setCreatedAuditor(id: UUID?) {
        createdBy = id
    }

    override fun getCreatedTimestamp(): JavaInstant = createdAt
    override fun setCreatedTimestamp(timestamp: JavaInstant) {
        createdAt = timestamp
    }

    override fun getLastModifiedAuditor(): UUID? = lastModifiedBy
    override fun setLastModifiedAuditor(auditor: UUID?) {
        lastModifiedBy = auditor
    }

    override fun getLastModifiedTimestamp(): JavaInstant? = lastModifiedAt
    override fun setLastModifiedTimestamp(timestamp: JavaInstant?) {
        this.lastModifiedAt = timestamp
    }

    override fun getVersion(): Long = version
    override fun setVersion(version: Long) {
        this.version = version
    }

    override fun getDisabledState(): Boolean = !enabled
    override fun setDisabledState(state: Boolean) {
        enabled = !state
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
        "${this::class.simpleName}(id=$id, version=$version, enabled=$enabled)"

    public companion object {
        @Serial
        private const val serialVersionUID: Long = 1L
    }
}
