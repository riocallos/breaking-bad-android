package com.riocallos.breakingbad.di

import com.riocallos.breakingbad.repository.CharactersRepository
import com.riocallos.breakingbad.ui.characters.CharactersViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * View model module for containing repositories and view models
 */
val viewModelModule = module {

    single { CharactersRepository(get(), get()) }

    viewModel { CharactersViewModel(get()) }

}