package dev.myrlennia237.template.entity

import dev.myrlennia237.annotation.ExperimentalKotlinVariantApi
import dev.myrlennia237.annotation.KotlinVariant
import dev.myrlennia237.internal.kotlin.entity.KAuditable
import dev.myrlennia237.internal.kotlin.entity.KConflictable
import dev.myrlennia237.internal.kotlin.entity.KRestorable
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.annotation.Version
import org.springframework.data.relational.core.mapping.InsertOnlyProperty
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import java.util.UUID

/**
 * Khai báo một lớp mà bản thân nó không phải là một **Entity**, nhưng các cấu hình ánh xạ của nó sẽ được kế thừa bởi
 * các entity kế thừa từ nó.
 *
 * **Ghi chú**: Phiên bản này chỉ hỗ trợ API tiêu chuẩn của Kotlin, không dành cho các đối tượng gọi tới là Java.
 *
 * Hỗ trợ:
 * - Xóa mềm (thông qua [KRestorable])
 * - Auditing (thông qua [KAuditable])
 * - Bảo vệ tính toàn vẹn của dữ liệu (thông qua [KConflictable])
 *
 * @author <a href="https://github.com/henry0337">Muharux</a>
 */
@Serializable
@KotlinVariant
@ExperimentalKotlinVariantApi
public abstract class KEntity protected constructor(
    @Id @Contextual
    public var id: UUID? = null,

    @CreatedBy @InsertOnlyProperty @Contextual
    public override var createdBy: UUID? = null,

    @CreatedDate @InsertOnlyProperty
    public override var createdDate: Instant = Clock.System.now(),

    @LastModifiedBy @Contextual
    public override var lastModifiedBy: UUID? = null,

    @LastModifiedDate
    public override var lastModifiedDate: Instant? = null,

    public override var disabled: Boolean = false,

    public override var lastDisabledAt: Instant? = null,

    @Contextual
    public override var lastDisabledBy: UUID? = null,

    @Version
    public override var version: Long = 0
) : KAuditable, KConflictable, KRestorable {
    override fun toString(): String =
        "${this::class.simpleName}(id=$id, version=$version, disabled=$disabled)"
}