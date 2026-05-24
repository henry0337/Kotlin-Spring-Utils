package template.service.java

import internal.service.java.*
import template.service.BaseReactiveService

/**
 * Java CRUD service base cho các template service của module WebFlux.
 *
 * @author <a href="https://github.com/henry0337">Myrlennia</a>
 */
abstract class BaseReactiveCrudService<T : Any, ID, I1, I2> :
    BaseReactiveService(),
    ReadableWithID<T, ID>,
    Insertable<T, I1>,
    Modifiable<T, ID, I2>,
    Deletable<ID>,
    Reversible<ID>
