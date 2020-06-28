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
    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class EightBallAnswers

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class PasswordAnswers

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class SynonymAnswers

    // There are predefined qualifiers @ApplicationContext and @ActivityContext qualifiers if needed.

    @Singleton
    @EightBallAnswers
    @Provides
    fun provideQuestion8Ball(): QuestionInterface {
        return QuestionEightBall
    }

    @Singleton
    @PasswordAnswers
    @Provides
    fun provideQuestionPassword(): QuestionInterface {
        return QuestionPassword()
    }

    @Singleton
    @SynonymAnswers
    @Provides
    fun provideQuestionSynonym(): QuestionInterface {
        return QuestionSynonym()
    }

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