package dev.myrlennia237.template.service.kotlin

import dev.myrlennia237.annotation.KotlinVariant
import dev.myrlennia237.internal.service.kotlin.*
import dev.myrlennia237.template.service.BaseReactiveService

/**
 * **[[Kotlin Coroutine Variant]]**
 *
 * Base class cho service CRUD dành cho dự án **Kotlin** trên môi trường WebFlux.
 *
 * Subclass phải implement 6 suspend operations:
 * `findAll`, `findById`, `insert`, `update`, `deleteById`, `disable`.
 * Tất cả đều trả về trực tiếp kiểu kết quả thay vì bọc trong `Mono`.
 *
 * @param T  Kiểu entity domain
 * @param ID Kiểu của primary key
 * @param I1 Kiểu DTO dùng để tạo mới entity (tham số của `insert`)
 * @param I2 Kiểu DTO dùng để cập nhật entity (tham số của `update`)
 *
 * @author <a href="https://github.com/henry0337">Ademia</a>
 */
@KotlinVariant
abstract class BaseCoroutineService<T : Any, ID, I1, I2> :
    BaseReactiveService(),
    ReadableWithID<T, ID>,
    Insertable<T, I1>,
    Modifiable<T, ID, I2>,
    Deletable<ID>,
    Reversible<ID>
