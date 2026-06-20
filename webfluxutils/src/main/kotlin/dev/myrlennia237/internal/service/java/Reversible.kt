package dev.myrlennia237.internal.service.java

import reactor.core.publisher.Mono

/**
 * @param ID Kiểu của ID dùng để tìm kiếm dữ liệu đầu ra.
 * @author <a href="https://github.com/henry0337">Muharux</a>
 */
internal interface Reversible<in ID> {
    /**
     * Vô hiệu hóa một bản ghi được chỉ định theo ID.
     * @param id Định danh của entity cần vô hiệu hóa
     */
    fun disable(id: ID): Mono<Void>

    /**
     * Kích hoạt lại một bản ghi được chỉ định theo ID.
     * @param id Định danh của entity cần kích hoạt lại
     */
    fun enable(id: ID): Mono<Void>
}
