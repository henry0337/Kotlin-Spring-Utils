package dev.myrlennia237.template.service

import dev.myrlennia237.internal.service.Deletable
import dev.myrlennia237.internal.service.Insertable
import dev.myrlennia237.internal.service.Modifiable
import dev.myrlennia237.internal.service.ReadableWithID
import dev.myrlennia237.internal.service.Reversible

/**
 * **[[Java-Interoperability Variant]]**
 *
 * Base class cho service CRUD dành cho dự án **Java** trên môi trường Spring MVC (blocking).
 *
 * Subclass phải implement 6 operations:
 * `findAll`, `findById`, `insert`, `update`, `deleteById`, `disable`.
 * `findById` trả về [java.util.Optional] — chuẩn JPA cho Java consumers.
 *
 * @param T  Kiểu entity domain
 * @param ID Kiểu của primary key
 * @param I1 Kiểu DTO dùng để tạo mới entity (tham số của `insert`)
 * @param I2 Kiểu DTO dùng để cập nhật entity (tham số của `update`)
 *
 * @author <a href="https://github.com/henry0337">Myrlennia</a>
 */
abstract class CrudService<T : Any, ID, I1, I2> :
    BaseService(),
    ReadableWithID<T, ID>,
    Insertable<T, I1>,
    Modifiable<T, ID, I2>,
    Deletable<ID>,
    Reversible<ID>