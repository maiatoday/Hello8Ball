package net.maiatoday.hello8ball

import kotlinx.coroutines.delay

object QuestionNetworkFake : QuestionInterface {

    internal val answers: List<String> = listOf(
        "It is certain.",
        "It is decidedly so.",
        "Without a doubt.",
        "Yes - definitely.",
        "You may rely on it.",
        "As I see it, yes.",
        "Most likely.",
        "Outlook good.",
        "Yes.",
        "Signs point to yes.",
        "Reply hazy, try again.",
        "Ask again later.",
        "Better not tell you now.",
        "Cannot predict now.",
        "Concentrate and ask again.",
        "Don't count on it.",
        "My reply is no.",
        "My sources say no.",
        "Outlook not so good.",
        "Very doubtful."
    )

    override suspend fun getAnswer(): String {
        // simulate a network call here
        val randomMillis = (500 + 3000 * Math.random()).toLong()
        delay(randomMillis)
        return answers.shuffled().first()
    }
}
