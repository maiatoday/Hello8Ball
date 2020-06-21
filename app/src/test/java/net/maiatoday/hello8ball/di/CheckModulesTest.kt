package net.maiatoday.hello8ball.di

import org.junit.Test
import org.junit.experimental.categories.Category
import org.koin.test.KoinTest
import org.koin.test.category.CheckModuleTest
import org.koin.test.check.checkModules

@Category(CheckModuleTest::class)
class ModuleCheckTest : KoinTest {

    @Test
    fun checkModules() = checkModules {
        modules(repositoryModule, uiModule)
    }
}