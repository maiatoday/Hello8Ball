package net.maiatoday.hello8ball.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.maiatoday.hello8ball.question.*
import net.maiatoday.hello8ball.util.DispatcherProvider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object AppModule {

    // There are predefined qualifiers @ApplicationContext and @ActivityContext qualifiers if needed.

    @Singleton
    @Provides
    fun provideQuestionRepository(
        @EightBallAnswers eightBall: QuestionInterface,
        @PasswordAnswers password: QuestionInterface,
        @SynonymAnswers synonym: QuestionInterface,
        provider: DispatcherProvider
    ): QuestionRepository =
        QuestionRepository(eightBall, password, synonym, provider)

}