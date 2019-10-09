package net.maiatoday.hello8ball.view

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import net.maiatoday.hello8ball.question.QuestionEightBall
import net.maiatoday.hello8ball.question.QuestionInterface
import net.maiatoday.hello8ball.question.QuestionRepository
import net.maiatoday.hello8ball.testutil.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.mockito.Mockito.verify

@ExperimentalCoroutinesApi
@ExtendWith(InstantExecutorExtension::class)
class MyViewModelTest {

    val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()
    val contextProvider = TestDispatcherProvider(testDispatcher)

    @BeforeEach
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `loading is false in the beginning`() =
        testDispatcher.runBlockingTest {
        val mockQuestionInterface = Mockito.mock(QuestionInterface::class.java)
        val repository = QuestionRepository(
            eightBall = mockQuestionInterface,
            contextProvider = contextProvider)
        val subject = MyViewModel(repository)

        assertThat(subject.isloading.getValueForTest()).isFalse()
    }

    @Test
    fun `asking a question sets is loading ⚡️🕥`() =
        testDispatcher.runBlockingTest {
            pauseDispatcher {

                // setup fake that responds slowly
                val fakeInterface: QuestionInterface = SlowFakeAnswer(5000)
                val repository = QuestionRepository(
                    eightBall = fakeInterface,
                    contextProvider = contextProvider
                )

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
            val fakeInterface: QuestionInterface = SlowFakeAnswer(1, "yes")
            val repository = QuestionRepository(
                eightBall = fakeInterface,
                contextProvider = contextProvider
            )
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
                val fakeInterface: QuestionInterface = SlowFakeAnswer(1, "copycopy")
                val repository = QuestionRepository(
                    eightBall = fakeInterface,
                    contextProvider = contextProvider
                )
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
                val fakeInterface: QuestionInterface = SlowFakeAnswer(1, "copycopy")
                val repository = QuestionRepository(
                    eightBall = fakeInterface,
                    contextProvider = contextProvider
                )
                val subject = MyViewModel(repository)

                subject.fetchAnswer("hello world")
                advanceTimeBy(1)

                subject.onCopy()
            }
        }

}