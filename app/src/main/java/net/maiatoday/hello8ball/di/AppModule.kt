package net.maiatoday.hello8ball.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import net.maiatoday.hello8ball.question.*
import net.maiatoday.hello8ball.util.DispatcherProvider
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    // There are predefined qualifiers @ApplicationContext and @ActivityContext qualifiers if needed.

    @Singleton
    @Provides
    fun provideQuestionRepository(
        @EightBallAnswers eightBall: QuestionInterface,
        @PasswordAnswers password: QuestionInterface,
        @SynonymAnswers synonym: QuestionInterface,
        provider: DispatcherProvider
    ): QuestionRepository {
        return QuestionRepository(eightBall, password, synonym, provider)
    }
}