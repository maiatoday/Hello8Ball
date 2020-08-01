package net.maiatoday.hello8ball.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import net.maiatoday.hello8ball.question.QuestionInterface
import net.maiatoday.hello8ball.question.QuestionPassword
import net.maiatoday.hello8ball.question.QuestionSynonym
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class PasswordAnswers

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class SynonymAnswers

@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {
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

}