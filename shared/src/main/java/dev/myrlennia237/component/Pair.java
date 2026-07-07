package dev.myrlennia237.component;

import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.Nullable;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Cặp hai giá trị bất biến, port từ {@link kotlin.Pair} của Kotlin.
 * <p>
 * Hai thành phần được truy cập qua {@link #first()} và {@link #second()}. Cả hai đều có thể mang giá trị {@code null},
 * đúng như {@code Pair} bên Kotlin.
 * </p>
 *
 * <p><b>Đây là phiên bản được port một phần từ ngôn ngữ Kotlin.</b></p>
 *
 * @param <A> Kiểu của thành phần thứ nhất.
 * @param <B> Kiểu của thành phần thứ hai.
 * @author <a href="https://github.com/henry0337">Muharux</a>
 */
@SuppressWarnings("unused")
public record Pair<A extends @Nullable Object, B extends @Nullable Object>(A first, B second)
        implements Map.Entry<A, B>, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Tạo một {@link Pair} từ hai giá trị cho trước — tương đương phép {@code first to second} bên Kotlin.
     *
     * @param first  Thành phần thứ nhất.
     * @param second Thành phần thứ hai.
     * @return Một {@link Pair} mới chứa hai giá trị đã cung cấp.
     */
    @Contract("_, _ -> new")
    public static <A extends @Nullable Object, B extends @Nullable Object> Pair<A, B> of(A first, B second) {
        return new Pair<>(first, second);
    }

    /**
     * Trả về một {@link Pair} mới với vị trí của hai thành phần được hoán đổi cho nhau.
     */
    @Contract(" -> new")
    public Pair<B, A> swap() {
        return new Pair<>(second, first);
    }

    /**
     * Trả về một {@link Pair} mới, giữ nguyên {@link #second()} và thay {@link #first()} bằng kết quả của {@code mapper}.
     *
     * @param mapper Logic chuyển đổi thành phần thứ nhất.
     * @return {@link Pair} mới sau chuyển đổi.
     */
    @Contract("_ -> new")
    public <A2 extends @Nullable Object> Pair<A2, B> mapFirst(Function<? super A, ? extends A2> mapper) {
        return new Pair<>(mapper.apply(first), second);
    }

    /**
     * Trả về một {@link Pair} mới, giữ nguyên {@link #first()} và thay {@link #second()} bằng kết quả của {@code mapper}.
     *
     * @param mapper Logic chuyển đổi thành phần thứ hai.
     * @return {@link Pair} mới sau chuyển đổi.
     */
    @Contract("_ -> new")
    public <B2 extends @Nullable Object> Pair<A, B2> mapSecond(Function<? super B, ? extends B2> mapper) {
        return new Pair<>(first, mapper.apply(second));
    }

    /**
     * Áp dụng {@code transformer} lên cả hai thành phần và trả về kết quả — tiện để rút gọn việc gọi
     * {@link #first()}/{@link #second()} thủ công.
     *
     * @param transformer Hàm nhận {@code (first, second)}.
     * @return Kết quả do {@code transformer} trả về.
     */
    public <R extends @Nullable Object> R map(BiFunction<? super A, ? super B, ? extends R> transformer) {
        return transformer.apply(first, second);
    }

    /**
     * {@inheritDoc}
     *
     * @deprecated Method của {@link Map.Entry} với tên không hợp ngữ nghĩa {@code Pair}. Dùng {@link #first()} thay thế.
     */
    @Deprecated(since = "0.1.0")
    @Contract(pure = true)
    public A getKey() {
        return first;
    }

    /**
     * {@inheritDoc}
     *
     * @deprecated Method của {@link Map.Entry} với tên không hợp ngữ nghĩa {@code Pair}. Dùng {@link #second()} thay thế.
     */
    @Deprecated(since = "0.1.0")
    @Contract(pure = true)
    public B getValue() {
        return second;
    }

    /**
     * Không được hỗ trợ — {@code Pair} là bất biến. Để "thay" giá trị thứ hai, tạo một {@link Pair} mới qua
     * {@link #mapSecond(Function)} hoặc {@link #of(Object, Object)}.
     *
     * @throws UnsupportedOperationException luôn luôn.
     * @deprecated {@code Pair} bất biến nên không hỗ trợ mutate. Dùng {@link #mapSecond(Function)} để tạo bản mới.
     */
    @Deprecated(since = "0.1.0")
    @Contract("_ -> fail")
    public B setValue(B value) {
        throw new UnsupportedOperationException("Pair là bất biến; dùng mapSecond(...) để tạo một Pair mới.");
    }

    @Override
    public String toString() {
        return "(" + first + ", " + second + ")";
    }
}
