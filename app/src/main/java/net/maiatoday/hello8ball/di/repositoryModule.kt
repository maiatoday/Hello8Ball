package net.maiatoday.hello8ball.di

import net.maiatoday.hello8ball.QuestionInterface
import net.maiatoday.hello8ball.QuestionNetworkFake
import net.maiatoday.hello8ball.QuestionRepository
import org.koin.dsl.module

val repositoryModule = module {

    single<QuestionInterface> { QuestionNetworkFake }

    single { QuestionRepository(get()) }

}