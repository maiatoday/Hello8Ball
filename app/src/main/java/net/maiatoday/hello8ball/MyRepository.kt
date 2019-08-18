package net.maiatoday.hello8ball

object MyRepository {
    fun setAnswers(stringArray: Array<String>) {
        if (answers.isEmpty()) {
            answers.addAll(stringArray)
        }
    }

    private val answers: MutableList<String> = ArrayList(20)

    fun getAnswer(question:String):String {

        val randomInteger = (0 until answers.size).shuffled().first()
        return answers.get(randomInteger)

    }
}