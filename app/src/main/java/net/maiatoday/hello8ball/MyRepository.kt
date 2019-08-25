package net.maiatoday.hello8ball

object MyRepository : QuestionInterface {
    const val MAX_ANSWERS = 20
    fun setAnswers(stringArray: Array<String>) {
        if (answers.isEmpty()) {
            answers.addAll(stringArray)
        }
    }

    private val answers: MutableList<String> = ArrayList(MAX_ANSWERS)

    override fun getAnswer(question:String):String {
        // simulate a network call here
        val randomInteger = (0 until answers.size).shuffled().first()
        return answers.get(randomInteger)

    }
}
