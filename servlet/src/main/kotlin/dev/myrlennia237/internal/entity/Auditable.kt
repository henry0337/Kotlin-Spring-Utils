package dev.myrlennia237.internal.entity

import dev.myrlennia237.JavaInstant
import java.util.UUID

/**
 * Chứa thông tin truy vết tới đối tượng thực hiện tinh chỉnh dữ liệu trên cơ sở dữ liệu.
 * @author <a href="https://github.com/henry0337">Muharux</a>
 */
internal interface Auditable {
    /**
     * @return UUID của tác nhân tạo dữ liệu.
     */
    fun getCreatedAuditor(): UUID?

    /**
     * @param id UUID của tác nhân tạo dữ liệu.
     */
    fun setCreatedAuditor(id: UUID?)

    /**
     * @return Thời điểm dữ liệu được tạo ra.
     */
    fun getCreatedDate(): JavaInstant

    /**
     * @param creationDate Thời điểm dữ liệu được tạo ra.
     */
    fun setCreatedDate(creationDate: JavaInstant)

    /**
     * @return UUID của tác nhân chỉnh sửa gần nhất, hoặc `null` nếu chưa có.
     */
    fun getLastModifiedAuditor(): UUID?

    /**
     * @param auditor UUID của tác nhân chỉnh sửa, hoặc `null` nếu muốn xóa giá trị.
     */
    fun setLastModifiedAuditor(auditor: UUID?)

    /**
     * @return Thời điểm chỉnh sửa gần nhất, hoặc `null` nếu chưa có.
     */
    fun getLastModifiedDate(): JavaInstant?

    /**
     * @param lastModifiedDate Thời điểm chỉnh sửa gần nhất, hoặc `null` nếu muốn xóa giá trị.
     */
    fun setLastModifiedDate(lastModifiedDate: JavaInstant?)
}
