package dev.myrlennia237.template.service.java

import dev.myrlennia237.internal.service.java.*
import dev.myrlennia237.template.service.BaseReactiveService

/**
 * **[[Java-Interoperability Variant]]**
 *
 * Base class cho service CRUD dành cho dự án **Java** trên môi trường WebFlux.
 *
 * Subclass phải implement 6 operations:
 * `findAll`, `findById`, `insert`, `update`, `deleteById`, `disable`.
 * Tất cả đều trả về `Mono<T>` hoặc `Mono<Void>`.
 *
 * @param T  Kiểu entity domain
 * @param ID Kiểu của primary key
 * @param I1 Kiểu DTO dùng để tạo mới entity (tham số của `insert`)
 * @param I2 Kiểu DTO dùng để cập nhật entity (tham số của `update`)
 *
 * @author <a href="https://github.com/henry0337">Myrlennia</a>
 */
abstract class BaseReactiveCrudService<T : Any, ID, I1, I2> :
    BaseReactiveService(),
    ReadableWithID<T, ID>,
    Insertable<T, I1>,
    Modifiable<T, ID, I2>,
    Deletable<ID>,
    Reversible<ID>
