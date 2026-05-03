package internal.service.java

import reactor.core.publisher.Mono

internal fun interface Insertable<T : Any, in I> {
    fun insert(item: I): Mono<T>
}
