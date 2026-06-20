package dev.myrlennia237.template.service.kotlin

import dev.myrlennia237.annotation.KotlinVariant
import dev.myrlennia237.config.AsyncAuditorAware
import dev.myrlennia237.internal.service.kotlin.Deletable
import dev.myrlennia237.internal.service.kotlin.Insertable
import dev.myrlennia237.internal.service.kotlin.Modifiable
import dev.myrlennia237.internal.service.kotlin.ReadableWithID
import dev.myrlennia237.internal.service.kotlin.Reversible
import dev.myrlennia237.template.service.BaseReactiveService
import org.springframework.beans.factory.annotation.Autowired

/**
 * @param T  Kiểu domain
 * @param ID Kiểu của primary key
 * @param I1 Kiểu DTO dùng để tạo mới entity (tham số của `insert`)
 * @param I2 Kiểu DTO dùng để cập nhật entity (tham số của `update`)
 * @author <a href="https://github.com/henry0337">Muharux</a>
 */
@KotlinVariant
abstract class CoroutineCrudService<T : Any, in ID, in I1, in I2> : BaseReactiveService(),
    ReadableWithID<T, ID>,
    Insertable<T, I1>,
    Modifiable<T, ID, I2>,
    Deletable<ID>,
    Reversible<ID> {

    @set:Autowired
    protected lateinit var auditorAware: AsyncAuditorAware

    abstract override suspend fun disable(id: ID)

    abstract override suspend fun enable(id: ID)
}
