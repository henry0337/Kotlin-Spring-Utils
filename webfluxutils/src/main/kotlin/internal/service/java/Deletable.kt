package internal.service.java

import reactor.core.publisher.Mono

internal fun interface Deletable<in ID> {
    fun deleteById(id: ID): Mono<Void>
}
