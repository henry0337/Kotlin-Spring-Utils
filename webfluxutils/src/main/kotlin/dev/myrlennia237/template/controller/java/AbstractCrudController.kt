package dev.myrlennia237.template.controller.java

import dev.myrlennia237.dto.PagedResponse
import dev.myrlennia237.template.controller.BaseReactiveController
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import reactor.core.publisher.Mono

/**
 * Controller dạng REST giúp khởi tạo nhanh các API.
 * @param T  Kiểu domain (thường là DTO phía response)
 * @param ID Kiểu của primary key
 * @param I1 Kiểu DTO dùng để tạo mới entity
 * @param I2 Kiểu DTO dùng để cập nhật entity
 *
 * @author <a href="https://github.com/henry0337">Muharux</a>
 */
abstract class AbstractCrudController<T : Any, in ID, in I1, in I2> : BaseReactiveController() {

    /**
     * Lấy toàn bộ bản ghi dưới dạng phân trang.
     *
     * @param pageable Cấu hình phân trang (page, size, sort)
     * @return `200 OK` kèm danh sách bản ghi và thông tin điều hướng phân trang
     */
    abstract fun findAll(pageable: Pageable): Mono<ResponseEntity<PagedResponse<T>>>

    /**
     * Tìm bản ghi theo ID.
     *
     * @param id Định danh của bản ghi cần tìm
     * @return `200 OK` nếu tìm thấy, `404 Not Found` nếu không có bản ghi tương ứng
     */
    abstract fun findById(id: ID): Mono<ResponseEntity<T>>

    /**
     * Tạo mới một bản ghi.
     *
     * @param body DTO chứa thông tin cần thiết để tạo bản ghi
     * @return `201 Created` kèm bản ghi vừa được tạo
     */
    abstract fun create(body: I1): Mono<ResponseEntity<T>>

    /**
     * Cập nhật thông tin bản ghi theo ID.
     *
     * @param id   Định danh của bản ghi cần cập nhật
     * @param body DTO chứa thông tin muốn thay đổi
     * @return `200 OK` kèm bản ghi sau khi cập nhật
     */
    abstract fun update(id: ID, body: I2): Mono<ResponseEntity<T>>

    /**
     * Xóa vĩnh viễn một bản ghi theo ID.
     *
     * @param id Định danh của bản ghi cần xóa
     * @return `204 No Content` khi xóa thành công
     */
    abstract fun delete(id: ID): Mono<ResponseEntity<Void>>

    /**
     * Vô hiệu hóa một bản ghi theo ID (xóa mềm).
     *
     * @param id Định danh của bản ghi cần vô hiệu hóa
     * @return `204 No Content` khi vô hiệu hóa thành công
     */
    abstract fun disable(id: ID): Mono<ResponseEntity<Void>>

    /**
     * Kích hoạt lại một bản ghi đã bị vô hiệu hóa theo ID.
     *
     * @param id Định danh của bản ghi cần kích hoạt lại
     * @return `204 No Content` khi kích hoạt lại thành công
     */
    abstract fun enable(id: ID): Mono<ResponseEntity<Void>>
}
