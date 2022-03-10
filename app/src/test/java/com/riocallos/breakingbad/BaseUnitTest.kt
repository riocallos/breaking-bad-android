package com.riocallos.breakingbad

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.riocallos.breakingbad.di.appModule
import com.riocallos.breakingbad.di.databaseModule
import com.riocallos.breakingbad.di.networkModule
import com.riocallos.breakingbad.di.viewModelModule
import com.riocallos.breakingbad.repository.CharactersRepository
import com.riocallos.breakingbad.services.CharactersService
import com.riocallos.breakingbad.utils.CoroutineContextProvider
import io.mockk.MockKAnnotations
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule


@RunWith(JUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class)
@Ignore("Base unit testing class")
open class BaseUnitTest : KoinTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testDispatcher = CoroutinesTestRule()

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        val testModule = module {
            single<CoroutineContextProvider> { TestCoroutineContextProvider() }

            single { mockk<CharactersService>(relaxed = true) }

            single { mockk<CharactersRepository>(relaxed = true) }

        }
        modules(listOf(appModule, databaseModule, networkModule, viewModelModule, testModule))
    }

    @Before
    open fun setup() {
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {
    }

}