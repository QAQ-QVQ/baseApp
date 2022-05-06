package com.base.baseapp.ui.about

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.base.ui.BaseViewModel

class AboutViewModel : BaseViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is about Fragment"
    }
    val text: LiveData<String> = _text

}