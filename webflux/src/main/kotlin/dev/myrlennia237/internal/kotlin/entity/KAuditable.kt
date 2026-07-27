package dev.myrlennia237.internal.kotlin.entity

import dev.myrlennia237.annotation.KotlinVariant
import kotlin.time.Instant
import kotlin.uuid.Uuid

/**
 * Chứa thông tin truy vết tới đối tượng thực hiện tinh chỉnh dữ liệu trên cơ sở dữ liệu.
 * @author <a href="https://github.com/henry0337">Muharux</a>
 */
@KotlinVariant
internal interface KAuditable {
    /**
     * Đối tượng thực hiện tạo mới bản ghi.
     */
    var createdBy: Uuid?

    /**
     * Thời gian bản ghi này được tạo ra.
     */
    var createdDate: Instant

    /**
     * Đối tượng cuối cùng thực hiện chỉnh sửa bản ghi này.
     */
    var lastModifiedBy: Uuid?

    /**
     * Thời gian lần cuối bản ghi này được chỉnh sửa.
     */
    var lastModifiedDate: Instant?
}
