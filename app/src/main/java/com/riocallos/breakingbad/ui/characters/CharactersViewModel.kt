package com.riocallos.breakingbad.ui.characters

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.riocallos.breakingbad.data.models.Character
import com.riocallos.breakingbad.repository.CharactersRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CharactersViewModel(
    private val repository: CharactersRepository
) : ViewModel() {

    val characters = MutableLiveData<Array<Character>>()
    val filteredCharacters = MutableLiveData<Array<Character>>()
    var seasonsFilter: MutableSet<Int> = HashSet(listOf(1, 2, 3, 4, 5))

    fun getCharacters(refresh: Boolean) {
        CoroutineScope(Dispatchers.IO).launch {
            if(refresh || characters.value.isNullOrEmpty()) {
                seasonsFilter = HashSet(listOf(1, 2, 3, 4, 5))
                characters.postValue(repository.get())
            }
        }
    }

    fun getSelectedSeasons(): BooleanArray {
        return arrayOf(
            seasonsFilter.contains(1),
            seasonsFilter.contains(2),
            seasonsFilter.contains(3),
            seasonsFilter.contains(4),
            seasonsFilter.contains(5)).toBooleanArray()
    }


    fun filterCharacters(toFilter: Array<Character>? = characters.value) {
        val filtered = mutableListOf<Character>()
        toFilter?.let {
            for (character in it) {
                if(seasonsFilter.intersect(character.appearance.toSet()).isNotEmpty()) {
                    filtered.add(character)
                }
            }
        }
        filteredCharacters.value = filtered.toTypedArray()
    }

    fun searchCharacters(query: String) {
        if(query.isNotEmpty()) {
            val filtered = mutableListOf<Character>()
            characters.value?.let {
                for (character in it) {
                    if (character.name.contains(query, ignoreCase = true)) {
                        filtered.add(character)
                    }
                }
            }
            filterCharacters(filtered.toTypedArray())
        } else {
            filterCharacters()
        }
    }

}