package net.maiatoday.hello8ball.di

import net.maiatoday.hello8ball.question.*
import net.maiatoday.hello8ball.util.DispatcherProvider
import org.koin.core.qualifier.named
import org.koin.dsl.module

val repositoryModule = module {
    single<QuestionInterface>(named("eightBall")) { QuestionEightBall }
    factory<QuestionInterface>(named("password")) { QuestionPassword() }
    factory<QuestionInterface>(named("synonym")) { QuestionSynonym() }
    single { DispatcherProvider() }
    single { QuestionRepository(
        get(named("eightBall")),
        get(named("password")),
        get(named("synonym")),
        get()) }
}