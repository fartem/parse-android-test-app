package com.smlnskgmail.jaman.randomnotes.model.api.entities

import com.j256.ormlite.field.DatabaseField

abstract class EntityWithId(

    @DatabaseField(generatedId = true)
    var id: Long = -1

)
