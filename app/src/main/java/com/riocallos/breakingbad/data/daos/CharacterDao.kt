package com.riocallos.breakingbad.data.daos

import androidx.room.*
import com.riocallos.breakingbad.data.models.Character

@Dao
interface CharacterDao {

    @Query("SELECT * FROM characters")
    fun getAll(): Array<Character>

    @Query("SELECT * FROM characters WHERE char_id IN (:ids)")
    fun findAllByIds(ids: Array<String>): Array<Character>

    @Query("SELECT * FROM characters WHERE char_id = :id LIMIT 1")
    fun findById(id: String): Character

    @Query("SELECT EXISTS(SELECT * FROM characters WHERE char_id = :id)")
    fun has(id: String): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg characters: Character)

    @Query("DELETE FROM characters")
    fun deleteAll()

    @Delete
    fun delete(character: Character)

}