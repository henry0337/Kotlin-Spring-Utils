package dev.myrlennia237.internal.java.service

import dev.myrlennia237.component.dto.PagedResponse
import org.springframework.data.domain.Pageable
import reactor.core.publisher.Mono
import java.util.UUID

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
 * @author <a href="https://github.com/henry0337">Muharux</a>
 */
internal interface ReadableWithID<T : Any> : Readable<T> {
    /**
     * Tìm dữ liệu bản ghi theo ID.
     * @param id Định danh của entity cần tìm
     */
    fun findById(id: UUID): Mono<T>
}
