package dev.myrlennia237.internal.java.entity

import dev.myrlennia237.JavaInstant
import java.util.UUID

/**
 * Đánh dấu một Entity sẽ áp dụng cơ chế xóa mềm lên data của chúng thay vì xóa hoàn toàn, đồng thời áp dụng cơ chế
 * khôi phục tương ứng cho mỗi dữ liệu đó.
 * @author <a href="https://github.com/henry0337">Myrlennia</a>
 */
internal interface Restorable {
    /**
     * Trả về trạng thái xóa logic hiện tại của entity.
     *
     * @return Giá trị trạng thái đã lưu
     */
    fun getDisabledState(): Boolean

    /**
     * Cập nhật trạng thái vô hiệu hóa (xóa mềm) của entity.
     *
     * @param disabled Giá trị trạng thái mới
     */
    fun setDisabledState(disabled: Boolean)

    /**
     * Trả về thời điểm entity bị đánh dấu đã xóa.
     *
     * @return Thời điểm xóa, hoặc `null` nếu entity chưa bị xóa
     */
    fun getDisabledTimestamp(): JavaInstant?

    /**
     * Cập nhật thời điểm entity bị đánh dấu vô hiệu hóa.
     *
     * @param disabledAt Thời điểm vô hiệu hóa, hoặc `null` nếu muốn xóa dấu thời gian
     */
    fun setDisabledTimestamp(disabledAt: JavaInstant?)

    /**
     * Trả về đối tượng gần đây nhất thực hiện vô hiệu hóa entity này.
     *
     * @return UUID của đối tượng đó, hoặc `null` nếu entity chưa bị vô hiệu hóa
     */
    fun getDisabledBy(): UUID?

    /**
     * Cập nhật đối tượng thực hiện vô hiệu hóa entity này.
     *
     * @param by UUID của đối tượng đó, hoặc `null` nếu muốn xóa giá trị
     */
    fun setDisabledBy(by: UUID?)

    /**
     * Kiểm tra entity hiện tại có đang bị xóa logic hay không.
     *
     * @return `true`/`false` tương ứng.
     */
    fun isDisabled(): Boolean = getDisabledState()

    /**
     * Đánh dấu entity hiện tại là đã xóa logic.
     *
     * Hàm này sẽ đặt trạng thái vô hiệu hóa về `true`, gán thời điểm vô hiệu hóa, và ghi nhận đối tượng thực hiện
     * thao tác.
     *
     * @param by UUID của đối tượng thực hiện vô hiệu hóa, hoặc `null` nếu không xác định
     * @param at Thời điểm vô hiệu hóa; mặc định là thời gian hiện tại. Cho phép truyền giá trị cố định để test
     *   xác định (deterministic).
     */
    fun markAsDisabled(by: UUID? = null, at: JavaInstant = JavaInstant.now()) {
        setDisabledState(true)
        setDisabledTimestamp(at)
        setDisabledBy(by)
    }

    /**
     * Khôi phục entity về trạng thái chưa xóa.
     *
     * Hàm này chỉ đặt lại flag [getDisabledState] về `false`.
     * [getDisabledTimestamp] và [getDisabledBy] được giữ nguyên để lưu vết lần vô hiệu hóa gần nhất.
     */
    fun restore() {
        setDisabledState(false)
    }
}
