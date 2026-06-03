package dev.myrlennia237.internal.entity

/**
 * Contract mô tả entity có cơ chế versioning để kiểm soát xung đột cập nhật.
 *
 * @author <a href="https://github.com/henry0337">Myrlennia</a>
 */
internal interface Conflictable {
    /**
     * Trả về phiên bản hiện tại của entity.
     *
     * @return Giá trị version
     */
    fun getVersion(): Long

    /**
     * Cập nhật phiên bản hiện tại của entity.
     *
     * @param version Giá trị version mới
     */
    fun setVersion(version: Long)
}
