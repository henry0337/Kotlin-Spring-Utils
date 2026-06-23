package dev.myrlennia237.template.service.java

import dev.myrlennia237.config.AsyncAuditorAware
import dev.myrlennia237.internal.service.java.Deletable
import dev.myrlennia237.internal.service.java.Insertable
import dev.myrlennia237.internal.service.java.Modifiable
import dev.myrlennia237.internal.service.java.ReadableWithID
import dev.myrlennia237.internal.service.java.Reversible
import dev.myrlennia237.template.service.BaseReactiveService
import dev.myrlennia237.util.ReactorHelper
import org.springframework.beans.factory.annotation.Autowired

/**
 * @param T  Kiểu domain
 * @param I1 Kiểu DTO dùng để tạo mới entity (tham số của `insert`)
 * @param I2 Kiểu DTO dùng để cập nhật entity (tham số của `update`)
 * @author <a href="https://github.com/henry0337">Muharux</a>
 */
abstract class AbstractCrudService<T : Any, in I1, in I2> : BaseReactiveService(),
    ReadableWithID<T>,
    Insertable<T, I1>,
    Modifiable<T, I2>,
    Deletable,
    Reversible {

    @set:Autowired
    protected lateinit var auditorAware: AsyncAuditorAware

    @set:Autowired
    protected lateinit var reactorUtil: ReactorHelper
}
