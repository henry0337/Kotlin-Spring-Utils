package dev.myrlennia237.internal.kotlin.entity

import dev.myrlennia237.annotation.KotlinVariant
import kotlin.time.Clock
import kotlin.time.Instant
import kotlin.uuid.Uuid

/**
 * Đánh dấu một Entity sẽ áp dụng cơ chế xóa mềm lên data của chúng thay vì xóa hoàn toàn, đồng thời áp dụng cơ chế
 * khôi phục tương ứng cho mỗi dữ liệu đó.
 * @author <a href="https://github.com/henry0337">Muharux</a>
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
     * Hàm này sẽ đặt trạng thái xóa về `true`, gán thời điểm xóa là thời gian hiện tại (UTC),
     * và ghi nhận đối tượng thực hiện thao tác.
     *
     * @param by UUID của đối tượng thực hiện vô hiệu hóa, hoặc `null` nếu không xác định
     */
    fun markAsDeleted(by: Uuid? = null) {
        disabled = true
        lastDisabledAt = Clock.System.now()
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
