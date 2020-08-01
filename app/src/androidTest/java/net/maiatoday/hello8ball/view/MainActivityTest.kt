package net.maiatoday.hello8ball.view

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import net.maiatoday.hello8ball.R
import net.maiatoday.hello8ball.di.*
import net.maiatoday.hello8ball.question.QuestionInterface
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Singleton

@UninstallModules(NetworkModule::class, EightBallModule::class)
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class MainActivityTest {

    @Module
    @InstallIn(ApplicationComponent::class)
    object TestModule {
        @Singleton
        @PasswordAnswers
        @Provides
        fun provideQuestionPassword(): QuestionInterface = object : QuestionInterface {
            override suspend fun getAnswer(question: String): String = "dragon123"
        }

        @Singleton
        @SynonymAnswers
        @Provides
        fun provideQuestionSynonym(): QuestionInterface = object : QuestionInterface {
            override suspend fun getAnswer(question: String): String = "synonym"
        }

        @Singleton
        @EightBallAnswers
        @Provides
        fun provideQuestion8Ball(): QuestionInterface = object : QuestionInterface {
            override suspend fun getAnswer(question: String): String = "Concentrate and ask again."
        }

    }

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun mainActivityLaunches() {
        onView(withId(R.id.image8ball))
            .check(matches(isDisplayed()))
    }

    @Test
    fun askPassword() {

        onView(withId(R.id.question))
            .perform(replaceText("password"), closeSoftKeyboard())

        onView(withId(R.id.fab))
            .perform((click()))

        onView(withId(R.id.answer))
            .check(matches(withText("dragon123")))
    }

    @Test
    fun askSynonym() {

        onView(withId(R.id.question))
            .perform(replaceText("word"), closeSoftKeyboard())

        onView(withId(R.id.fab))
            .perform((click()))

        onView(withId(R.id.answer))
            .check(matches(withText("synonym")))

    }

    @Test
    fun askIsNotPrime() {

        onView(withId(R.id.question))
            .perform(replaceText("12"), closeSoftKeyboard())

        onView(withId(R.id.fab))
            .perform((click()))

        onView(withId(R.id.answer))
            .check(matches(withText("12 is not Prime")))

    }

    @Test
    fun askIsPrime() {

        onView(withId(R.id.question))
            .perform(replaceText("173"), closeSoftKeyboard())

        onView(withId(R.id.fab))
            .perform((click()))

        onView(withId(R.id.answer))
            .check(matches(withText("173 is Prime")))

    }

    @Test
    fun askMeaning() {

        onView(withId(R.id.question))
            .perform(
                replaceText("the meaning of life, the universe and everything"),
                closeSoftKeyboard()
            )

        onView(withId(R.id.fab))
            .perform((click()))

        onView(withId(R.id.answer))
            .check(matches(withText("42")))

    }

    @Test
    fun askEightBall() {

        onView(withId(R.id.question))
            .perform(
                replaceText("why oh why"),
                closeSoftKeyboard()
            )

        onView(withId(R.id.fab))
            .perform((click()))

        onView(withId(R.id.answer))
            .check(matches(withText("Concentrate and ask again.")))

    }
}
