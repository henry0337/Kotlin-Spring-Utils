package dev.myrlennia237.template.service.java

import dev.myrlennia237.config.AsyncAuditorAware
import dev.myrlennia237.internal.service.java.Deletable
import dev.myrlennia237.internal.service.java.Insertable
import dev.myrlennia237.internal.service.java.Modifiable
import dev.myrlennia237.internal.service.java.ReadableWithID
import dev.myrlennia237.internal.service.java.Reversible
import dev.myrlennia237.template.service.BaseReactiveService
import org.springframework.beans.factory.annotation.Autowired
import reactor.core.publisher.Mono

/**
 * @param T  Kiểu domain
 * @param ID Kiểu của primary key
 * @param I1 Kiểu DTO dùng để tạo mới entity (tham số của `insert`)
 * @param I2 Kiểu DTO dùng để cập nhật entity (tham số của `update`)
 * @author <a href="https://github.com/henry0337">Muharux</a>
 */
abstract class AbstractCrudService<T : Any, in ID, in I1, in I2> : BaseReactiveService(),
    ReadableWithID<T, ID>,
    Insertable<T, I1>,
    Modifiable<T, ID, I2>,
    Deletable<ID>,
    Reversible<ID> {

    @set:Autowired
    protected lateinit var auditorAware: AsyncAuditorAware

    abstract override fun disable(id: ID): Mono<Void>

    abstract override fun enable(id: ID): Mono<Void>
}
