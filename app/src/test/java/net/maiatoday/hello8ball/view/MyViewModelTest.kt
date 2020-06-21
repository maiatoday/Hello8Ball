package net.maiatoday.hello8ball.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import net.maiatoday.hello8ball.di.uiModule
import net.maiatoday.hello8ball.question.QuestionEightBall
import net.maiatoday.hello8ball.question.QuestionInterface
import net.maiatoday.hello8ball.question.QuestionRepository
import net.maiatoday.hello8ball.testutil.CoroutinesTestRule
import net.maiatoday.hello8ball.testutil.SlowFakeAnswer
import net.maiatoday.hello8ball.testutil.TestDispatcherProvider
import net.maiatoday.hello8ball.testutil.getValueForTest
import org.junit.Rule
import org.junit.Test
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject
import org.mockito.Mockito
import org.mockito.Mockito.verify

@ExperimentalCoroutinesApi
class MyViewModelTest: KoinTest {

    val mockQuestionInterface = Mockito.mock(QuestionInterface::class.java)
    val testModule = module {
        single<QuestionInterface>(named("eightBall")) { SlowFakeAnswer(5000) }
        factory<QuestionInterface>(named("password")) { mockQuestionInterface }
        factory<QuestionInterface>(named("synonym")) { mockQuestionInterface }
        single { contextProvider }
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

    private val repository:QuestionRepository by inject()

    // Set the main coroutines dispatcher for unit testing.
    // We are setting the above-defined testDispatcher as the Main thread dispatcher.
    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    val testDispatcher = coroutinesTestRule.testDispatcher
    val contextProvider = TestDispatcherProvider(testDispatcher)

    @Test
    fun `loading is false in the beginning`() =
        testDispatcher.runBlockingTest {

        val subject = MyViewModel(repository)

        assertThat(subject.isloading.getValueForTest()).isFalse()
    }

    @Test
    fun `asking a question sets is loading ⚡️🕥`() =
        testDispatcher.runBlockingTest {
            pauseDispatcher {

                // setup subject
                val subject = MyViewModel(repository)
                subject.fetchAnswer("hello world")

                // control time and test
                assertThat(subject.isloading.getValueForTest()).isFalse()
                advanceTimeBy(1)
                assertThat(subject.isloading.getValueForTest()).isTrue()
                advanceTimeBy(4998)
                assertThat(subject.isloading.getValueForTest()).isTrue()
                advanceTimeBy(1)
                assertThat(subject.isloading.getValueForTest()).isFalse()
            }
        }

    @Test
    fun `asking a question returns an answer`() =
        testDispatcher.runBlockingTest {

            val subject = MyViewModel(repository)

            subject.fetchAnswer("hello world")
            advanceTimeBy(1)

            assertThat(subject.answer.getValueForTest()).isEqualTo("yes")
        }


    @Test
    fun `return an answer stops loading`() = testDispatcher.runBlockingTest {
        val mockQuestionInterface = Mockito.mock(QuestionInterface::class.java)
        Mockito.`when`(mockQuestionInterface.getAnswer()).thenReturn("Yes")
        val repository = QuestionRepository(
            mockQuestionInterface,
            mockQuestionInterface,
            mockQuestionInterface,
            contextProvider
        )
        val subject = MyViewModel(repository)

        subject.fetchAnswer("hello world")

        assertThat(subject.isloading.getValueForTest()).isFalse()
    }

    @Test
    fun `🚀 asking a real question returns an answer (no delay)`() =
        testDispatcher.runBlockingTest {
            pauseDispatcher {

                val mockQuestionInterface = Mockito.mock(QuestionInterface::class.java)
                val repository = QuestionRepository(
                    QuestionEightBall,
                    mockQuestionInterface,
                    mockQuestionInterface,
                    contextProvider
                )
                val subject = MyViewModel(repository)

                subject.fetchAnswer("hello world")
                advanceTimeBy(5000)
                assertThat(subject.answer.getValueForTest()).isIn(QuestionEightBall.answers)
            }
        }

    @Test
    fun `clicking onCopy calls the copy handler with the answer`()  =
        testDispatcher.runBlockingTest {
            pauseDispatcher {
                val mockCopyHandler = Mockito.mock(CopyHandler::class.java)

                val subject = MyViewModel(repository)
                subject.copyHandler = mockCopyHandler

                subject.fetchAnswer("hello world")
                advanceTimeBy(1)

                subject.onCopy()

                verify(mockCopyHandler).copy("copycopy")
            }
        }

    @Test
    fun `clicking onCopy with no copy handler does nothing`()  =
        testDispatcher.runBlockingTest {
            pauseDispatcher {

                val subject = MyViewModel(repository)

                subject.fetchAnswer("hello world")
                advanceTimeBy(1)

                subject.onCopy()
            }
        }

}