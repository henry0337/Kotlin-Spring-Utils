package dev.myrlennia237.template.controller.kotlin

import dev.myrlennia237.annotation.ExperimentalKotlinVariantApi
import dev.myrlennia237.annotation.KotlinVariant
import dev.myrlennia237.dto.KPagedResponse
import dev.myrlennia237.template.controller.ReactiveRestController
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.RestController
import kotlin.uuid.Uuid

/**
 * Chỉ định một lớp được đánh dấu là [RestController] là một lớp chuyên xử lý các tác vụ liên quan tới CRUD.
 *
 * @param T  **Entity** hoặc **Output DTO** mong muốn
 * @param I1 **Input DTO** dùng để tạo mới bản ghi
 * @param I2 **Input DTO** dùng để cập nhật dữ liệu bản ghi
 * @author <a href="https://github.com/henry0337">Muharux</a>
 */
@KotlinVariant
@ExperimentalKotlinVariantApi
public abstract class CoroutineRestController<T : Any, in I1, in I2> : ReactiveRestController() {

    /**
     * Lấy toàn bộ bản ghi dưới dạng phân trang.
     *
     * @param pageable Cấu hình phân trang (page, size, sort)
     * @return Danh sách bản ghi của trang hiện tại kèm thông tin điều hướng
     */
    public abstract suspend fun findAll(pageable: Pageable): KPagedResponse<T>

    /**
     * Tìm bản ghi theo ID. Ném [org.springframework.web.server.ResponseStatusException]
     * với status `404 Not Found` nếu không tìm thấy bản ghi.
     *
     * @param id Định danh của bản ghi cần tìm
     * @return Bản ghi tương ứng
     */
    public abstract suspend fun findById(id: Uuid): T

    /**
     * Tạo mới một bản ghi.
     *
     * @param body DTO chứa thông tin cần thiết để tạo bản ghi
     * @return Bản ghi vừa được tạo
     */
    public abstract suspend fun create(body: I1): T

    /**
     * Cập nhật thông tin bản ghi theo ID.
     *
     * @param id   Định danh của bản ghi cần cập nhật
     * @param body DTO chứa thông tin muốn thay đổi
     * @return Bản ghi sau khi cập nhật
     */
    public abstract suspend fun update(id: Uuid, body: I2): T

    /**
     * Xóa vĩnh viễn một bản ghi theo ID.
     *
     * @param id Định danh của bản ghi cần xóa
     */
    public abstract suspend fun delete(id: Uuid)

    /**
     * Vô hiệu hóa một bản ghi theo ID (xóa mềm).
     *
     * @param id Định danh của bản ghi cần vô hiệu hóa
     */
    public abstract suspend fun disable(id: Uuid)

    /**
     * Kích hoạt lại một bản ghi đã bị vô hiệu hóa theo ID.
     *
     * @param id Định danh của bản ghi cần kích hoạt lại
     */
    public abstract suspend fun enable(id: Uuid)
}
