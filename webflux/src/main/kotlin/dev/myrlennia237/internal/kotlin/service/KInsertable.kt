package dev.myrlennia237.internal.kotlin.service

import dev.myrlennia237.annotation.KotlinVariant

/**
 * @param T Dữ liệu đầu ra, thường là Aggregate Root.
 * @param I Dữ liệu đầu vào, thường là DTO, Projection hoặc tương tự.
 * @author <a href="https://github.com/henry0337">Muharux</a>
 */
@KotlinVariant
internal fun interface KInsertable<out T, in I> {
    /**
     * Tạo mới một entity từ dữ liệu đầu vào.
     * @param item Dữ liệu đầu vào dùng để tạo entity
     */
    suspend fun insert(item: I): T
}
