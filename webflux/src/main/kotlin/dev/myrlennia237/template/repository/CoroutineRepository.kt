package dev.myrlennia237.template.repository

import dev.myrlennia237.annotation.KotlinVariant
import dev.myrlennia237.template.entity.KEntity
import kotlinx.coroutines.flow.Flow
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.NoRepositoryBean
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.data.repository.kotlin.CoroutineSortingRepository
import kotlin.uuid.Uuid

/**
 * Repository đặc thù cho phía Kotlin, sử dụng chính các API của Kotlin để thực hiện các tác vụ CRUD.
 *
 * Sử dụng [kotlin.uuid.Uuid] làm kiểu ID — việc chuyển đổi qua lại với [java.util.UUID] được thư viện
 * xử lý tự động thông qua các R2DBC converter đã đăng ký sẵn.
 *
 * @param T  Kiểu domain mà repository này sẽ quản lý, vì ưu tiên convention của thư viện nên lớp đại diện cho generic
 * đó phải là một **entity** (tức kế thừa [KEntity]).
 * @author <a href="https://github.com/henry0337">Muharux</a>
 * @see <a href="https://docs.spring.io/spring-data/relational/reference/kotlin/coroutines.html">Coroutine-specific Repository</a>
 */
@KotlinVariant
@NoRepositoryBean
public interface CoroutineRepository<T : KEntity> : CoroutineCrudRepository<T, Uuid>, CoroutineSortingRepository<T, Uuid> {

    /**
     * Tìm kiếm tất cả bản ghi có trong cơ sở dữ liệu, áp dụng tính năng **phân trang dữ liệu**.
     * @param pageable Tham số phân trang dữ liệu
     */
    public fun findAllBy(pageable: Pageable): Flow<T>
}
