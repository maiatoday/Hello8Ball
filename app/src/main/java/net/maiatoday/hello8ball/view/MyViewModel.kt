package net.maiatoday.hello8ball.view

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import net.maiatoday.hello8ball.question.QuestionRepository
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(private val repository: QuestionRepository) : ViewModel() {

    /**
     * Answer from the repository
     */
    val answer = repository.answer.asLiveData()
    var copyHandler: CopyHandler? = null

    private val _isLoading: MutableLiveData<Boolean> by lazy {
        val liveData = MutableLiveData<Boolean>()
        liveData.value = false
        return@lazy liveData
    }

    val isloading: LiveData<Boolean>
        get() = _isLoading

    /**
     * Ask the repository for the answer
     */
    fun fetchAnswer(question: String) {
        launchDataLoad {
            repository.getAnswer(question)
        }
    }

    fun onCopy() {
        answer.value?.let {
            copyHandler?.copy(it)
        }
    }

    /**
     * Helper function to call a data load function with a loading spinner
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

interface CopyHandler {
    fun copy(item: String)
}

