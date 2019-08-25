package net.maiatoday.hello8ball

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MyViewModel : ViewModel() {
   // private val repository: QuestionRepository
    private val _answer: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    private val _isLoading: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    val isloading: LiveData<Boolean>
        get() = _isLoading

    val answer: LiveData<String>
        get() = _answer


    fun fetchAnswer(question:String) {
        launchDataLoad {
            _answer.value = QuestionNetworkFake.getAnswer()
        }
    }

    /**
     * Helper function to call a data load function with a loading spinner, errors will trigger a
     * snackbar.
     *
     * By marking `block` as `suspend` this creates a suspend lambda which can call suspend
     * functions.
     *
     * @param block lambda to actually load data. It is called in the viewModelScope. Before calling the
     *              lambda the loading spinner will display, after completion or error the loading
     *              spinner will stop
     */
    private fun launchDataLoad(block: suspend () -> Unit): Job {
        return viewModelScope.launch {
            try {
                _isLoading.value = true
                block()
            } finally {
                _isLoading.value = false
            }
        }
    }
}
