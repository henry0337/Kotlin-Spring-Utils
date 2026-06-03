package dev.myrlennia237.template.service.kotlin

import dev.myrlennia237.annotation.KotlinVariant
import dev.myrlennia237.internal.service.kotlin.*
import dev.myrlennia237.template.service.BaseService

/**
 * **[[Kotlin Variant]]**
 *
 * Base class cho service CRUD dành cho dự án **Kotlin** trên môi trường Spring MVC (blocking).
 *
 * Subclass phải implement 6 operations:
 * `findAll`, `findById`, `insert`, `update`, `deleteById`, `disable`.
 * `findById` trả về `T?` thay vì [java.util.Optional] — idiomatic Kotlin.
 *
 * @param T  Kiểu entity domain
 * @param ID Kiểu của primary key
 * @param I1 Kiểu DTO dùng để tạo mới entity (tham số của `insert`)
 * @param I2 Kiểu DTO dùng để cập nhật entity (tham số của `update`)
 *
 * @author <a href="https://github.com/henry0338">Myrlennia</a>
 */
@KotlinVariant
abstract class BaseKotlinCrudService<T : Any, ID, I1, I2> :
    BaseService(),
    ReadableWithID<T, ID>,
    Insertable<T, I1>,
    Modifiable<T, ID, I2>,
    Deletable<ID>,
    Reversible<ID>
