package com.smlnskgmail.jaman.randomnotes.entities

import com.smlnskgmail.jaman.randomnotes.entities.Entity
import org.junit.Assert
import org.junit.Test

class EntityFieldsTest {

    @Test
    fun runTest() {
        val entity = object : Entity() {}

        Assert.assertEquals(entity.id, -1)
    }

}