package dev.myrlennia237.internal.kotlin.entity

import dev.myrlennia237.annotation.KotlinVariant
import kotlin.time.Clock
import kotlin.time.Instant
import kotlin.uuid.Uuid

/**
 * Đánh dấu một Entity sẽ áp dụng cơ chế xóa mềm lên data của chúng thay vì xóa hoàn toàn, đồng thời áp dụng cơ chế
 * khôi phục tương ứng cho mỗi dữ liệu đó.
 * @author <a href="https://github.com/henry0337">Myrlennia</a>
 */
@KotlinVariant
internal interface KRestorable {
    /**
     * Trạng thái xóa logic hiện tại của entity.
     */
    var disabled: Boolean

    /**
     * Thời điểm entity bị đánh dấu đã xóa, hoặc `null` nếu chưa bị xóa.
     */
    var lastDisabledAt: Instant?

    /**
     * Đối tượng gần đây nhất thực hiện vô hiệu hóa entity này, hoặc `null` nếu chưa bị vô hiệu hóa.
     */
    var lastDisabledBy: Uuid?

    /**
     * Kiểm tra entity hiện tại có đang bị xóa logic hay không.
     */
    fun isDisabled(): Boolean = disabled

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
    fun markAsDisabled(by: Uuid? = null, at: Instant = Clock.System.now()) {
        disabled = true
        lastDisabledAt = at
        lastDisabledBy = by
    }

    /**
     * Khôi phục entity về trạng thái chưa xóa.
     *
     * [lastDisabledAt] và [lastDisabledBy] được giữ nguyên để lưu vết lần vô hiệu hóa gần nhất.
     */
    fun restore() {
        disabled = false
    }
}
