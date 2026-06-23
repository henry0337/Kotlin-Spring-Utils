package dev.myrlennia237.template.controller.kotlin

import dev.myrlennia237.annotation.KotlinVariant
import dev.myrlennia237.dto.PagedResponse
import dev.myrlennia237.template.controller.BaseReactiveController
import kotlin.uuid.Uuid
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

/**
 * Controller dạng REST đặc thù cho phía Kotlin giúp khởi tạo nhanh các API.
 * @param T  Kiểu domain (thường là DTO phía response)
 * @param I1 Kiểu DTO dùng để tạo mới entity
 * @param I2 Kiểu DTO dùng để cập nhật entity
 *
 * @author <a href="https://github.com/henry0337">Muharux</a>
 */
@KotlinVariant
abstract class CoroutineCrudController<T : Any, in I1, in I2> : BaseReactiveController() {

    /**
     * Lấy toàn bộ bản ghi dưới dạng phân trang.
     *
     * @param pageable Cấu hình phân trang (page, size, sort)
     * @return Danh sách bản ghi của trang hiện tại kèm thông tin điều hướng
     */
    abstract suspend fun findAll(pageable: Pageable): PagedResponse<T>

    /**
     * Tìm bản ghi theo ID. Ném [org.springframework.web.server.ResponseStatusException]
     * với status `404 Not Found` nếu không tìm thấy bản ghi.
     *
     * @param id Định danh của bản ghi cần tìm
     * @return Bản ghi tương ứng
     */
    abstract suspend fun findById(id: Uuid): T

    /**
     * Tạo mới một bản ghi.
     *
     * @param body DTO chứa thông tin cần thiết để tạo bản ghi
     * @return Bản ghi vừa được tạo
     */
    @ResponseStatus(HttpStatus.CREATED)
    abstract suspend fun create(body: I1): T

    /**
     * Cập nhật thông tin bản ghi theo ID.
     *
     * @param id   Định danh của bản ghi cần cập nhật
     * @param body DTO chứa thông tin muốn thay đổi
     * @return Bản ghi sau khi cập nhật
     */
    abstract suspend fun update(id: Uuid, body: I2): T

    /**
     * Xóa vĩnh viễn một bản ghi theo ID.
     *
     * @param id Định danh của bản ghi cần xóa
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    abstract suspend fun delete(id: Uuid)

    /**
     * Vô hiệu hóa một bản ghi theo ID (xóa mềm).
     *
     * @param id Định danh của bản ghi cần vô hiệu hóa
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    abstract suspend fun disable(id: Uuid)

    /**
     * Kích hoạt lại một bản ghi đã bị vô hiệu hóa theo ID.
     *
     * @param id Định danh của bản ghi cần kích hoạt lại
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    abstract suspend fun enable(id: Uuid)
}
