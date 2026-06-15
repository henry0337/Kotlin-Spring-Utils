package dev.myrlennia237.internal.service.java

import reactor.core.publisher.Mono

/**
 * @param ID Kiểu của ID dùng để tìm kiếm dữ liệu đầu ra.
 * @author <a href="https://github.com/henry0337">Ademia</a>
 */
internal fun interface Reversible<in ID> {
    /**
     * Vô hiệu hóa một bản ghi được chỉ định theo ID.
     * @param id Định danh của entity cần vô hiệu hóa
     */
    fun disable(id: ID): Mono<Void>
}
