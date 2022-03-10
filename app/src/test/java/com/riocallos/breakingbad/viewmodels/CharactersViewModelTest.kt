package com.riocallos.breakingbad.viewmodels

import android.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.riocallos.breakingbad.BaseUnitTest
import com.riocallos.breakingbad.data.models.Character
import com.riocallos.breakingbad.repository.CharactersRepository
import com.riocallos.breakingbad.ui.characters.CharactersViewModel
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import org.koin.core.component.inject


@ExperimentalCoroutinesApi
class CharactersViewModelTest: BaseUnitTest()  {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @InjectMockKs
    lateinit var viewModel: CharactersViewModel

    private val charactersRepository by inject<CharactersRepository>()

    @MockK
    lateinit var characters:  MutableLiveData<Array<Character>>

    @Test
    fun `get characters called`() {
        viewModel = spyk(viewModel)
        every { viewModel.characters } returns characters

        viewModel.getCharacters(true)

        coVerify(exactly = 1) { charactersRepository.get() }
        verify(exactly = 1) { characters.postValue(any()) }
    }

}