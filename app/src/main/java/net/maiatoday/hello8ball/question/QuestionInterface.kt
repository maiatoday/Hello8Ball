package net.maiatoday.hello8ball.question

interface QuestionInterface {
    suspend fun getAnswer(question: String = ""): String
}
