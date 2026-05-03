package template.service.java

import internal.service.java.*
import template.service.BaseReactiveService

abstract class BaseReactiveCrudService<T : Any, ID, I1, I2> :
    BaseReactiveService(),
    ReadableWithID<T, ID>,
    Insertable<T, I1>,
    Modifiable<T, ID, I2>,
    Deletable<ID>,
    Reversible<ID>
