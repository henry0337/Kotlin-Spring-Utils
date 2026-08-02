package dev.myrlennia237.sample

import dev.myrlennia237.component.dto.PagedResponse
import dev.myrlennia237.template.service.AbstractCrudService
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.Optional
import java.util.UUID

internal data class FooDto(val fooName: String)
internal data class FooCreateDto(val fooName: String)
internal data class FooUpdateDto(val fooName: String)

@Service
internal class FooService : AbstractCrudService<FooDto, FooCreateDto, FooUpdateDto>() {
    override fun findById(id: UUID): Optional<FooDto> {
        TODO("Not yet implemented")
    }

    override fun findAll(pageable: Pageable): PagedResponse<FooDto> {
        TODO("Not yet implemented")
    }

    override fun insert(item: FooCreateDto): FooDto {
        TODO("Not yet implemented")
    }

    override fun update(
        id: UUID,
        body: FooUpdateDto
    ): FooDto {
        TODO("Not yet implemented")
    }

    override fun deleteById(id: UUID) {
        TODO("Not yet implemented")
    }

    override fun disable(id: UUID) {
        TODO("Not yet implemented")
    }

    override fun enable(id: UUID) {
        TODO("Not yet implemented")
    }
}