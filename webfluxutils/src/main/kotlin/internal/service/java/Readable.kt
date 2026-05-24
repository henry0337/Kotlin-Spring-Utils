package internal.service.java

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import reactor.core.publisher.Mono

/**
 * Java variant cho contract đọc dữ liệu dạng reactive.
 *
 * @author <a href="https://github.com/henry0337">Myrlennia</a>
 */
internal fun interface Readable<T : Any> {
    /**
     * Lấy danh sách entity theo trang.
     *
     * @param pageable Thông tin phân trang và sắp xếp
     * @return `Mono` bọc trang dữ liệu tương ứng
     */
    fun findAll(pageable: Pageable): Mono<Page<T>>
}

/**
 * Java variant mở rộng của [Readable] để đọc một entity theo định danh.
 *
 * @author <a href="https://github.com/henry0337">Myrlennia</a>
 */
internal interface ReadableWithID<T : Any, in ID> : Readable<T> {
    /**
     * Tìm một entity theo định danh.
     *
     * @param id Định danh của entity cần tìm
     * @return `Mono` bọc entity nếu tìm thấy
     */
    fun findById(id: ID): Mono<T>
}
