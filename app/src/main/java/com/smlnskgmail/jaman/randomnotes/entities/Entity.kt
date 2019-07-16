package com.smlnskgmail.jaman.randomnotes.entities

import com.j256.ormlite.field.DatabaseField

abstract class Entity(

    @DatabaseField(generatedId = true)
    var id: Long = -1

)