package net.maiatoday.hello8ball.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import net.maiatoday.hello8ball.question.QuestionEightBall
import net.maiatoday.hello8ball.question.QuestionInterface
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class EightBallAnswers

@Module
@InstallIn(ApplicationComponent::class)
object EightBallModule {
    @Singleton
    @EightBallAnswers
    @Provides
    fun provideQuestion8Ball(): QuestionInterface {
        return QuestionEightBall
    }

}