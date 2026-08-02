package dev.myrlennia237.internal.java.service

import reactor.core.publisher.Mono

/**
 * @param T Dữ liệu đầu ra, thường là Aggregate Root.
 * @param I Dữ liệu đầu vào, thường là DTO, Projection hoặc tương tự.
 * @author <a href="https://github.com/henry0337">Myrlennia</a>
 */
internal fun interface Insertable<T : Any, in I> {
    /**
     * Tạo mới một entity từ dữ liệu đầu vào.
     * @param item Dữ liệu đầu vào dùng để tạo entity
     */
    fun insert(item: I): Mono<T>
}
