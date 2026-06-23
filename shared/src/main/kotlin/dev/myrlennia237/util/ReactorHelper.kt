package dev.myrlennia237.util

import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
class ReactorHelper {
    /**
     * Tạo một [Mono] mới phát ra một [data] được chỉ định, dữ liệu đó sẽ được thu thập vào thời điểm khởi tạo.
     *
     * @param T    Kiểu dữ liệu của dữ liệu đầu vào
     * @param data Dữ liệu sẽ được phát ra
     * @see Mono.just
     */
    fun <T : Any> single(data: T): Mono<T> = Mono.just(data)

    /**
     * Tạo một [Mono] mới phát ra một [instance] được chỉ định nếu nó không `null`, ngược lại sẽ phát ra [Mono.empty].
     *
     * @param T        Kiểu dữ liệu của dữ liệu đầu vào
     * @param instance Dữ liệu sẽ được phát ra
     * @see Mono.justOrEmpty
     */
    fun <T : Any> singleOrEmpty(instance: T?): Mono<T> = Mono.justOrEmpty(instance)

    /**
     * Tạo một [Mono] mà không phát ra bất cứ dữ liệu nào cả.
     *
     * @param T Kiểu dữ liệu của dữ liệu đầu vào
     * @see Mono.empty
     */
    fun <T : Any> emptyMono(): Mono<T> = Mono.empty()

    /**
     * Tạo một [Flux] mà không phát ra bất cứ dữ liệu nào cả.
     *
     * @param T Kiểu dữ liệu của dữ liệu đầu vào
     * @see Flux.empty
     */
    fun <T : Any> emptyFlux(): Flux<T> = Flux.empty()

    /**
     * Lấy ra giá trị được bọc trong một [Mono] được chỉ định.
     *
     * @param T         Kiểu dữ liệu được wrap trong [Mono]
     * @param publisher Đối tượng [Mono] cần lấy giá trị được wrap tương ứng
     * @return Giá trị được wrap bên trong nếu tồn tại, nếu như [Mono.empty] thì trả về `null`.
     */
    fun <T : Any> awaitMono(publisher: Mono<T>): T? = publisher.block()

    /**
     * Lấy ra giá trị [List] được bọc trong một [Flux] được chỉ định.
     *
     * @param T         Kiểu dữ liệu được wrap trong [Flux]
     * @param publisher Đối tượng [Flux] cần lấy giá trị được wrap tương ứng
     * @return Danh sách giá trị được wrap bên trong nếu tồn tại, nếu như [Flux.empty] thì trả về `null`.
     */
    fun <T : Any> awaitFluxToList(publisher: Flux<T>): List<T>? = publisher.collectList().block()
}
