package com.smlnskgmail.jaman.randomnotes.entities

import com.j256.ormlite.field.DatabaseField

class Note(

    @DatabaseField
    var title: String? = null,

    @DatabaseField
    var subtitle: String? = null

) : Entity()