package dev.myrlennia237.helper

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

/**
 * Helper cung cấp các toán tử được tùy chỉnh từ thư viện **Reactor**.
 *
 * Các phương thức được cung cấp trong này sẽ chỉ nên được tương tác **đơn lẻ** với từng toán tử khác nhau khác.
 * Không khuyến khích nối các toán tử này với các toán tử gốc của thư viện.
 * @see <a href="https://projectreactor.io/">Project Reactor</a>
 * @author <a href="https://github.com/henry0337">Muharux</a>
 */
public class ReactorHelper {
    /**
     * Tạo một [Mono] mới phát ra một [data] được chỉ định, dữ liệu đó sẽ được thu thập vào thời điểm khởi tạo.
     *
     * @param T         Kiểu dữ liệu của dữ liệu đầu vào
     * @param data      Dữ liệu sẽ được phát ra
     * @see Mono.just
     */
    public fun <T : Any> only(data: T): Mono<T> = Mono.just(data)

    /**
     * Tạo một [Mono] mới phát ra một [instance] được chỉ định nếu nó không `null`, ngược lại sẽ phát ra [Mono.empty].
     *
     * @param T                 Kiểu dữ liệu của dữ liệu đầu vào
     * @param instance          Dữ liệu sẽ được phát ra
     * @see Mono.justOrEmpty
     */
    public fun <T : Any> onlyOrEmpty(instance: T?): Mono<T> = Mono.justOrEmpty(instance)

    /**
     * Tạo một [Mono] mà không phát ra bất cứ dữ liệu nào cả.
     *
     * @param T Kiểu dữ liệu của dữ liệu đầu vào
     * @see Mono.empty
     */
    public fun <T : Any> emptyMono(): Mono<T> = Mono.empty()

    /**
     * Tạo một [Flux] mà không phát ra bất cứ dữ liệu nào cả.
     *
     * @param T Kiểu dữ liệu của dữ liệu đầu vào
     * @see Flux.empty
     */
    public fun <T : Any> emptyFlux(): Flux<T> = Flux.empty()

    /**
     * Lấy ra giá trị được bọc trong một [Mono] được chỉ định bằng cách **chặn luồng vô thời hạn** cho đến khi nhận được
     * tín hiệu kế tiếp.
     *
     * @param T     Kiểu dữ liệu được wrap trong [Mono]
     * @param mono  Đối tượng [Mono] cần lấy giá trị được wrap tương ứng
     * @return Giá trị được wrap bên trong nếu tồn tại, nếu như [Mono.empty] thì trả về `null`.
     */
    public fun <T : Any> waitUntilCompleted(mono: Mono<T>): T? = mono.block()

    /**
     * Đánh dấu một [Mono] sẽ được bỏ qua giá trị trả về, kết quả thực tế sẽ được thay bằng `Mono<Void>`.
     * @see Mono.then
     */
    @Suppress("kotlin:S6508")
    public fun <T : Any> disposeOf(mono: Mono<T>): Mono<Void> = mono.then()
}
