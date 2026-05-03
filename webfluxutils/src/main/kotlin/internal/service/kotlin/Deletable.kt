package internal.service.kotlin

import annotation.KotlinVariant

@KotlinVariant
internal fun interface Deletable<in ID> {
    suspend fun deleteById(id: ID)
}
