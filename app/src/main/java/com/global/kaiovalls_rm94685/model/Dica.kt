package com.global.kaiovalls_rm94685.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Dica (

    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,

    val title: String,
    val descricao: String
)