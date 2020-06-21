package net.maiatoday.hello8ball.question

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import net.maiatoday.hello8ball.di.repositoryModule
import net.maiatoday.hello8ball.di.uiModule
import net.maiatoday.hello8ball.testutil.CoroutinesTestRule
import net.maiatoday.hello8ball.testutil.getValueForTest
import net.maiatoday.hello8ball.util.DispatcherProvider
import net.maiatoday.hello8ball.view.MyViewModel
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject
import org.mockito.Mockito

@ExperimentalCoroutinesApi
@Ignore("flakey tests for demo")
class FlakeyTests: KoinTest {

    private val testModule = module {
        single<QuestionInterface>(named("eightBall")) { Mockito.mock(QuestionInterface::class.java) }
        factory<QuestionInterface>(named("password")) { Mockito.mock(QuestionInterface::class.java) }
        factory<QuestionInterface>(named("synonym")) { Mockito.mock(QuestionInterface::class.java) }
        single { DispatcherProvider() }
        single { QuestionRepository(
            get(named("eightBall")),
            get(named("password")),
            get(named("synonym")),
            get()) }
    }
    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(testModule, uiModule)
    }

    // Set the main coroutines dispatcher for unit testing.
    // We are setting the above-defined testDispatcher as the Main thread dispatcher.
    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val repository:QuestionRepository by inject()

    @Test
    fun `asking a question sets is loading`() = runBlocking {
        val subject = MyViewModel(repository)

        subject.fetchAnswer("hello world")
        delay(1000) // … the test might fail ¯\_(ツ)_/¯

        assertThat(subject.isloading.getValueForTest()).isTrue()
    }

    @Test
    fun `return an answer stops loading`() = runBlocking {
        val mockQuestionInterface = Mockito.mock(QuestionInterface::class.java)
        Mockito.`when`(mockQuestionInterface.getAnswer()).thenReturn("Yes")
        val subject = MyViewModel(repository)

        subject.fetchAnswer("hello world")
        //  delay(1000)

        assertThat(subject.isloading.getValueForTest()).isFalse()
    }

    @Test
    fun `asking a question sets is loading (flakey)`() {
        val subject = MyViewModel(repository)

        subject.fetchAnswer("hello world")

        assertThat(subject.isloading.getValueForTest()).isTrue()

    }
}