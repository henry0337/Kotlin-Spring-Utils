package internal.service.java

import reactor.core.publisher.Mono

internal fun interface Reversible<in ID> {
    fun disable(id: ID): Mono<Void>
}
