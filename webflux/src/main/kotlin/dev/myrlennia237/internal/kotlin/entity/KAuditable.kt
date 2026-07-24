package dev.myrlennia237.internal.kotlin.entity

import dev.myrlennia237.annotation.KotlinVariant
import kotlinx.datetime.Instant
import java.util.UUID

/**
 * Chứa thông tin truy vết tới đối tượng thực hiện tinh chỉnh dữ liệu trên cơ sở dữ liệu.
 * @author <a href="https://github.com/henry0337">Muharux</a>
 */
@KotlinVariant
internal interface KAuditable {
    /**
     * Đối tượng thực hiện tạo mới bản ghi.
     */
    var createdBy: UUID?

    /**
     * Thời gian bản ghi này được tạo ra.
     */
    var createdDate: Instant

    /**
     * Đối tượng cuối cùng thực hiện chỉnh sửa bản ghi này.
     */
    var lastModifiedBy: UUID?

    /**
     * Thời gian lần cuối bản ghi này được chỉnh sửa.
     */
    var lastModifiedDate: Instant?
}
