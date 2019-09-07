package net.maiatoday.hello8ball.question

import com.google.common.truth.Truth.assertWithMessage
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class ParseQuestionTest(val question: String, val questionType: QuestionType) {
    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun data() = listOf(
            arrayOf("password", QuestionType.PASSWORD),
            arrayOf("Password", QuestionType.PASSWORD),
            arrayOf("word", QuestionType.SYNONYM),
            arrayOf("supercalifragilistixexpialidocious", QuestionType.SYNONYM),
            arrayOf("a", QuestionType.SYNONYM),
            arrayOf("how is life with dogs 🐶?", QuestionType.OTHER),
            arrayOf("Is the universe expanding 🌟?", QuestionType.OTHER),
            arrayOf("771", QuestionType.PRIME),
            arrayOf("life the universe and everything", QuestionType.BASIC),
            arrayOf("", QuestionType.OTHER)
        )
    }

    @Test
    fun `parse question should detect question type`() {
        assertWithMessage("Expect $question to parse to ${questionType}")
            .that(parseQuestion(question))
            .isEqualTo(questionType)
    }

}