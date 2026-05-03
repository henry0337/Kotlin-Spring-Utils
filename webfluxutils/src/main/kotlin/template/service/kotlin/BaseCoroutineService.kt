package template.service.kotlin

import annotation.KotlinVariant
import internal.service.kotlin.*
import template.service.BaseReactiveService

@KotlinVariant
abstract class BaseCoroutineService<T : Any, ID, I1, I2> :
    BaseReactiveService(),
    ReadableWithID<T, ID>,
    Insertable<T, I1>,
    Modifiable<T, ID, I2>,
    Deletable<ID>,
    Reversible<ID>
