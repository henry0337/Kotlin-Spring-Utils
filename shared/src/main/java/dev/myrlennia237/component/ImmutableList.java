package dev.myrlennia237.component;

import org.jetbrains.annotations.Contract;
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
 * Phiên bản bất biến (về mặt cấu trúc) của {@link List}, chỉ hỗ trợ các thao tác đọc.
 * <p>
 * Mọi thao tác ghi kế thừa từ {@link List} — {@link List#add add}, {@link List#set set}, {@link List#remove remove},
 * {@link List#clear clear} đều ném ra {@link UnsupportedOperationException}.
 * </p>
 *
 * <p>
 * Các phương thức biến đổi như {@link #map}, {@link #filter}, {@link #sorted} không thay đổi danh sách hiện tại mà trả
 * về một instance mới.
 * </p>
 *
 * <p><b>Đây là phiên bản được port một phần từ ngôn ngữ Kotlin.</b></p>
 * @param <E> Kiểu phần tử trong danh sách.
 * @author <a href="https://github.com/henry0337">Muharux</a>
 * @author <a href="https://github.com/AdorableDandelion25">Himekawa</a>
 */
@SuppressWarnings("unused")
public final class ImmutableList<E> extends AbstractList<E> {

    @SuppressWarnings("rawtypes")
    private static final ImmutableList EMPTY = new ImmutableList<>(new Object[0]);

    private final Object[] elements;

    private ImmutableList(Object[] elements) {
        this.elements = elements;
    }

    /**
     * Tạo một {@link ImmutableList} từ các phần tử được cung cấp trực tiếp.
     *
     * @param elements Các phần tử cần đưa vào danh sách.
     * @return Một {@link ImmutableList} mới chứa tất cả phần tử đã cung cấp.
     */
    @SafeVarargs
    @SuppressWarnings("java:S923")
    public static <E> ImmutableList<E> of(E... elements) {
        if (elements.length == 0) {
            return empty();
        }
        return new ImmutableList<>(Arrays.copyOf(elements, elements.length));
    }

    /**
     * Tạo một {@link ImmutableList} được sao chép từ một {@link Iterable} bất kỳ.
     *
     * <p>Nếu {@code source} đã là {@link ImmutableList}, trả về chính nó mà không tạo bản sao.
     *
     * @param source Nguồn dữ liệu để sao chép.
     * @return Một {@link ImmutableList} chứa tất cả phần tử từ {@code source}.
     */
    public static <E> ImmutableList<E> copyFrom(Iterable<E> source) {
        if (source instanceof ImmutableList) return (ImmutableList<E>) source;
        var buffer = new ArrayList<E>();
        source.forEach(buffer::add);
        return new ImmutableList<>(buffer.toArray());
    }

    /**
     * Trả về một {@link ImmutableList} rỗng.
     */
    @Contract(pure = true)
    @SuppressWarnings({"unchecked", "java:S2384"}) // EMPTY là bất biến nên có thể chia sẻ trực tiếp
    public static <E> ImmutableList<E> empty() {
        return EMPTY;
    }

    @Override
    @Contract(pure = true)
    @SuppressWarnings("unchecked")
    public E get(int index) {
        Objects.checkIndex(index, elements.length);
        return (E) elements[index];
    }

    @Override
    @Contract(pure = true)
    public int size() {
        return elements.length;
    }

    @Override
    @Contract(pure = true)
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
     * Trả về phần tử đầu tiên thỏa mãn điều kiện tìm kiếm thông qua {@code predicate}, hoặc {@code null} nếu không có
     * bất cứ data nào thỏa mãn.
     */
    public @Nullable E find(Predicate<E> predicate) {
        for (E e : this) {
            if (predicate.test(e)) {
                return e;
            }
        }
        return null;
    }

    /**
     * Trả về phần tử đầu tiên trong danh sách.
     * @throws NoSuchElementException nếu danh sách rỗng.
     */
    public E first() {
        if (isEmpty()) {
            throw new NoSuchElementException("ImmutableList is empty");
        }
        return get(0);
    }

    /**
     * Trả về phần tử đầu tiên trong danh sách, hoặc {@code null} nếu như danh sách này rỗng.
     */
    public @Nullable E firstOrNull() {
        return isEmpty() ? null : get(0);
    }

    /**
     * Trả về phần tử cuối cùng trong danh sách.
     * @throws NoSuchElementException nếu danh sách rỗng.
     */
    public E last() {
        if (isEmpty()) {
            throw new NoSuchElementException("ImmutableList is empty");
        }
        return get(elements.length - 1);
    }

    /**
     * Trả về phần tử cuối cùng trong danh sách, hoặc {@code null} nếu như danh sách này rỗng.
     */
    public @Nullable E lastOrNull() {
        return isEmpty() ? null : get(elements.length - 1);
    }

    /**
     * Trả về {@code true} nếu như có <b>bất cứ phần tử nào</b> được tìm thấy thỏa mãn điều kiện đặt ra bởi {@code predicate},
     * ngoài ra trả về {@code false}.
     *
     * @param predicate Điều kiện chỉ định
     * @return Giá trị {@code boolean} tương ứng.
     */
    public boolean any(Predicate<E> predicate) {
        for (E e : this) {
            if (predicate.test(e)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Trả về {@code true} nếu như <b>tất cả phần tử</b> trong danh sách thỏa mãn điều kiện đặt ra bởi {@code predicate},
     * ngoài ra trả về {@code false}.
     *
     * @param predicate Điều kiện chỉ định
     * @return Giá trị {@code boolean} tương ứng.
     */
    public boolean all(Predicate<E> predicate) {
        for (E e : this) {
            if (!predicate.test(e)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Trả về {@code true} nếu như <b>không có phần tử nào</b> trong danh sách thỏa mãn điều kiện đưa ra bởi {@code predicate},
     * ngoài ra trả về {@code false}.
     *
     * @param predicate Điều kiện chỉ định
     * @return Giá trị {@code boolean} tương ứng.
     */
    public boolean none(Predicate<E> predicate) {
        for (E e : this) {
            if (predicate.test(e)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Lấy ra số lượng phần tử trong danh sách thỏa mãn điều kiện đưa ra bởi {@code predicate}.
     * @param predicate Điều kiện chỉ định
     * @return Số lượng phần tử thỏa mãn điều kiện.
     */
    public int count(Predicate<E> predicate) {
        var n = 0;
        for (E e : this) {
            if (predicate.test(e)) {
                n++;
            }
        }
        return n;
    }

    /**
     * Áp dụng {@code mapper} lên từng phần tử, trả về {@link ImmutableList} mới chứa kết quả.
     */
    @Contract("_ -> new")
    public <R> ImmutableList<R> map(Function<E, R> mapper) {
        var result = new Object[elements.length];
        for (var i = 0; i < elements.length; i++) {
            result[i] = mapper.apply(get(i));
        }
        return new ImmutableList<>(result);
    }

    /**
     * Trả về một danh sách chứa kết quả sau khi áp dụng logic của {@code transformer} được cung cấp cho từng phần tử
     * và chỉ số của nó vào danh sách ban đầu.
     *
     * @param transformer Logic chuyển đổi giá trị
     * @return Danh sách bất biến mới chứa kết quả sau chuyển đổi.
     */
    @Contract("_ -> new")
    public <R> ImmutableList<R> mapIndexed(BiFunction<Integer, E, R> transformer) {
        var result = new Object[elements.length];
        for (var i = 0; i < elements.length; i++) {
            result[i] = transformer.apply(i, get(i));
        }
        return new ImmutableList<>(result);
    }

    /**
     * Trả về một danh sách chỉ chứa các phần tử thỏa mãn {@code predicate} chỉ định.
     *
     * @param predicate Điều kiện chỉ định
     * @return Danh sách chứa các phần tử thỏa mãn.
     */
    @Contract("_ -> new")
    public ImmutableList<E> filter(Predicate<E> predicate) {
        var buffer = new ArrayList<E>();
        for (E e : this) {
            if (predicate.test(e)) {
                buffer.add(e);
            }
        }
        return new ImmutableList<>(buffer.toArray());
    }

    /**
     * Trả về một danh sách duy nhất chứa tất cả các phần tử thu được từ việc thực hiện logic biến đổi qua {@code transformer}
     * trên từng phần tử của danh sách ban đầu.
     *
     * @param transformer Logic chuyển đổi giá trị
     * @return Danh sách bất biến mới chứa kết quả sau chuyển đổi.
     */
    @Contract("_ -> new")
    public <R> ImmutableList<R> flatMap(Function<E, Iterable<R>> transformer) {
        var buffer = new ArrayList<R>();
        for (E e : this) {
            transformer.apply(e).forEach(buffer::add);
        }
        return new ImmutableList<>(buffer.toArray());
    }

    /**
     * Trả về một danh sách chỉ bao gồm {@code n} phần tử tính từ đầu danh sách.
     */
    public ImmutableList<E> take(int n) {
        if (n <= 0) {
            return empty();
        }
        return new ImmutableList<>(Arrays.copyOf(elements, Math.min(n, elements.length)));
    }

    /**
     * Trả về một danh sách bao gồm tất cả các phần tử đã loại trừ {@code n} phần tử ban đầu.
     */
    public ImmutableList<E> drop(int n) {
        if (n <= 0) {
            return this;
        }
        if (n >= elements.length) {
            return empty();
        }
        return new ImmutableList<>(Arrays.copyOfRange(elements, n, elements.length));
    }

    /**
     * Trả về một danh sách với các phần tử với thứ tự xuất hiện đảo ngược.
     */
    @Override
    @Contract(" -> new")
    public ImmutableList<E> reversed() {
        var result = Arrays.copyOf(elements, elements.length);
        for (int i = 0, j = result.length - 1; i < j; i++, j--) {
            var tmp = result[i];
            result[i] = result[j];
            result[j] = tmp;
        }
        return new ImmutableList<>(result);
    }

    /**
     * Trả về một danh sách mới chỉ chứa các phần tử <b>khác biệt</b> so với danh sách ban đầu.
     * <p>
     * Trong số các phần tử giống nhau của danh sách ban đầu, chỉ phần tử xuất hiện đầu tiên sẽ ở trong danh sách đầu ra.
     * Các phần tử trong danh sách đầu ra được sắp xếp theo cùng thứ tự như danh sách ban đầu.
     *
     * @see kotlin.collections.CollectionsKt#distinct(Iterable)
     */
    @Contract(" -> new")
    public ImmutableList<E> distinct() {
        var seen = new LinkedHashSet<>(this);
        return new ImmutableList<>(seen.toArray());
    }

    /**
     * Trả về một chuỗi cung cấp các phần tử của chuỗi này đã được sắp xếp theo thứ tự sắp xếp tự nhiên của chúng.
     *
     * @return {@link ImmutableList} mới đã được sắp xếp.
     */
    @Contract(" -> new")
    @SuppressWarnings("unchecked")
    public ImmutableList<E> sorted() {
        var result = Arrays.copyOf(elements, elements.length);
        Arrays.sort(result, (a, b) -> ((Comparable<Object>) a).compareTo(b));
        return new ImmutableList<>(result);
    }

    /**
     * Trả về một chuỗi cung cấp các phần tử của chuỗi này đã được sắp xếp theo {@code comparator} được chỉ định.
     *
     * @return {@link ImmutableList} mới đã được sắp xếp.
     */
    @Contract("_ -> new")
    @SuppressWarnings("unchecked")
    public ImmutableList<E> sortedWith(Comparator<E> comparator) {
        var result = (E[]) Arrays.copyOf(elements, elements.length);
        Arrays.sort(result, comparator);
        return new ImmutableList<>(result);
    }

    /**
     * Thực hiện {@code action} được chỉ định trên từng phần tử, đồng thời cung cấp chỉ số theo thứ tự cùng với phần tử đó.
     *
     * @param action Hàm nhận {@code (index, element)}.
     */
    public void forEachIndexed(BiConsumer<Integer, E> action) {
        for (var i = 0; i < elements.length; i++) {
            action.accept(i, get(i));
        }
    }

    /**
     * @return {@link ArrayList} mới chứa các phần tử từ danh sách này — có thể chỉnh sửa.
     */
    @Contract(value = " -> new", pure = true)
    public List<E> toList() {
        return new ArrayList<>(this);
    }
}
