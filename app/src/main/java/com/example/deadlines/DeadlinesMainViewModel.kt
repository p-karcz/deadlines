package com.example.deadlines

import androidx.lifecycle.*

enum class CurrFragment {
    ALL_TASKS,
    TASK_CREATION
}

class DeadlinesMainViewModel: ViewModel() {

    private var _navigateToFragment = MutableLiveData<Boolean>(false)
    val navigateToFragment: LiveData<Boolean>
        get() = _navigateToFragment

    var curr = CurrFragment.ALL_TASKS

    fun navigate() {
        _navigateToFragment.value = true
    }

    fun doneNavigating() {
        _navigateToFragment.value = false
    }
}