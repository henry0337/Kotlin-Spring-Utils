package dev.myrlennia237.internal.java.entity

import dev.myrlennia237.JavaInstant
import java.util.UUID

/**
 * Chứa thông tin truy vết tới đối tượng thực hiện tinh chỉnh dữ liệu trên cơ sở dữ liệu.
 * @author <a href="https://github.com/henry0337">Muharux</a>
 */
internal interface Auditable {
    /**
     * Đối tượng thực hiện tạo mới bản ghi.
     */
    fun getCreatedAuditor(): UUID?

    /**
     * @param id Đối tượng thực hiện tạo mới bản ghi.
     */
    fun setCreatedAuditor(id: UUID?)

    /**
     * @return Thời gian bản ghi này được tạo ra.
     */
    fun getCreatedDate(): JavaInstant

    /**
     * @param creationDate Thời gian bản ghi này được tạo ra.
     */
    fun setCreatedDate(creationDate: JavaInstant)

    /**
     * Đối tượng cuối cùng thực hiện chỉnh sửa bản ghi này.
     * @return UUID của đối tượng chỉnh sửa gần nhất, hoặc `null` nếu chưa có.
     */
    fun getLastModifiedAuditor(): UUID?

    /**
     * @param auditor UUID của đối tượng chỉnh sửa gần nhất, hoặc `null` nếu chưa có.
     */
    fun setLastModifiedAuditor(auditor: UUID?)

    /**
     * Thời gian lần cuối bản ghi này được chỉnh sửa.
     */
    fun getLastModifiedDate(): JavaInstant?

    /**
     * @param lastModifiedDate Thời điểm chỉnh sửa gần nhất, hoặc `null` nếu muốn xóa giá trị.
     */
    fun setLastModifiedDate(lastModifiedDate: JavaInstant?)
}
