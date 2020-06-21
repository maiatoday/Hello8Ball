package net.maiatoday.hello8ball

import android.app.Application
import net.maiatoday.hello8ball.di.repositoryModule
import net.maiatoday.hello8ball.di.uiModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(repositoryModule, uiModule)
        }
    }

}