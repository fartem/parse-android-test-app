package com.smlnskgmail.jaman.randomnotes.entities

import com.smlnskgmail.jaman.randomnotes.model.api.entities.EntityWithId
import org.junit.Assert.assertEquals

class EntityWithIdTest : BaseEntityTest() {

    private val entityWithId = object : EntityWithId() {}

    override fun `Validate fields`() {
        assertEquals(
            -1,
            entityWithId.id
        )

        val id = 1L
        entityWithId.id = id
        assertEquals(
            id,
            entityWithId.id
        )
    }

    override fun `Validate equals()`() {
        assertEquals(
            entityWithId,
            entityWithId
        )
    }

    override fun `Validate hashCode()`() {
        assertEquals(
            entityWithId.hashCode(),
            entityWithId.hashCode()
        )
    }

}
