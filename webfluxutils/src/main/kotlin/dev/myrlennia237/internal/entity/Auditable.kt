package dev.myrlennia237.internal.entity

import java.time.Instant

/**
 * Chứa thông tin truy vết tới đối tượng thực hiện tinh chỉnh dữ liệu trên cơ sở dữ liệu.
 * @author <a href="https://github.com/henry0337">Muharux</a>
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
    fun getCreatedDate(): Instant

    /**
     * @param creationDate Thời điểm dữ liệu được tạo ra.
     */
    fun setCreatedDate(creationDate: Instant)

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
    fun getLastModifiedDate(): Instant?

    /**
     * @param lastModifiedDate Thời điểm chỉnh sửa gần nhất, hoặc `null` nếu muốn xóa giá trị.
     */
    fun setLastModifiedDate(lastModifiedDate: Instant?)
}
