package com.base.toolsframe.networkframe

import retrofit2.Call
import retrofit2.Callback

interface HttpCallback<T> : Callback<T> {
    fun onStart(call: Call<T>?)
    fun onCompleted(call: Call<T>?)
}