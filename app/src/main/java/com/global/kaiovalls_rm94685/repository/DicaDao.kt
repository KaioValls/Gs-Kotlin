package com.global.kaiovalls_rm94685.repository

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.global.kaiovalls_rm94685.model.Dica

@Dao
interface DicaDao {

    @Query("SELECT * FROM Dica")
    fun getAll(): LiveData<List<Dica>>

    @Insert
    fun insert(dica: Dica)

    @Delete
    fun delete(dica: Dica)

    @Query("SELECT * FROM Dica where title like :searchQuery")
    fun findDicaByTitle(searchQuery: String): LiveData<List<Dica>>

}