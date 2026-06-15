package dev.myrlennia237.template.controller.kotlin

import dev.myrlennia237.annotation.KotlinVariant
import dev.myrlennia237.template.controller.BaseReactiveController

/**
 * **[[Kotlin Coroutine Variant]]**
 *
 * Base class cho REST controller CRUD dành cho dự án **Kotlin** trên môi trường WebFlux.
 *
 * Thêm `@RestController` và `@RequestMapping` tại subclass để khai báo endpoints.
 *
 * @author <a href="https://github.com/henry0337">Ademia</a>
 */
@KotlinVariant
abstract class BaseCoroutineController : BaseReactiveController()
