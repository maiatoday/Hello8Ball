package net.maiatoday.hello8ball

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class QuestionRepository(val network: QuestionInterface) {

//    val answer: LiveData<String> by lazy<LiveData<String>>(LazyThreadSafetyMode.NONE) {
//
//    }

    suspend fun getAnswer() {
        withContext(Dispatchers.IO) {
            //try {
                val result = network.getAnswer()
//            } catch (error: FakeNetworkException) {
//                throw TitleRefreshError(error)
//            }
        }
    }

}