package net.maiatoday.hello8ball

object MyRepository {
    const val MAX_ANSWERS = 20
    fun setAnswers(stringArray: Array<String>) {
        if (answers.isEmpty()) {
            answers.addAll(stringArray)
        }
    }

    private val answers: MutableList<String> = ArrayList(MAX_ANSWERS)

    fun getAnswer(question:String):String {

        val randomInteger = (0 until answers.size).shuffled().first()
        return answers.get(randomInteger)

    }
}
