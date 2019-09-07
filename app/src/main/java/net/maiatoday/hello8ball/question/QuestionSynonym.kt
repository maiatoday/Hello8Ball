package net.maiatoday.hello8ball.question

import net.maiatoday.hello8ball.api.synonym.SynonymService
import retrofit2.HttpException

class QuestionSynonym(val service: SynonymService = SynonymService.instance) :
    QuestionInterface {
    override suspend fun getAnswer(question: String): String {
        return try {
            val response = service.getSynonymAsync(question).await()
            val synonyms = response.synonyms
            synonyms.shuffled().first()
        } catch (e: HttpException) {
            "no synonym for $question"
        }
    }
}
