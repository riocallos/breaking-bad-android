package com.riocallos.breakingbad

import com.riocallos.breakingbad.utils.CoroutineContextProvider
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext


class TestCoroutineContextProvider : CoroutineContextProvider() {
    override val IO: CoroutineContext
        get() = Dispatchers.Unconfined
    override val Main: CoroutineContext
        get() = Dispatchers.Unconfined
}