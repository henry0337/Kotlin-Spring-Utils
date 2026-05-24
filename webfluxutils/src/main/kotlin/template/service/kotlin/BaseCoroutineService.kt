package template.service.kotlin

import annotation.KotlinVariant
import internal.service.kotlin.*
import template.service.BaseReactiveService

/**
 * Kotlin coroutine CRUD service base cho các template service của module WebFlux.
 *
 * @author <a href="https://github.com/henry0337">Myrlennia</a>
 */
@KotlinVariant
abstract class BaseCoroutineService<T : Any, ID, I1, I2> :
    BaseReactiveService(),
    ReadableWithID<T, ID>,
    Insertable<T, I1>,
    Modifiable<T, ID, I2>,
    Deletable<ID>,
    Reversible<ID>
