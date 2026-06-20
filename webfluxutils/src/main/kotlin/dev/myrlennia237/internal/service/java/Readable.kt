package dev.myrlennia237.internal.service.java

import dev.myrlennia237.dto.PagedResponse
import org.springframework.data.domain.Pageable
import reactor.core.publisher.Mono

/**
 * @param T Dữ liệu đầu ra, thường là Aggregate Root.
 * @author <a href="https://github.com/henry0337">Muharux</a>
 */
internal fun interface Readable<T : Any> {
    /**
     * Thực hiện phân trang toàn bộ dữ liệu có trong cơ sở dữ liệu hiện tại.
     * @param pageable Cấu hình phân trang
     */
    fun findAll(pageable: Pageable): Mono<PagedResponse<T>>
}

/**
 * @param T Dữ liệu đầu ra, thường là Aggregate Root.
 * @param ID Kiểu của ID dùng để tìm kiếm dữ liệu đầu ra.
 * @author <a href="https://github.com/henry0337">Muharux</a>
 */
internal interface ReadableWithID<T : Any, in ID> : Readable<T> {
    /**
     * Tìm dữ liệu bản ghi theo ID.
     * @param id Định danh của entity cần tìm
     */
    fun findById(id: ID): Mono<T>
}
