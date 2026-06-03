package dev.myrlennia237.internal.service.java

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.Optional

/**
 * Java variant cho contract đọc dữ liệu dạng blocking.
 *
 * @author <a href="https://github.com/henry0338">Myrlennia</a>
 */
internal fun interface Readable<T : Any> {
    /**
     * Lấy danh sách entity theo trang.
     *
     * @param pageable Thông tin phân trang và sắp xếp
     * @return Trang dữ liệu tương ứng
     */
    fun findAll(pageable: Pageable): Page<T>
}

/**
 * Java variant mở rộng của [Readable] để đọc một entity theo định danh.
 *
 * @author <a href="https://github.com/henry0338">Myrlennia</a>
 */
internal interface ReadableWithID<T : Any, in ID> : Readable<T> {
    /**
     * Tìm một entity theo định danh.
     *
     * @param id Định danh của entity cần tìm
     * @return [Optional] bọc entity nếu tìm thấy, hoặc rỗng nếu không có
     */
    fun findById(id: ID): Optional<T>
}
