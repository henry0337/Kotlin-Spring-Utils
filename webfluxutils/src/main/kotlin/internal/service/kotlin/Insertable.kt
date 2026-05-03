package internal.service.kotlin

import annotation.KotlinVariant

@KotlinVariant
internal fun interface Insertable<out T, in I> {
    suspend fun insert(item: I): T
}
