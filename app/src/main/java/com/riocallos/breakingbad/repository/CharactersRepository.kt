package com.riocallos.breakingbad.repository

import com.riocallos.breakingbad.data.AppDatabase
import com.riocallos.breakingbad.data.models.Character
import com.riocallos.breakingbad.services.CharactersService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CharactersRepository(
    private val appDatabase: AppDatabase,
    private val service: CharactersService
) {

    suspend fun get(): Array<Character>? {
        service.get().also {
            return if(it.isSuccessful) {
                val characters = it.body()
                characters?.let {
                    CoroutineScope(Dispatchers.IO).launch {
                        appDatabase.characterDao.insertAll(*characters)
                    }
                }
                characters
            }
            else emptyArray()
        }
    }

}