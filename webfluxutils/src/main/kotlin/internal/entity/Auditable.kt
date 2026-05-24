package internal.entity

import utils.JavaLocalDateTime

/**
 * Contract mô tả các trường audit của một entity.
 *
 * Implementations thường lưu thông tin người tạo, thời điểm tạo, người sửa gần nhất
 * và thời điểm sửa gần nhất.
 *
 * @author <a href="https://github.com/henry0337">Myrlennia</a>
 */
internal interface Auditable {
    /**
     * Trả về người dùng hoặc hệ thống đã tạo entity này.
     *
     * @return Tên tác nhân tạo dữ liệu
     */
    fun getCreatedAuditor(): String

    /**
     * Cập nhật tác nhân đã tạo entity này.
     *
     * @param id Tên tác nhân tạo dữ liệu
     */
    fun setCreatedAuditor(id: String)

    /**
     * Trả về thời điểm entity này được tạo.
     *
     * @return Thời điểm tạo
     */
    fun getCreatedDate(): JavaLocalDateTime

    /**
     * Cập nhật thời điểm entity này được tạo.
     *
     * @param creationDate Thời điểm tạo mới
     */
    fun setCreatedDate(creationDate: JavaLocalDateTime)

    /**
     * Trả về người dùng hoặc hệ thống đã chỉnh sửa entity lần gần nhất.
     *
     * @return Tên tác nhân chỉnh sửa gần nhất, hoặc `null` nếu chưa có
     */
    fun getLastModifiedAuditor(): String?

    /**
     * Cập nhật tác nhân chỉnh sửa gần nhất của entity.
     *
     * @param auditor Tên tác nhân chỉnh sửa, hoặc `null` nếu muốn xóa giá trị
     */
    fun setLastModifiedAuditor(auditor: String?)

    /**
     * Trả về thời điểm entity được chỉnh sửa lần gần nhất.
     *
     * @return Thời điểm chỉnh sửa gần nhất, hoặc `null` nếu chưa có
     */
    fun getLastModifiedDate(): JavaLocalDateTime?

    /**
     * Cập nhật thời điểm chỉnh sửa gần nhất của entity.
     *
     * @param lastModifiedDate Thời điểm chỉnh sửa, hoặc `null` nếu muốn xóa giá trị
     */
    fun setLastModifiedDate(lastModifiedDate: JavaLocalDateTime?)
}
