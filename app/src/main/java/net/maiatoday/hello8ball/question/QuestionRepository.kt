package net.maiatoday.hello8ball.question

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import net.maiatoday.hello8ball.util.DispatcherProvider
import net.maiatoday.hello8ball.util.isPrime
import java.util.*
import javax.inject.Inject

/**
 * The QuestionRepository answers questions as it sees fit. It provides the answer in the answer
 * live data.
 */
class QuestionRepository @Inject constructor(
    val eightBall: QuestionInterface,
    val password: QuestionInterface,
    val synonym: QuestionInterface,
    val contextProvider: DispatcherProvider
) {
    private val _answer = MutableStateFlow("")
    val answer: Flow<String> = _answer.asStateFlow()

    suspend fun getAnswer(question: String) {
        _answer.value = ponder(question)
    }

    suspend fun ponder(question: String): String {
        var newAnswer = ""
        when (parseQuestion(question)) {
            QuestionType.BASIC -> newAnswer = "42"
            QuestionType.OTHER -> withContext(contextProvider.Default) {
                newAnswer = eightBall.getAnswer()
            }
            QuestionType.SYNONYM -> withContext(contextProvider.IO) {
                newAnswer = synonym.getAnswer(question)
            }
            QuestionType.PASSWORD -> withContext(contextProvider.IO) {
                newAnswer = password.getAnswer()
            }
            QuestionType.PRIME -> {
                val number = question.toInt()
                withContext(contextProvider.Default) {
                    val primeAnswer = if (isPrime(number)) "" else " not"
                    newAnswer = "$number is$primeAnswer Prime"
                }
            }
        }
        return newAnswer
    }
}

fun parseQuestion(question: String): QuestionType =
    when {
        (question.toIntOrNull() != null) -> QuestionType.PRIME
        (question.lowercase(Locale.getDefault()).contains("password")) -> QuestionType.PASSWORD
        (question.isNotEmpty() && !question.contains(" ")) -> QuestionType.SYNONYM
        (question.contains("life") && question.contains("universe")) -> QuestionType.BASIC
        else -> QuestionType.OTHER
    }

enum class QuestionType {
    BASIC, PRIME, SYNONYM, PASSWORD, OTHER
}
