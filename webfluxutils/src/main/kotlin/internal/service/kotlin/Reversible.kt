package internal.service.kotlin

import annotation.KotlinVariant

@KotlinVariant
internal fun interface Reversible<in ID> {
    suspend fun disable(id: ID)
}
