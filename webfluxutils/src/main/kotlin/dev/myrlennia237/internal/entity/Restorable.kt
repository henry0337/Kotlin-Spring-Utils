package dev.myrlennia237.internal.entity

import dev.myrlennia237.utils.JavaLocalDateTime
import java.time.LocalDateTime

/**
 * Contract mô tả entity có trạng thái xóa logic và có thể được khôi phục.
 *
 * @author <a href="https://github.com/henry0337">Myrlennia</a>
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
    fun getDeletedTimestamp(): JavaLocalDateTime?

    /**
     * Cập nhật thời điểm entity bị đánh dấu đã xóa.
     *
     * @param deletedAt Thời điểm xóa, hoặc `null` nếu muốn xóa dấu thời gian
     */
    fun setRemovedTimestamp(deletedAt: JavaLocalDateTime?)

    /**
     * Kiểm tra entity hiện tại có đang bị xóa logic hay không.
     *
     * @return `true` nếu trạng thái xóa là `1`, ngược lại `false`
     */
    fun isDeleted(): Boolean = getRemovedState().toInt() == 1

    /**
     * Đánh dấu entity hiện tại là đã xóa logic.
     *
     * Hàm này sẽ đặt trạng thái xóa về `1` và gán thời điểm xóa là thời gian hiện tại.
     */
    fun markAsDeleted() {
        setRemovedState(1.toShort())
        setRemovedTimestamp(LocalDateTime.now())
    }

    /**
     * Khôi phục entity về trạng thái chưa xóa.
     *
     * Hàm này sẽ đặt trạng thái xóa về `0` và xóa thời điểm xóa.
     */
    fun restore() {
        setRemovedState(0.toShort())
        setRemovedTimestamp(null)
    }
}
