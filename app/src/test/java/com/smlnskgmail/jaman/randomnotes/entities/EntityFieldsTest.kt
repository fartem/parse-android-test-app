package com.smlnskgmail.jaman.randomnotes.entities

import com.smlnskgmail.jaman.randomnotes.db.entities.EntityWitId
import junit.framework.Assert.assertEquals
import org.junit.Test

class EntityFieldsTest {

    @Test
    fun runTest() {
        val entity = object : EntityWitId() {}

        assertEquals(entity.id, -1)
    }

}