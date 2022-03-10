package com.riocallos.breakingbad.di

import android.content.Context
import androidx.room.Room
import com.riocallos.breakingbad.data.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 * Database module for creating instances of database and daos
 */
val databaseModule = module {
    single { createDatabase(androidContext()) }
    single { createCharactersDao(get()) }
}

/**
 * Create database instance from context
 * @param appContext as [Context] needed to initialize room database
 * @return as [AppDatabase] initialized room database
 */
fun createDatabase(appContext: Context) =
    Room.databaseBuilder(appContext, AppDatabase::class.java, "com-riocallos-breakingbad-db")
        .build()

fun createCharactersDao(db: AppDatabase) = db.characterDao
