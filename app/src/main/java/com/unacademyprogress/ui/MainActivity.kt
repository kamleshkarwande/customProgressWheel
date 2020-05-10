package com.unacademyprogress.ui

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.unacademyprogress.R
import com.unacademyprogress.databinding.MainActivityBinding

const val SEED_PERCENT = 60.0f

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: MainActivityBinding
    private val mainVM: MainViewModel by lazy {
        ViewModelProvider(this@MainActivity, MainViewModelFactory(SEED_PERCENT)).get(
            MainViewModel::class.java
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<MainActivityBinding>(this@MainActivity, R.layout.activity_main)
            .apply {
                this.lifecycleOwner = this@MainActivity
                this.mainViewModel = mainVM
            }
        setupLayout()

    }

    private fun setupLayout() {
        with(binding) {
            btnSubmit.setOnClickListener(this@MainActivity)
        }
    }

    private fun updateProgressBar() {
        mainVM.updatePercentage()
        binding.progressBar.setPercentage(mainVM.userPercent)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_submit -> {
                updateProgressBar()
                hideKeyboard(view)
            }
        }
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
