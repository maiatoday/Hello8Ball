package net.maiatoday.hello8ball.testutil

import kotlinx.coroutines.CoroutineDispatcher
import net.maiatoday.hello8ball.util.DispatcherProvider
import kotlin.coroutines.CoroutineContext

class TestDispatcherProvider(val testContext: CoroutineDispatcher) :
    DispatcherProvider() {
    override val Main: CoroutineDispatcher
        get() = testContext

    override val IO: CoroutineDispatcher
        get() = testContext

    override val Default: CoroutineDispatcher
        get() = testContext
}