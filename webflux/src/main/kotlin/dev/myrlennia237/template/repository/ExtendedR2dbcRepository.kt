package dev.myrlennia237.template.repository

import dev.myrlennia237.template.entity.Entity
import org.springframework.data.domain.Pageable
import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.data.repository.NoRepositoryBean
import reactor.core.publisher.Flux
import java.util.UUID

/**
 * Repository được tinh chỉnh từ [R2dbcRepository] có thêm chức năng phân trang dữ liệu.
 *
 * @param T  Kiểu domain mà repository này sẽ quản lý, vì ưu tiên convention của thư viện nên lớp đại diện cho generic
 * đó phải là một **entity** (tức kế thừa [Entity]).
 * @author <a href="https://github.com/henry0337">Myrlennia</a>
 * @see <a href="https://docs.spring.io/spring-data/relational/reference/r2dbc/repositories.html">R2DBC Repository</a>
 */
@NoRepositoryBean
@JvmSuppressWildcards
public interface ExtendedR2dbcRepository<T : Entity> : R2dbcRepository<T, UUID> {

    /**
     * Tìm kiếm tất cả bản ghi có trong cơ sở dữ liệu và sau đó thực hiện phân trang chúng.
     *
     * @param pageable Thông số cấu hình phân trang
     * @return [Flux] phát ra danh sách toàn bộ dữ liệu.
     */
    public fun findAllBy(pageable: Pageable): Flux<T>
}
