package internal.service.kotlin

import annotation.KotlinVariant
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

@KotlinVariant
internal fun interface Readable<T : Any> {
    suspend fun findAll(pageable: Pageable): Page<T>
}

@KotlinVariant
internal interface ReadableWithID<T : Any, in ID> : Readable<T> {
    suspend fun findById(id: ID): T?
}
