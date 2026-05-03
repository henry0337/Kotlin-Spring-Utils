package internal.entity

import utils.JavaLocalDateTime

internal interface Auditable {
    fun getCreatedAuditor(): String

    fun setCreatedAuditor(id: String)

    fun getCreatedDate(): JavaLocalDateTime

    fun setCreatedDate(creationDate: JavaLocalDateTime)

    fun getLastModifiedAuditor(): String?

    fun setLastModifiedAuditor(auditor: String?)

    fun getLastModifiedDate(): JavaLocalDateTime?

    fun setLastModifiedDate(lastModifiedDate: JavaLocalDateTime?)
}