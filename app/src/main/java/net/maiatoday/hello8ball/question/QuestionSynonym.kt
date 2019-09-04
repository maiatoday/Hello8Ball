package net.maiatoday.hello8ball.question

import net.maiatoday.hello8ball.api.synonym.SynonymService

class QuestionSynonym(val service: SynonymService = SynonymService.instance) :
    QuestionInterface {
    override suspend fun getAnswer(question: String): String {
        val response = service.getSynonymAsync(question).await()
        val synonyms = response.synonyms
        return synonyms.shuffled().first()
    }
}