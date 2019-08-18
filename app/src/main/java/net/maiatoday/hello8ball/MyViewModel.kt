package net.maiatoday.hello8ball

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyViewModel : ViewModel() {
    private val answer: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    fun getAnswer(): LiveData<String> {
        return answer
    }

    fun fetchAnswer(question:String) {
        answer.value = MyRepository.getAnswer(question)
    }
}
