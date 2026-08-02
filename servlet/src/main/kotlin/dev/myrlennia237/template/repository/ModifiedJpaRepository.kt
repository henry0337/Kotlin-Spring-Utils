package dev.myrlennia237.template.repository

import dev.myrlennia237.component.ImmutableList
import dev.myrlennia237.template.entity.BaseEntity
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.NoRepositoryBean
import java.util.UUID

/**
 * Repository dạng **blocking (Servlet)** được tinh chỉnh từ [JpaRepository] hỗ trợ thực thi thêm các
 * [Specification][org.springframework.data.jpa.domain.Specification] thông qua [JpaSpecificationExecutor]
 * để giúp tinh chỉnh sâu hơn cho các câu truy vấn JPA.
 *
 * @author <a href="https://github.com/henry0337">Myrlennia</a>
 * @see <a href="https://docs.spring.io/spring-data/jpa/reference/jpa/getting-started.html">JPA Repository</a>
 * @see <a href="https://docs.spring.io/spring-data/jpa/reference/jpa/specifications.html">Spring Data JPA's Specifications</a>
 * @sample dev.myrlennia237.sample.FooRepository
 */
@NoRepositoryBean
@JvmSuppressWildcards
public interface ModifiedJpaRepository<T : BaseEntity> : JpaRepository<T, UUID>, JpaSpecificationExecutor<T> {

    /**
     * Tìm kiếm tất cả bản ghi có trong cơ sở dữ liệu và sau đó thực hiện phân trang chúng.
     *
     * @param pageable Thông số cấu hình phân trang
     * @return Một [ImmutableList] phát ra danh sách toàn bộ dữ liệu.
     */
    public fun findAllBy(pageable: Pageable): ImmutableList<T>
}