package net.maiatoday.hello8ball.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * Provides the run contexts for the coroutines. Main runs on the UI thread and IO runs on the CommonPool
 */
open class DispatcherProvider {
    /**
     * Main coroutine context running on the UI thread
     */
    @Suppress("VariableNaming")
    open val Main: CoroutineDispatcher by lazy { Dispatchers.Main }

    /**
     * IO coroutine context running on the CommonPool
     */
    @Suppress("VariableNaming")
    open val IO: CoroutineDispatcher by lazy { Dispatchers.IO }

    /**
     * IO coroutine context running on the CommonPool
     */
    @Suppress("VariableNaming")
    open val Default: CoroutineDispatcher by lazy { Dispatchers.Default }
}