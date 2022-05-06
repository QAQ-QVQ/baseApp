package com.base.toolsframe.networkframe

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LifecycleOwner
import retrofit2.Call
import java.util.*

interface LifecycleCall<T> : Cloneable {
    fun delegate(): Call<T>?
    fun enqueue(callback: HttpCallback<T>?)
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun enqueue(owner: LifecycleOwner?, callback: HttpCallback<T>?) {
        Objects.requireNonNull<Any>(callback, "callback==null")
        this.enqueue(
            (if (owner != null) LifecycleCallback(
                this,
                callback,
                owner
            ) else callback) as HttpCallback
        )
    }

}
