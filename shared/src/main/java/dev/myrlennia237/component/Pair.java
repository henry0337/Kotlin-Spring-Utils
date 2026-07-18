package dev.myrlennia237.component;

import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.Nullable;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

/**
 * Đại diện cho một cặp giá trị bất biến chỉ bao gồm duy nhất 2 phần tử có thể nullable.
 *
 * <p><b>Đây là phiên bản được port hoàn chỉnh từ ngôn ngữ Kotlin.</b></p>
 *
 * @param <A> Thành phần thứ nhất.
 * @param <B> Thành phần thứ hai.
 * @author <a href="https://github.com/henry0337">Muharux</a>
 * @author <a href="https://github.com/AdorableDandelion25">Himekawa</a>
 */
@SuppressWarnings("unused")
public record Pair<A extends @Nullable Object, B extends @Nullable Object>(A first, B second)
        implements Map.Entry<A, B>, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Khởi tạo một {@link Pair} chứa giá trị của 2 phần tử {@code first} và {@code second}.
     *
     * @param first  Giá trị được gán vào cho generic {@code A}
     * @param second Giá trị được gán vào cho generic {@code B}
     */
    @Contract("_, _ -> new")
    public static <A extends @Nullable Object, B extends @Nullable Object> Pair<A, B> of(A first, B second) {
        return new Pair<>(first, second);
    }

    /**
     * {@inheritDoc}
     *
     * @deprecated In favor of {@link Pair}'s {@link Pair#first} property.
     */
    @Contract(pure = true)
    @Deprecated(since = "0.1.0", forRemoval = false)
    public A getKey() {
        return first;
    }

    /**
     * {@inheritDoc}
     *
     * @deprecated In favor of {@link Pair}'s {@link Pair#second} property.
     */
    @Contract(pure = true)
    @Deprecated(since = "0.1.0", forRemoval = false)
    public B getValue() {
        return second;
    }

    /**
     * {@inheritDoc}
     *
     * @deprecated Any instance of this {@link Pair} class and its values would be immutable when created, so please
     * don't use this method, it would be confused and breaks the contract we made.
     */
    @Contract("_ -> fail")
    @Deprecated(since = "0.1.0", forRemoval = false)
    public B setValue(B value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        return "(" + first + ", " + second + ")";
    }
}
