package dev.myrlennia237.sample

import dev.myrlennia237.annotation.spring.ApiController
import dev.myrlennia237.component.dto.PagedResponse
import dev.myrlennia237.template.controller.AbstractCrudController
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import java.util.UUID

@ApiController
internal class FooController : AbstractCrudController<FooDto, FooCreateDto, FooUpdateDto>() {
    override fun findAll(pageable: Pageable): ResponseEntity<PagedResponse<FooDto>> {
        TODO("Not yet implemented")
    }

    override fun findById(id: UUID): ResponseEntity<FooDto> {
        TODO("Not yet implemented")
    }

    override fun create(body: FooCreateDto): ResponseEntity<FooDto> {
        TODO("Not yet implemented")
    }

    override fun update(
        id: UUID,
        body: FooUpdateDto
    ): ResponseEntity<FooDto> {
        TODO("Not yet implemented")
    }

    override fun delete(id: UUID): ResponseEntity<Void> {
        TODO("Not yet implemented")
    }

    override fun disable(id: UUID): ResponseEntity<Void> {
        TODO("Not yet implemented")
    }

    override fun enable(id: UUID): ResponseEntity<Void> {
        TODO("Not yet implemented")
    }
}