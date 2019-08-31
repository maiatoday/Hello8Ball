package net.maiatoday.hello8ball

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.maiatoday.hello8ball.util.isPrime

/**
 * The QuestionRepository answers questions as it sees fit. It provides the answer in the answer
 * live data.
 */
class QuestionRepository(
    val network: QuestionInterface,
    val dispatcherIO: CoroutineDispatcher = Dispatchers.IO,
    val dispatcherDefault: CoroutineDispatcher = Dispatchers.Default
) {
    private val _answer: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val answer: LiveData<String>
        get() = _answer

    suspend fun getAnswer(question: String) {
        _answer.value = ponder(question)
    }

    suspend fun ponder(question: String): String {
        var newAnswer = ""
        val possibleNumber: Int? = question.toIntOrNull()
        if (question.contains("life") &&
            question.contains("universe")
        ) {
            newAnswer = "42"
        } else if (possibleNumber != null) {
            withContext(dispatcherIO) {
                val primeAnswer = if (isPrime(possibleNumber)) "" else " not"
                newAnswer = "$possibleNumber is$primeAnswer Prime"
            }
        } else {
            withContext(dispatcherDefault) {
                newAnswer = network.getAnswer()
            }
        }
        return newAnswer
    }
}
