package dev.myrlennia237.sample

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
internal interface FooRepository : JpaRepository<Foo, UUID>