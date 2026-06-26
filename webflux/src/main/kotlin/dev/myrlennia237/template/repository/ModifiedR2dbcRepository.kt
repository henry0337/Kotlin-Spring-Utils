package dev.myrlennia237.template.repository

import dev.myrlennia237.template.entity.Entity
import org.springframework.data.domain.Pageable
import org.springframework.data.querydsl.ReactiveQuerydslPredicateExecutor
import org.springframework.data.repository.NoRepositoryBean
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.data.repository.reactive.ReactiveSortingRepository
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor
import org.springframework.data.r2dbc.repository.R2dbcRepository
import reactor.core.publisher.Flux
import java.util.UUID

/**
 * Repository đã được chỉnh sửa theo cú pháp gốc từ [R2dbcRepository] để sử dụng [ReactiveQuerydslPredicateExecutor]
 * thay cho [ReactiveQueryByExampleExecutor].
 *
 * @param T  Kiểu domain mà repository này sẽ quản lý, vì ưu tiên convention của thư viện nên lớp đại diện cho generic
 * đó phải là một **entity** (tức kế thừa [Entity]).
 * @author <a href="https://github.com/henry0337">Muharux</a>
 * @see <a href="https://docs.spring.io/spring-data/relational/reference/r2dbc/repositories.html">R2DBC Repository</a>
 */
@NoRepositoryBean
@JvmSuppressWildcards
public interface ModifiedR2dbcRepository<T : Entity> : ReactiveCrudRepository<T, UUID>, ReactiveSortingRepository<T, UUID>,
    ReactiveQuerydslPredicateExecutor<T> {

    /**
     * Tìm kiếm tất cả bản ghi có trong cơ sở dữ liệu, áp dụng tính năng **phân trang dữ liệu**.
     * @param pageable Tham số phân trang dữ liệu
     */
    public fun findAllBy(pageable: Pageable): Flux<T>
}
