package net.maiatoday.hello8ball.question

import com.google.common.truth.Truth.assertWithMessage
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class ParseQuestionTest() {
    companion object {
        @JvmStatic
        fun testData():List<Arguments> = listOf(
            Arguments.of("password", QuestionType.PASSWORD),
            Arguments.of("Password", QuestionType.PASSWORD),
            Arguments.of("word", QuestionType.SYNONYM),
            Arguments.of("supercalifragilistixexpialidocious", QuestionType.SYNONYM),
            Arguments.of("a", QuestionType.SYNONYM),
            Arguments.of("how is life with dogs 🐶?", QuestionType.OTHER),
            Arguments.of("Is the universe expanding 🌟?", QuestionType.OTHER),
            Arguments.of("771", QuestionType.PRIME),
            Arguments.of("life the universe and everything", QuestionType.BASIC),
            Arguments.of("", QuestionType.OTHER)
        )
    }

    @ParameterizedTest
    @MethodSource("testData")
    fun `parse question should detect question type`(question:String, questionType:QuestionType) {
        assertWithMessage("Expect $question to parse to ${questionType}")
            .that(parseQuestion(question))
            .isEqualTo(questionType)
    }
}