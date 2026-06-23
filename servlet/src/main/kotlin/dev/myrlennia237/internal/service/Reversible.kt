package dev.myrlennia237.internal.service

import java.util.UUID

/**
 * @author <a href="https://github.com/henry0337">Muharux</a>
 */
internal interface Reversible {
    /**
     * Vô hiệu hóa một bản ghi được chỉ định theo ID.
     * @param id Định danh của entity cần vô hiệu hóa
     */
    fun disable(id: UUID)

    /**
     * Kích hoạt lại một bản ghi được chỉ định theo ID.
     * @param id Định danh của entity cần kích hoạt lại
     */
    fun enable(id: UUID)
}
