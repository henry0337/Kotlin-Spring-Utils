@file:KotlinVariant
package dev.myrlennia237.extension

import dev.myrlennia237.annotation.KotlinVariant
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactor.awaitSingleOrNull
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

/**
 * Lấy giá trị được bọc trong [Mono] bằng cách suspend coroutine cho đến khi [Mono] này phát ra tín hiệu hoàn thành.
 *
 * @return Giá trị bên trong nếu [Mono] phát ra dữ liệu, hoặc `null` nếu là [Mono.empty].
 * @author <a href="https://github.com/henry0337">Muharux</a>
 */
@JvmSynthetic
public suspend fun <T : Any> Mono<T>.await(): T? = this.awaitSingleOrNull()

/**
 * Biến đổi một đối tượng [Flux] thành [List] theo cách **bất đồng bộ** của Kotlin.
 *
 * @return Danh sách tất cả phần tử [Flux] phát ra; trả về list rỗng nếu [Flux] không phát ra gì.
 * @author <a href="https://github.com/henry0337">Muharux</a>
 */
@JvmSynthetic
public suspend fun <T : Any> Flux<T>.awaitAsList(): List<T> = this.asFlow().toList()

/**
 * Bỏ qua giá trị được phát ra của [Mono] được chỉ định.
 *
 * Kết quả trả về cuối cùng được thay bằng `Mono<Void>`.
 *
 * @author <a href="https://github.com/henry0337">Muharux</a>
 * @see Mono.then
 */
@JvmSynthetic
public fun <T : Any> Mono<T>.discard(): Mono<Void> = this.then()
