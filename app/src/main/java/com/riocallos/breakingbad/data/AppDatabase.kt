package com.riocallos.breakingbad.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.riocallos.breakingbad.data.converters.ArrayConverter
import com.riocallos.breakingbad.data.daos.CharacterDao
import com.riocallos.breakingbad.data.models.Character

/**
 * [AppDatabase] The Room database for this app
 */
@Database(
    entities = [Character::class],
    version = 1,
    exportSchema = false)
@TypeConverters(ArrayConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract val characterDao : CharacterDao

}
