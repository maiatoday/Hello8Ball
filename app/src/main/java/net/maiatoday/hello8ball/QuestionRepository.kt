package net.maiatoday.hello8ball

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * The QuestionRepository answers questions as it sees fit. It provides the answer in the answer
 * live data.
 */
class QuestionRepository(val network: QuestionInterface) {
    private val _answer: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val answer: LiveData<String>
        get() = _answer

    suspend fun getAnswer(question: String) {
        if (question.contains("life") &&
            question.contains("universe")) {
            _answer.value = "42"
            return
        }
        var newAnswer = ""
        withContext(Dispatchers.IO) {
            newAnswer = network.getAnswer()
        }
        _answer.value = newAnswer
    }
}
