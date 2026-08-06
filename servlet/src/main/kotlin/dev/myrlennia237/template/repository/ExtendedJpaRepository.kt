package dev.myrlennia237.template.repository

import dev.myrlennia237.component.ImmutableList
import dev.myrlennia237.template.entity.BaseEntity
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.NoRepositoryBean
import java.util.UUID

/**
 * Repository được tinh chỉnh từ [JpaRepository] hỗ trợ thực thi thêm các
 * [Specification][org.springframework.data.jpa.domain.Specification] thông qua [JpaSpecificationExecutor]
 * để giúp tinh chỉnh sâu hơn cho các câu truy vấn JPA.
 *
 * @param T  Kiểu domain mà repository này sẽ quản lý, vì ưu tiên convention của thư viện nên lớp đại diện cho generic
 * đó phải là một **entity** (tức kế thừa [BaseEntity]).
 * @author <a href="https://github.com/henry0337">Myrlennia</a>
 * @see <a href="https://docs.spring.io/spring-data/jpa/reference/jpa/getting-started.html">JPA Repository</a>
 * @see <a href="https://docs.spring.io/spring-data/jpa/reference/jpa/specifications.html">Spring Data JPA's Specifications</a>
 * @sample dev.myrlennia237.sample.FooRepository
 */
@NoRepositoryBean
@JvmSuppressWildcards
public interface ExtendedJpaRepository<T : BaseEntity> : JpaRepository<T, UUID>, JpaSpecificationExecutor<T> {

    /**
     * Tìm kiếm tất cả bản ghi có trong cơ sở dữ liệu và sau đó thực hiện phân trang chúng.
     *
     * @param pageable Thông số cấu hình phân trang
     * @return [ImmutableList] phát ra danh sách toàn bộ dữ liệu.
     */
    public fun findAllBy(pageable: Pageable): ImmutableList<T>
}
