package dev.myrlennia237.internal.kotlin.service

import dev.myrlennia237.annotation.KotlinVariant
import dev.myrlennia237.dto.KPagedResponse
import kotlin.uuid.Uuid
import org.springframework.data.domain.Pageable

/**
 * @param T Dữ liệu đầu ra, thường là Aggregate Root.
 * @author <a href="https://github.com/henry0337">Muharux</a>
 */
@KotlinVariant
internal fun interface KReadable<T : Any> {
    /**
     * Thực hiện phân trang toàn bộ dữ liệu có trong cơ sở dữ liệu hiện tại.
     * @param pageable Cấu hình phân trang
     */
    suspend fun findAll(pageable: Pageable): KPagedResponse<T>
}

/**
 * @param T Dữ liệu đầu ra, thường là Aggregate Root.
 * @author <a href="https://github.com/henry0337">Muharux</a>
 */
@KotlinVariant
internal interface KReadableWithID<T : Any> : KReadable<T> {
    /**
     * Tìm dữ liệu bản ghi theo ID.
     * @param id Định danh của entity cần tìm
     */
    suspend fun findById(id: Uuid): T?
}
