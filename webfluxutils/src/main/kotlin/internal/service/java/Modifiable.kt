package internal.service.java

import reactor.core.publisher.Mono

internal fun interface Modifiable<T : Any, in ID, in I> {
    fun update(id: ID, body: I): Mono<T>
}
