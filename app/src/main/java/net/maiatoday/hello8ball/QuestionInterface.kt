package net.maiatoday.hello8ball

interface QuestionInterface {
    suspend fun getAnswer(): String
}
