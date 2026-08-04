package dev.myrlennia237.internal.entity

import dev.myrlennia237.JavaInstant
import java.util.UUID

/**
 * Chứa thông tin truy vết tới đối tượng thực hiện tinh chỉnh dữ liệu trên cơ sở dữ liệu.
 * 
 * @author <a href="https://github.com/henry0337">Myrlennia</a>
 */
internal interface Auditable {
    /**
     * @return UUID của đối tượng tạo dữ liệu.
     */
    fun getCreatedAuditor(): UUID?

    /**
     * @param id UUID của đối tượng tạo dữ liệu.
     */
    fun setCreatedAuditor(id: UUID?)

    /**
     * @return Thời điểm dữ liệu được tạo ra.
     */
    fun getCreatedTimestamp(): JavaInstant

    /**
     * @param timestamp Thời điểm dữ liệu được tạo ra.
     */
    fun setCreatedTimestamp(timestamp: JavaInstant)

    /**
     * @return UUID của đối tượng chỉnh sửa gần nhất, hoặc `null` nếu chưa có.
     */
    fun getLastModifiedAuditor(): UUID?

    /**
     * @param auditor UUID của đối tượng chỉnh sửa, hoặc `null` nếu muốn xóa giá trị.
     */
    fun setLastModifiedAuditor(auditor: UUID?)

    /**
     * @return Thời điểm chỉnh sửa gần nhất, hoặc `null` nếu chưa có.
     */
    fun getLastModifiedTimestamp(): JavaInstant?

    /**
     * @param timestamp Thời điểm chỉnh sửa gần nhất, hoặc `null` nếu muốn xóa giá trị.
     */
    fun setLastModifiedTimestamp(timestamp: JavaInstant?)
}
