package dev.myrlennia237.internal.entity

import dev.myrlennia237.JavaInstant

/**
 * Chứa thông tin truy vết tới đối tượng thực hiện tinh chỉnh dữ liệu trên cơ sở dữ liệu.
 * @author <a href="https://github.com/henry0338">Myrlennia</a>
 */
internal interface Auditable {
    /**
     * @return Đối tượng tạo dữ liệu.
     */
    fun getCreatedAuditor(): String

    /**
     * @param id Đối tượng tạo dữ liệu.
     */
    fun setCreatedAuditor(id: String)

    /**
     * @return Thời điểm dữ liệu được tạo ra.
     */
    fun getCreatedDate(): JavaInstant

    /**
     * @param creationDate Thời điểm dữ liệu được tạo ra.
     */
    fun setCreatedDate(creationDate: JavaInstant)

    /**
     * @return Tên tác nhân chỉnh sửa gần nhất, hoặc `null` nếu chưa có.
     */
    fun getLastModifiedAuditor(): String?

    /**
     * @param auditor Tên tác nhân chỉnh sửa, hoặc `null` nếu muốn xóa giá trị.
     */
    fun setLastModifiedAuditor(auditor: String?)

    /**
     * @return Thời điểm chỉnh sửa gần nhất, hoặc `null` nếu chưa có.
     */
    fun getLastModifiedDate(): JavaInstant?

    /**
     * @param lastModifiedDate Thời điểm chỉnh sửa gần nhất, hoặc `null` nếu muốn xóa giá trị.
     */
    fun setLastModifiedDate(lastModifiedDate: JavaInstant?)
}
