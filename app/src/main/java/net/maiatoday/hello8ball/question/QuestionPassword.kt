package net.maiatoday.hello8ball.question

import net.maiatoday.hello8ball.api.password.PasswordService
import net.maiatoday.hello8ball.question.QuestionInterface
import retrofit2.HttpException

class QuestionPassword(private val service: PasswordService = PasswordService.instance) :
    QuestionInterface {
    override suspend fun getAnswer(question: String): String {
        return try {
            val response = service.getPasswordAsync().await()
            val passwords = response.char
            if (passwords.isNotEmpty()) {
                passwords[0]
            } else {
                "Oops no password"
            }
        } catch (e: HttpException) {
            "Oops no password"
        }
    }
}
