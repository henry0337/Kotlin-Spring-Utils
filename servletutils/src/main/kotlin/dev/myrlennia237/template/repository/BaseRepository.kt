package dev.myrlennia237.template.repository

import dev.myrlennia237.template.entity.BaseEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.NoRepositoryBean

/**
 * **[[Domain Repository's Specific]]**
 *
 * Repository trung gian chứa các phương thức có thể được sử dụng nhiều trong dự án.
 *
 * **Ghi chú**: Repository này được thiết kế dành riêng cho dự án sử dụng **Spring MVC** với JPA.
 *
 * @param T  Kiểu domain mà repository này sẽ quản lý
 * @param ID Kiểu của ID thuộc domain mà repository này sẽ quản lý
 *
 * @author <a href="https://github.com/henry0337">Myrlennia</a>
 * @see <a href="https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html">JPA Repository</a>
 */
@NoRepositoryBean
@JvmSuppressWildcards
interface BaseRepository<T : BaseEntity<ID>, ID : Any> : JpaRepository<T, ID>, JpaSpecificationExecutor<T>
