package dev.myrlennia237.template.controller.kotlin

import dev.myrlennia237.annotation.KotlinVariant
import dev.myrlennia237.template.controller.BaseController

/**
 * **[[Kotlin Variant]]**
 *
 * Base class cho REST controller CRUD dành cho dự án **Kotlin** trên môi trường Spring MVC.
 *
 * Thêm `@RestController` và `@RequestMapping` tại subclass để khai báo endpoints.
 *
 * @author <a href="https://github.com/henry0338">Myrlennia</a>
 */
@KotlinVariant
abstract class BaseKotlinCrudController : BaseController()
