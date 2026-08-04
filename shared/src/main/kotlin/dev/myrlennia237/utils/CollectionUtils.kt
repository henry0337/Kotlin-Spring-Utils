package dev.myrlennia237.utils

import dev.myrlennia237.component.ImmutableList

public object CollectionUtils {
    /**
     * Đánh dấu một [List] sẽ không thể thực hiện các tác vụ liên quan tới chính sửa dữ liệu bên trong chúng
     * sau khi được chuyển đổi.
     *
     * @return Một phiên bản bất biến sau chuyển đổi của [List].
     */
    @JvmStatic
    public fun <E : Any> toImmutableList(list: List<E>): ImmutableList<E> = ImmutableList.copyFrom(list)
}
