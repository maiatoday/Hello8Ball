package net.maiatoday.hello8ball

import net.maiatoday.hello8ball.api.password.PasswordService

class QuestionPassword : QuestionInterface {
    override suspend fun getAnswer(question: String): String {
        val response = PasswordService.instance.getPasswordAsync().await()
        val passwords = response.char
        return passwords[0]
    }
}