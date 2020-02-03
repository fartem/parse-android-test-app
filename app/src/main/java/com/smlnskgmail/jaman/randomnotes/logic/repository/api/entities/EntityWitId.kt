package com.smlnskgmail.jaman.randomnotes.logic.repository.api.entities

import com.j256.ormlite.field.DatabaseField

abstract class EntityWitId(

    @DatabaseField(generatedId = true)
    var id: Long = -1

)