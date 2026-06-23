package dev.myrlennia237.template.service

import com.querydsl.jpa.impl.JPAQueryFactory
import dev.myrlennia237.internal.service.Deletable
import dev.myrlennia237.internal.service.Insertable
import dev.myrlennia237.internal.service.Modifiable
import dev.myrlennia237.internal.service.ReadableWithID
import dev.myrlennia237.internal.service.Reversible
import org.springframework.beans.factory.annotation.Autowired

/**
 * **[[Java-Interoperability Variant]]**
 *
 * Base class cho service CRUD dành cho dự án **Java** trên môi trường Spring MVC (Servlet).
 *
 * Subclass phải implement 7 operations:
 * `findAll`, `findById`, `insert`, `update`, `deleteById`, `disable`, `enable`.
 *
 * @param T  Kiểu entity domain
 * @param I1 Kiểu DTO dùng để tạo mới entity (tham số của `insert`)
 * @param I2 Kiểu DTO dùng để cập nhật entity (tham số của `update`)
 *
 * @author <a href="https://github.com/henry0337">Muharux</a>
 */
abstract class CrudService<T : Any, I1, I2> : BaseService(),
    ReadableWithID<T>,
    Insertable<T, I1>,
    Modifiable<T, I2>,
    Deletable,
    Reversible {

    @set:Autowired(required = false)
    protected lateinit var queryFactory: JPAQueryFactory
}