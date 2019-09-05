package com.smlnskgmail.jaman.randomnotes.db.entities

import com.j256.ormlite.field.DatabaseField

abstract class EntityWitId(

    @DatabaseField(generatedId = true)
    var id: Long = -1

) {

    fun isNew() = id == -1L

}