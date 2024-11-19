package com.global.kaiovalls_rm94685.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.global.kaiovalls_rm94685.DicaDatabase
import com.global.kaiovalls_rm94685.model.Dica
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DicaRepository(context: Context) {

    private val dicaDao: DicaDao = Room.databaseBuilder(
        context,
        DicaDatabase::class.java,
        "dica_database"
    ).build().dicaDao()

    fun getAllDicas(): LiveData<List<Dica>> {
        return dicaDao.getAll()
    }

    suspend fun addDica(dica: Dica) {
        withContext(Dispatchers.IO) {
            dicaDao.insert(dica)
        }
    }

    suspend fun removeDica(dica:Dica){
        withContext(Dispatchers.IO){
            dicaDao.delete(dica)
        }
    }

    fun searchDica(searchQuery: String): LiveData<List<Dica>> {
        val formattedQuery = "%$searchQuery%"
        return dicaDao.findDicaByTitle(formattedQuery);
    }

}
