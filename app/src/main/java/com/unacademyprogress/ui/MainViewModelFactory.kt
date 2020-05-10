package com.unacademyprogress.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MainViewModelFactory(private val seedCount: Float = 0.0f) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            MainViewModel(seedCount) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }

}