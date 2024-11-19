package com.global.kaiovalls_rm94685

import androidx.room.Database
import androidx.room.RoomDatabase
import com.global.kaiovalls_rm94685.model.Dica
import com.global.kaiovalls_rm94685.repository.DicaDao

@Database(entities = [Dica::class], version = 1 )
abstract class DicaDatabase : RoomDatabase() {

    abstract fun dicaDao() : DicaDao
}