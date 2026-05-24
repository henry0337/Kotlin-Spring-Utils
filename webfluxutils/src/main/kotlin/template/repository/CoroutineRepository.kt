package template.repository

import annotation.KotlinVariant
import org.springframework.data.repository.NoRepositoryBean
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.data.repository.kotlin.CoroutineSortingRepository
import template.entity.BaseEntity

/**
 * **[[Domain Repository's Specific]]**
 *
 * Repository trung gian chứa các phương thức có thể được sử dụng nhiều trong dự án.
 *
 * **Ghi chú**: Repository này được thiết kế dành riêng cho dự án được viết bằng **Kotlin** trên môi trường
 * [WebFlux](https://docs.spring.io/spring-framework/reference/web/webflux.html).
 *
 * @param T  Kiểu domain mà repository này sẽ quản lý
 * @param ID Kiểu của ID thuộc domain mà repository này sẽ quản lý
 *
 * @author <a href="https://github.com/henry0337">Myrlennia</a>
 * @see <a href="https://docs.spring.io/spring-data/relational/reference/kotlin/coroutines.html">Coroutine-specific Repository</a>
 */
@KotlinVariant
@NoRepositoryBean
interface CoroutineRepository<T : BaseEntity<ID>, ID : Any> :
    CoroutineCrudRepository<T, ID>,
    CoroutineSortingRepository<T, ID>
