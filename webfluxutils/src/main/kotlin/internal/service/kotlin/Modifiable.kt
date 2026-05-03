package internal.service.kotlin

import annotation.KotlinVariant

@KotlinVariant
internal fun interface Modifiable<out T, in ID, in I> {
    suspend fun update(id: ID, body: I): T
}
