package internal.entity

internal interface Conflictable {
    fun getVersion(): Long

    fun setVersion(version: Long)
}