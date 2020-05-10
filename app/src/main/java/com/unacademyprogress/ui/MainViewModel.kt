package com.unacademyprogress.ui

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel(
    userSeedPercent: Float = 0.0f
) : ViewModel(), Observable {

    @Bindable
    var inputUserPer = MutableLiveData<String>()

    var userPercent: Float = 0.0f
        get() = field

    init {
        userPercent = userSeedPercent
    }

    fun updatePercentage() {
        userPercent = getEnteredNumber()
    }

    private fun getEnteredNumber() = try {
        inputUserPer.value?.toFloat() ?: 0.0f
    } catch (exception: Exception) {
        0.0f
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    val validateInput: Boolean
        @Bindable("inputUserPer")
        get() = !((try {
            inputUserPer.value?.toInt() ?: 0
        } catch (e: Exception) {
            0
        }) > 100)

}