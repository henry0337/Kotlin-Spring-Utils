package internal.entity

import java.time.LocalDateTime

internal interface Restorable {
    fun getRemovedState(): Short

    fun setRemovedState(removed: Short)

    fun getDeletedTimestamp(): LocalDateTime?

    fun setRemovedTimestamp(deletedAt: LocalDateTime?)

    fun isDeleted(): Boolean {
        return getRemovedState().toInt() == 1
    }

    fun markAsDeleted() {
        setRemovedState(1.toShort())
        setRemovedTimestamp(LocalDateTime.now())
    }

    fun restore() {
        setRemovedState(0.toShort())
        setRemovedTimestamp(null)
    }
}