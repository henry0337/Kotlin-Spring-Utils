package dev.myrlennia237.internal.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.Optional

/**
 * @param T Dữ liệu đầu ra, thường là Aggregate Root.
 * @author <a href="https://github.com/henry0337">Muharux</a>
 */
internal fun interface Readable<T : Any> {
    /**
     * Thực hiện phân trang toàn bộ dữ liệu có trong cơ sở dữ liệu hiện tại.
     * @param pageable Cấu hình phân trang
     */
    fun findAll(pageable: Pageable): Page<T>
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
    fun findById(id: ID): Optional<T>
}
