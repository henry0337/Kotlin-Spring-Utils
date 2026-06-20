package dev.myrlennia237.internal.service.java

import reactor.core.publisher.Mono

/**
 * @param T Dữ liệu đầu ra, thường là Aggregate Root.
 * @param ID Kiểu của ID dùng để tìm kiếm dữ liệu đầu ra.
 * @param I Dữ liệu đầu vào, thường là DTO, Projection hoặc tương tự.
 * @author <a href="https://github.com/henry0337">Muharux</a>
 */
internal fun interface Modifiable<T : Any, in ID, in I> {
    /**
     * Cập nhật dữ liệu của một entity theo ID.
     * @param id ID của entity cần cập nhật
     * @param body Dữ liệu mới để thay thế
     */
    fun update(id: ID, body: I): Mono<T>
}
