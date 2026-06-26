package dev.myrlennia237.template.repository

import dev.myrlennia237.annotation.KotlinVariant
import dev.myrlennia237.template.entity.Entity
import kotlinx.coroutines.flow.Flow
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.NoRepositoryBean
import org.springframework.data.repository.Repository
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.data.repository.kotlin.CoroutineSortingRepository
import kotlin.uuid.Uuid

/**
 * Repository đặc thù cho phía Kotlin, sử dụng chính các API của Kotlin để thực hiện các tác vụ CRUD.
 *
 * **Ghi chú**: Repository này coi tham số generic `ID` của [Repository] mặc định là [kotlin.uuid.Uuid], hãy để ý rằng
 * khi sử dụng repository này bạn phải chuyển đổi kiểu đấy sang kiểu [java.util.UUID] trước khi **lưu bản ghi vào cơ sở
 * dữ liệu**, vì framework chỉ có thể tương tác qua các API từ Java.
 *
 * @param T  Kiểu domain mà repository này sẽ quản lý, vì ưu tiên convention của thư viện nên lớp đại diện cho generic
 * đó phải là một **entity** (tức kế thừa [Entity]).
 * @author <a href="https://github.com/henry0337">Muharux</a>
 * @see <a href="https://docs.spring.io/spring-data/relational/reference/kotlin/coroutines.html">Coroutine-specific Repository</a>
 */
@KotlinVariant
@NoRepositoryBean
public interface CoroutineRepository<T : Entity> : CoroutineCrudRepository<T, Uuid>, CoroutineSortingRepository<T, Uuid> {

    /**
     * Tìm kiếm tất cả bản ghi có trong cơ sở dữ liệu, áp dụng tính năng **phân trang dữ liệu**.
     * @param pageable Tham số phân trang dữ liệu
     */
    public fun findAllBy(pageable: Pageable): Flow<T>
}
