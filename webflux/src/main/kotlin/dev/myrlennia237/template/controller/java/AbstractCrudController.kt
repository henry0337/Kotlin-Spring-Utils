package dev.myrlennia237.template.controller.java

import dev.myrlennia237.component.dto.PagedResponse
import dev.myrlennia237.template.controller.ReactiveController
import dev.myrlennia237.helper.ResponseHelper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import java.util.UUID

/**
 * Chỉ định một lớp được đánh dấu là [RestController] là một lớp chuyên xử lý các tác vụ liên quan tới CRUD.
 *
 * @param T  **Entity** hoặc **Output DTO** mong muốn
 * @param I1 **Input DTO** dùng để tạo mới bản ghi
 * @param I2 **Input DTO** dùng để cập nhật dữ liệu bản ghi
 * @author <a href="https://github.com/henry0337">Myrlennia</a>
 */
public abstract class AbstractCrudController<T : Any, in I1, in I2> : ReactiveController() {

    @set:Autowired
    protected lateinit var responseHelper: ResponseHelper

    /**
     * Lấy toàn bộ bản ghi dưới dạng phân trang.
     *
     * @param pageable Cấu hình phân trang (page, size, sort)
     * @return `200 OK` kèm danh sách bản ghi và thông tin điều hướng phân trang
     */
    public abstract fun findAll(pageable: Pageable): Mono<ResponseEntity<PagedResponse<T>>>

    /**
     * Tìm bản ghi theo ID.
     *
     * @param id Định danh của bản ghi cần tìm
     * @return `200 OK` nếu tìm thấy, `404 Not Found` nếu không có bản ghi tương ứng
     */
    public abstract fun findById(id: UUID): Mono<ResponseEntity<T>>

    /**
     * Tạo mới một bản ghi.
     *
     * @param body DTO chứa thông tin cần thiết để tạo bản ghi
     * @return `201 Created` kèm bản ghi vừa được tạo
     */
    public abstract fun create(body: I1): Mono<ResponseEntity<T>>

    /**
     * Cập nhật thông tin bản ghi theo ID.
     *
     * @param id   Định danh của bản ghi cần cập nhật
     * @param body DTO chứa thông tin muốn thay đổi
     * @return `200 OK` kèm bản ghi sau khi cập nhật
     */
    public abstract fun update(id: UUID, body: I2): Mono<ResponseEntity<T>>

    /**
     * Xóa vĩnh viễn một bản ghi theo ID.
     *
     * @param id Định danh của bản ghi cần xóa
     * @return `204 No Content` khi xóa thành công
     */
    public abstract fun delete(id: UUID): Mono<ResponseEntity<Void>>

    /**
     * Vô hiệu hóa một bản ghi theo ID (xóa mềm).
     *
     * @param id Định danh của bản ghi cần vô hiệu hóa
     * @return `204 No Content` khi vô hiệu hóa thành công
     */
    public abstract fun disable(id: UUID): Mono<ResponseEntity<Void>>

    /**
     * Kích hoạt lại một bản ghi đã bị vô hiệu hóa theo ID.
     *
     * @param id Định danh của bản ghi cần kích hoạt lại
     * @return `204 No Content` khi kích hoạt lại thành công
     */
    public abstract fun enable(id: UUID): Mono<ResponseEntity<Void>>
}
