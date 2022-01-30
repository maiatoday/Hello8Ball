package net.maiatoday.hello8ball.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.maiatoday.hello8ball.question.QuestionEightBall
import net.maiatoday.hello8ball.question.QuestionInterface
import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class EightBallAnswers

@Module
@InstallIn(SingletonComponent::class)
internal object EightBallModule {
    @EightBallAnswers
    @Provides
    fun provideQuestion8Ball(): QuestionInterface =
        QuestionEightBall

}