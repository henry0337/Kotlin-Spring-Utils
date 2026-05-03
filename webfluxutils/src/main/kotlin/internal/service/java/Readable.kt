package internal.service.java

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import reactor.core.publisher.Mono

internal fun interface Readable<T : Any> {
    fun findAll(pageable: Pageable): Mono<Page<T>>
}

internal interface ReadableWithID<T : Any, in ID> : Readable<T> {
    fun findById(id: ID): Mono<T>
}
