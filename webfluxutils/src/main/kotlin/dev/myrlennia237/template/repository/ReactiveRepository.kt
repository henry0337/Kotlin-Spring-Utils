package dev.myrlennia237.template.repository

import dev.myrlennia237.template.entity.BaseEntity
import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.data.repository.NoRepositoryBean

/**
 * **[[Domain Repository's Specific]]**
 *
 * Repository trung gian chứa các phương thức có thể được sử dụng nhiều trong dự án.
 *
 * **Ghi chú**: Repository này được thiết kế dành riêng cho dự án được viết bằng **Java** trên môi trường
 * [WebFlux](https://docs.spring.io/spring-framework/reference/web/webflux.html).
 *
 * @param T  Kiểu domain mà repository này sẽ quản lý
 * @param ID Kiểu của ID thuộc domain mà repository này sẽ quản lý
 *
 * @author <a href="https://github.com/henry0337">Myrlennia</a>
 * @see <a href="https://docs.spring.io/spring-data/relational/reference/r2dbc/repositories.html">R2DBC Repository</a>
 */
@NoRepositoryBean
@JvmSuppressWildcards
interface ReactiveRepository<T : BaseEntity<ID>, ID : Any> : R2dbcRepository<T, ID>
