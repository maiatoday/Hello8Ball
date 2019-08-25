package net.maiatoday.hello8ball

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyViewModel : ViewModel() {
   // private val repository: QuestionInterface
    private val answer: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    private val isLoading: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    fun getIsLoading(): LiveData<Boolean> {
        return isLoading
    }

    fun getAnswer(): LiveData<String> {
        return answer
    }

    fun fetchAnswer(question:String) {
        isLoading.value = true
        answer.value = MyRepository.getAnswer(question)
        isLoading.value = false
    }
}
