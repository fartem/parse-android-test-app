package com.smlnskgmail.jaman.randomnotes.entities

import com.smlnskgmail.jaman.randomnotes.model.api.entities.EntityWithId
import org.junit.Assert.assertEquals
import org.junit.Test

class EntityWithIdTest {
    
    @Test
    fun validateFields() {
        var entityWithId: EntityWithId = object : EntityWithId() {}
        assertEquals(
            -1,
            entityWithId.id
        )

        entityWithId.id = 1
        assertEquals(
            1,
            entityWithId.id
        )

        entityWithId = object : EntityWithId(1) {}
        assertEquals(
            1,
            entityWithId.id
        )
    }
    
}
