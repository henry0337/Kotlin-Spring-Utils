package dev.myrlennia237.template.controller

import dev.myrlennia237.component.dto.PagedResponse
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import java.util.UUID

/**
 * Một lớp định nghĩa các contract cho phương thức REST API cơ bản.
 *
 * Việc mà phía consumer cần làm sẽ chỉ đơn giản là **triển khai hoàn thiện logic xử lý request** cho từng phương thức bên trong.
 *
 * @param T  Dữ liệu đầu ra - thường là **Aggregate Root**, **Projection** hoặc **DTO** mong muốn
 * @param I1 **DTO** dùng làm nơi nhận dữ liệu để **thêm mới (Create)** vào cơ sở dữ liệu
 * @param I2 **DTO** dùng làm nơi nhận dữ liệu để **cập nhật (Update)** vào bản ghi có sẵn trong cơ sở dữ liệu
 * @author <a href="https://github.com/henry0337">Myrlennia</a>
 * @see BaseController
 * @sample dev.myrlennia237.sample.FooController
 */
public abstract class AbstractCrudController<T : Any, I1, I2> : BaseController() {

    /**
     * Cung cấp API trả về danh sách toàn bộ bản ghi đã được phân trang.
     *
     * @param pageable Thông số cấu hình phân trang
     * @return [ResponseEntity] với status là `200 OK` với [PagedResponse] là response body
     */
    public abstract fun findAll(pageable: Pageable): ResponseEntity<PagedResponse<T>>

    /**
     * Cung cấp API trả về bản ghi tương ứng với [id] được truyền qua request.
     *
     * @param id ID của bản ghi cần lấy thông tin
     * @return [ResponseEntity] với status là `200 OK` và generic [T] là response body, có thể trả về status
     * `204 No Content` và body là `null` nếu không có dữ liệu tương ứng được tìm thấy.
     */
    public abstract fun findById(id: UUID): ResponseEntity<T>

    /**
     * Cung cấp API trả về bản ghi mới được tạo bằng thông tin được truyền vào qua [body].
     *
     * @param body Create DTO cho request này
     * @return [ResponseEntity] với status là `201 Created` và generic [T] là response body.
     */
    public abstract fun create(body: I1): ResponseEntity<T>

    /**
     * Cung cấp API trả về bản ghi được tìm thấy qua [id] đã được cập nhật thông tin với dữ liệu mới từ [body].
     *
     * @param id ID của bản ghi cần cập nhật
     * @param body Update DTO cho request này
     * @return [ResponseEntity] với status là `200 OK` và generic [T] là response body, có thể trả về status
     * `404 Not Found` và body là `null` nếu không có dữ liệu tương ứng được tìm thấy.
     */
    public abstract fun update(id: UUID, body: I2): ResponseEntity<T>

    /**
     * Xóa hoàn toàn một bản ghi được tìm thấy qua [id].
     *
     * @param id ID của bản ghi cần xóa
     * @return [ResponseEntity] với status là `200 OK`, có thể trả về status `404 Not Found` nếu không có dữ liệu tương
     * ứng được tìm thấy.
     */
    public abstract fun delete(id: UUID): ResponseEntity<Void>

    /**
     * Vô hiệu hóa một bản ghi được tìm thấy qua [id].
     *
     * @param id ID của bản ghi cần vô hiệu hóa
     * @return [ResponseEntity] với status là `200 OK`, có thể trả về status `404 Not Found` nếu không có dữ liệu tương
     * ứng được tìm thấy.
     */
    public abstract fun disable(id: UUID): ResponseEntity<Void>

    /**
     * Kích hoạt lại một bản ghi được tìm thấy qua [id].
     *
     * @param id ID của bản ghi cần kích hoạt lại
     * @return [ResponseEntity] với status là `200 OK`, có thể trả về status `404 Not Found` nếu không có dữ liệu tương
     * ứng được tìm thấy.
     */
    public abstract fun enable(id: UUID): ResponseEntity<Void>
}