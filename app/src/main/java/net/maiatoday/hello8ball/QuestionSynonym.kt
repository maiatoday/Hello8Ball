package net.maiatoday.hello8ball

import net.maiatoday.hello8ball.api.synonym.SynonymService

class QuestionSynonym:QuestionInterface {

    override suspend fun getAnswer(question: String): String {
        val response = SynonymService.instance.getSynonymAsync(question).await()
        val synonyms = response.synonyms
        return synonyms.shuffled().first()
    }

}