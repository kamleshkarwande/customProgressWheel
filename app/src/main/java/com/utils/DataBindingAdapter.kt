package com.utils

import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter(value = ["validationRule", "errMsg"], requireAll = true)
fun TextInputLayout.bindValidation(validation: Boolean, errMsg: String) {
    this.isErrorEnabled = validation
    this.error = if (validation) {
        errMsg
    } else {
        null
    }
}