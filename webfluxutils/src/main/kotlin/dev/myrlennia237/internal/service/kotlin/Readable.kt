package dev.myrlennia237.internal.service.kotlin

import dev.myrlennia237.annotation.KotlinVariant
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

/**
 * Kotlin variant cho contract đọc dữ liệu theo kiểu coroutine.
 *
 * @author <a href="https://github.com/henry0337">Myrlennia</a>
 */
@KotlinVariant
internal fun interface Readable<T : Any> {
    /**
     * Lấy danh sách entity theo trang.
     *
     * @param pageable Thông tin phân trang và sắp xếp
     * @return Trang dữ liệu đã được resolve
     */
    suspend fun findAll(pageable: Pageable): Page<T>
}

/**
 * Kotlin variant mở rộng của [Readable] để đọc một entity theo định danh.
 *
 * @author <a href="https://github.com/henry0337">Myrlennia</a>
 */
@KotlinVariant
internal interface ReadableWithID<T : Any, in ID> : Readable<T> {
    /**
     * Tìm một entity theo định danh.
     *
     * @param id Định danh của entity cần tìm
     * @return Entity nếu tìm thấy, hoặc `null`
     */
    suspend fun findById(id: ID): T?
}
