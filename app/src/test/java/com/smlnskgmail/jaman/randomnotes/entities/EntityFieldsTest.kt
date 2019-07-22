package com.smlnskgmail.jaman.randomnotes.entities

import junit.framework.Assert.assertEquals
import org.junit.Test

class EntityFieldsTest {

    @Test
    fun runTest() {
        val entity = object : Entity() {}

        assertEquals(entity.id, -1)
    }

}