package dev.myrlennia237.internal.entity

import dev.myrlennia237.JavaInstant

/**
 * Đánh dấu một Entity sẽ áp dụng cơ chế xóa mềm lên data của chúng thay vì xóa hoàn toàn, đồng thời áp dụng cơ chế
 * khôi phục tương ứng cho mỗi dữ liệu đó.
 * @author <a href="https://github.com/henry0337">Muharux</a>
 */
internal interface Restorable {
    /**
     * Trả về trạng thái xóa logic hiện tại của entity.
     *
     * @return Giá trị trạng thái đã lưu
     */
    fun getRemovedState(): Short

    /**
     * Cập nhật trạng thái xóa logic của entity.
     *
     * @param removed Giá trị trạng thái mới
     */
    fun setRemovedState(removed: Short)

    /**
     * Trả về thời điểm entity bị đánh dấu đã xóa.
     *
     * @return Thời điểm xóa, hoặc `null` nếu entity chưa bị xóa
     */
    fun getDeletedTimestamp(): JavaInstant?

    /**
     * Cập nhật thời điểm entity bị đánh dấu đã xóa.
     *
     * @param deletedAt Thời điểm xóa, hoặc `null` nếu muốn xóa dấu thời gian
     */
    fun setRemovedTimestamp(deletedAt: JavaInstant?)

    /**
     * Kiểm tra entity hiện tại có đang bị xóa logic hay không.
     *
     * @return `true` nếu trạng thái xóa là `1`, ngược lại `false`
     */
    fun isDeleted(): Boolean = getRemovedState().toInt() == 1

    /**
     * Đánh dấu entity hiện tại là đã xóa logic.
     *
     * Hàm này sẽ đặt trạng thái xóa về `1` và gán thời điểm xóa là thời gian hiện tại (UTC).
     */
    fun markAsDeleted() {
        setRemovedState(1)
        setRemovedTimestamp(JavaInstant.now())
    }

    /**
     * Khôi phục entity về trạng thái chưa xóa.
     *
     * Hàm này sẽ đặt trạng thái xóa về `0` và xóa thời điểm xóa.
     */
    fun restore() {
        setRemovedState(0)
        setRemovedTimestamp(null)
    }
}
