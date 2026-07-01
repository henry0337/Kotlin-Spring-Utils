package dev.myrlennia237.component;

import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Danh sách bất biến (immutable) được đảm bảo tại compile-time — không thể thêm, xóa, hay thay thế phần tử
 * sau khi khởi tạo.
 *
 * <p>Khác với {@link List#of(Object[])}, class này ngăn chặn mọi thao tác chỉnh sửa ngay từ kiểu dữ liệu
 * thay vì phải đợi đến runtime mới ném {@link UnsupportedOperationException}.
 *
 * <p><b>Khởi tạo:</b>
 * <pre>{@code
 * ImmutableList<String> list = ImmutableList.of("a", "b", "c");
 * ImmutableList<String> copy = ImmutableList.copyFrom(existingList);
 * ImmutableList<String> none = ImmutableList.empty();
 * }</pre>
 *
 * @param <T> Kiểu phần tử trong danh sách.
 * @author <a href="https://github.com/henry0338">Muharux</a>
 */
public final class ImmutableList<T> extends AbstractList<T> {

    @SuppressWarnings("rawtypes")
    private static final ImmutableList EMPTY = new ImmutableList<>(new Object[0]);

    private final Object[] elements;

    private ImmutableList(Object[] elements) {
        this.elements = elements;
    }

    @Override
    @Contract(value = "null -> false", pure = true)
    public boolean equals(Object o) {
        if (!(o instanceof ImmutableList<?> that)) return false;
        if (!super.equals(o)) return false;
        return Objects.deepEquals(elements, that.elements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), Arrays.hashCode(elements));
    }

    /**
     * Tạo một {@link ImmutableList} từ các phần tử được cung cấp trực tiếp.
     *
     * @param elements Các phần tử cần đưa vào danh sách.
     * @return Một {@link ImmutableList} mới chứa tất cả phần tử đã cung cấp.
     */
    @SafeVarargs
    public static <T> ImmutableList<T> of(T @NonNull ... elements) {
        if (elements.length == 0) return empty();
        return new ImmutableList<>(Arrays.copyOf(elements, elements.length));
    }

    /**
     * Tạo một {@link ImmutableList} từ một {@link Iterable} bất kỳ.
     *
     * <p>Nếu {@code source} đã là {@link ImmutableList}, trả về chính nó mà không tạo bản sao.
     *
     * @param source Nguồn dữ liệu để sao chép.
     * @return Một {@link ImmutableList} chứa tất cả phần tử từ {@code source}.
     */

    public static <T> @NonNull ImmutableList<T> copyFrom(Iterable<T> source) {
        if (source instanceof ImmutableList) return (ImmutableList<T>) source;
        var buffer = new ArrayList<T>();
        source.forEach(buffer::add);
        return new ImmutableList<>(buffer.toArray());
    }

    /**
     * @return Một {@link ImmutableList} rỗng, dùng chung singleton.
     */
    @Contract(pure = true)
    @SuppressWarnings("unchecked")
    public static <T> ImmutableList<T> empty() {
        return EMPTY;
    }

    // -------------------------------------------------------------------------
    // Core — AbstractList yêu cầu
    // -------------------------------------------------------------------------

    @Override
    @SuppressWarnings("unchecked")
    public T get(int index) {
        Objects.checkIndex(index, elements.length);
        return (T) elements[index];
    }

    @Override
    public int size() {
        return elements.length;
    }

    /**
     * Trả về phần tử đầu tiên thỏa mãn {@code predicate}, hoặc {@code null} nếu không tìm thấy.
     */
    public @Nullable T find(Predicate<T> predicate) {
        for (T e : this) if (predicate.test(e)) return e;
        return null;
    }

    /**
     * @return Phần tử đầu tiên trong danh sách.
     * @throws NoSuchElementException nếu danh sách rỗng.
     */
    public T first() {
        if (isEmpty()) throw new NoSuchElementException("ImmutableList is empty");
        return get(0);
    }

    /**
     * @return Phần tử đầu tiên, hoặc {@code null} nếu danh sách rỗng.
     */
    public @Nullable T firstOrNull() {
        return isEmpty() ? null : get(0);
    }

    /**
     * @return Phần tử cuối cùng trong danh sách.
     * @throws NoSuchElementException nếu danh sách rỗng.
     */
    public T last() {
        if (isEmpty()) throw new NoSuchElementException("ImmutableList is empty");
        return get(elements.length - 1);
    }

    /**
     * @return Phần tử cuối cùng, hoặc {@code null} nếu danh sách rỗng.
     */
    public @Nullable T lastOrNull() {
        return isEmpty() ? null : get(elements.length - 1);
    }

    /**
     * @return {@code true} nếu ít nhất một phần tử thỏa mãn {@code predicate}.
     */
    public boolean any(Predicate<T> predicate) {
        for (T e : this) if (predicate.test(e)) return true;
        return false;
    }

    /**
     * @return {@code true} nếu tất cả phần tử đều thỏa mãn {@code predicate}.
     */
    public boolean all(Predicate<T> predicate) {
        for (T e : this) if (!predicate.test(e)) return false;
        return true;
    }

    /**
     * @return {@code true} nếu không có phần tử nào thỏa mãn {@code predicate}.
     */
    public boolean none(Predicate<T> predicate) {
        for (T e : this) if (predicate.test(e)) return false;
        return true;
    }

    /**
     * @return Số phần tử thỏa mãn {@code predicate}.
     */
    public int count(Predicate<T> predicate) {
        int n = 0;
        for (T e : this) if (predicate.test(e)) n++;
        return n;
    }

    /**
     * Áp dụng {@code mapper} lên từng phần tử, trả về {@link ImmutableList} mới chứa kết quả.
     */
    @Contract("_ -> new")
    public <R> @NonNull ImmutableList<R> map(Function<T, R> mapper) {
        var result = new Object[elements.length];
        for (int i = 0; i < elements.length; i++) result[i] = mapper.apply(get(i));
        return new ImmutableList<>(result);
    }

    /**
     * Lọc các phần tử thỏa mãn {@code predicate}, trả về {@link ImmutableList} mới.
     */
    @Contract("_ -> new")
    public @NonNull ImmutableList<T> filter(Predicate<T> predicate) {
        var buffer = new ArrayList<T>();
        for (T e : this) if (predicate.test(e)) buffer.add(e);
        return new ImmutableList<>(buffer.toArray());
    }

    /**
     * Áp dụng {@code mapper} rồi gộp phẳng kết quả, trả về {@link ImmutableList} mới.
     */
    @Contract("_ -> new")
    public <R> @NonNull ImmutableList<R> flatMap(Function<T, Iterable<R>> mapper) {
        var buffer = new ArrayList<R>();
        for (T e : this) mapper.apply(e).forEach(buffer::add);
        return new ImmutableList<>(buffer.toArray());
    }

    /**
     * Ánh xạ kèm index.
     *
     * @param mapper Hàm nhận {@code (index, element)} và trả về kết quả.
     */
    @Contract("_ -> new")
    public <R> @NonNull ImmutableList<R> mapIndexed(BiFunction<Integer, T, R> mapper) {
        var result = new Object[elements.length];
        for (int i = 0; i < elements.length; i++) result[i] = mapper.apply(i, get(i));
        return new ImmutableList<>(result);
    }

    /**
     * @return {@link ImmutableList} mới chỉ chứa {@code n} phần tử đầu tiên.
     */
    public ImmutableList<T> take(int n) {
        if (n <= 0) return empty();
        return new ImmutableList<>(Arrays.copyOf(elements, Math.min(n, elements.length)));
    }

    /**
     * @return {@link ImmutableList} mới bỏ qua {@code n} phần tử đầu tiên.
     */
    public ImmutableList<T> drop(int n) {
        if (n <= 0) return this;
        if (n >= elements.length) return empty();
        return new ImmutableList<>(Arrays.copyOfRange(elements, n, elements.length));
    }

    /**
     * @return {@link ImmutableList} mới theo thứ tự ngược lại.
     */
    @Override
    @Contract(" -> new")
    public @NonNull ImmutableList<T> reversed() {
        var result = Arrays.copyOf(elements, elements.length);
        for (int i = 0, j = result.length - 1; i < j; i++, j--) {
            var tmp = result[i];
            result[i] = result[j];
            result[j] = tmp;
        }
        return new ImmutableList<>(result);
    }

    /**
     * @return {@link ImmutableList} mới loại bỏ các phần tử trùng lặp, giữ nguyên thứ tự xuất hiện đầu tiên.
     */
    @Contract(" -> new")
    public @NonNull ImmutableList<T> distinct() {
        var seen = new LinkedHashSet<T>();
        forEach(seen::add);
        return new ImmutableList<>(seen.toArray());
    }

    /**
     * Sắp xếp theo thứ tự tự nhiên — phần tử phải implement {@link Comparable}.
     *
     * @return {@link ImmutableList} mới đã được sắp xếp.
     */
    @Contract(" -> new")
    @SuppressWarnings("unchecked")
    public @NonNull ImmutableList<T> sorted() {
        var result = Arrays.copyOf(elements, elements.length);
        Arrays.sort(result, (a, b) -> ((Comparable<Object>) a).compareTo(b));
        return new ImmutableList<>(result);
    }

    /**
     * Sắp xếp theo {@code comparator}.
     *
     * @return {@link ImmutableList} mới đã được sắp xếp.
     */
    @Contract("_ -> new")
    @SuppressWarnings("unchecked")
    public @NonNull ImmutableList<T> sortedWith(Comparator<T> comparator) {
        var result = (T[]) Arrays.copyOf(elements, elements.length);
        Arrays.sort(result, comparator);
        return new ImmutableList<>(result);
    }

    /**
     * Duyệt qua từng phần tử kèm index.
     *
     * @param action Hàm nhận {@code (index, element)}.
     */
    public void forEachIndexed(BiConsumer<Integer, T> action) {
        for (int i = 0; i < elements.length; i++) action.accept(i, get(i));
    }

    /**
     * @return {@link ArrayList} mới chứa các phần tử từ danh sách này — có thể chỉnh sửa.
     */
    @Contract(value = " -> new", pure = true)
    public @NonNull List<T> toMutableList() {
        return new ArrayList<>(this);
    }
}
