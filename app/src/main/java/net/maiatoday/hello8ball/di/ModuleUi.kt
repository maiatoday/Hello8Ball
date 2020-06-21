package net.maiatoday.hello8ball.di

import net.maiatoday.hello8ball.view.MyViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val uiModule = module {

    viewModel { MyViewModel(get()) }

}