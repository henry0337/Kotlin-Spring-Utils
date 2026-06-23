package dev.myrlennia237.template.repository

import dev.myrlennia237.template.entity.BaseEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.ListCrudRepository
import org.springframework.data.repository.ListPagingAndSortingRepository
import org.springframework.data.repository.NoRepositoryBean
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.querydsl.ListQuerydslPredicateExecutor
import java.util.UUID

/**
 * Repository được tùy chỉnh từ [JpaRepository] để sử dụng [ListQuerydslPredicateExecutor] thay thế cho [JpaSpecificationExecutor].
 * @author <a href="https://github.com/henry0337">Muharux</a>
 * @see <a href="https://docs.spring.io/spring-data/jpa/reference/repositories/core-extensions.html#core.extensions.querydsl">QueryDSL Extension</a>
 */
@NoRepositoryBean
@JvmSuppressWildcards
interface ModifiedJpaRepository<T : BaseEntity> : ListCrudRepository<T, UUID>,
    ListPagingAndSortingRepository<T, UUID>,
    ListQuerydslPredicateExecutor<T>