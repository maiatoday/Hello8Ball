package net.maiatoday.hello8ball.question

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import net.maiatoday.hello8ball.di.uiModule
import net.maiatoday.hello8ball.testutil.TestDispatcherProvider
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
class QuestionRepositoryTest: KoinTest {

    val mockQuestionInterface = Mockito.mock(QuestionInterface::class.java)
    val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()
    val contextProvider = TestDispatcherProvider(testDispatcher)

    val testModule = module {
        single<QuestionInterface>(named("eightBall")) { mockQuestionInterface }
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

    private val subject:QuestionRepository by inject()

    @Test
    fun `should return 42 on life universe question`() = testDispatcher.runBlockingTest {

        val answer = subject.ponder("the meaning of life the universe and everything")

        assertThat(answer).isEqualTo("42")
    }

    @Test
    fun `should detect prime number`() = testDispatcher.runBlockingTest {

        val answer = subject.ponder("773")

        assertThat(answer).isEqualTo("773 is Prime")
    }

    @Test
    fun `should detect non prime number`() = testDispatcher.runBlockingTest {

        val answer = subject.ponder("774")

        assertThat(answer).isEqualTo("774 is not Prime")
    }

    @Test
    fun `should return answer from 🎱`() = testDispatcher.runBlockingTest {
        Mockito.`when`(mockQuestionInterface.getAnswer()).thenReturn("Yes")

        val answer = subject.ponder("Any question")

        assertThat(answer).isEqualTo("Yes")
    }

    @Test
    fun `should return password 🐲`() = testDispatcher.runBlockingTest {
        Mockito.`when`(mockQuestionInterface.getAnswer()).thenReturn("dragon")

        val answer = subject.ponder("password")

        assertThat(answer).isEqualTo("dragon")
    }

    @Test
    fun `should return synonym 👯` () = testDispatcher.runBlockingTest {
        Mockito.`when`(mockQuestionInterface.getAnswer("word")).thenReturn("formulate")

        val answer = subject.ponder("word")

        assertThat(answer).isEqualTo("formulate")
        verify(mockQuestionInterface).getAnswer("word")
    }

    @Test
    fun `🚀 should return answer from 🎱 (no delay)`() = testDispatcher.runBlockingTest {

        val answer = subject.ponder("Any question")

        assertThat(answer).isIn(QuestionEightBall.answers)
    }

}