package dev.myrlennia237.query

import org.springframework.data.relational.core.query.Criteria

/**
 * @author <a href="https://github.com/AdorableDandelion25">Himekawa</a>
 */
public fun interface Specification<T> {
    public fun toCriteria(): Criteria
    
    public fun and(other: Specification<T>): Specification<T> =
        Specification { toCriteria().and(other.toCriteria()) }
        
    public fun or(other: Specification<T>): Specification<T> =
        Specification { toCriteria().or(other.toCriteria()) }
        
    public companion object {
        @JvmStatic
        public fun <T> unrestricted(): Specification<T> = Specification { Criteria.empty() }

        @JvmStatic
        public fun <T> where(spec: Specification<T>?): Specification<T> = spec ?: unrestricted()
    }
}