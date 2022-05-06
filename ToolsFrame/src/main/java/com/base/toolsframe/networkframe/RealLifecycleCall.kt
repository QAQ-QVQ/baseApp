package com.base.toolsframe.networkframe

import android.os.Build
import androidx.annotation.RequiresApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.util.*
import java.util.concurrent.Executor

class RealLifecycleCall<T> internal constructor(
    private val callbackExecutor: Executor,
    private val delegate: Call<T>
) :
    LifecycleCall<T> {
    override fun delegate(): Call<T> {
        return delegate
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun enqueue(callback: HttpCallback<T>?) {
        Objects.requireNonNull<Any>(callback, "callback==null")
        callbackExecutor.execute { callback?.onStart(delegate) }
        delegate.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                callbackExecutor.execute {
                    if (delegate.isCanceled) {
                        callback?.onFailure(
                            delegate,
                            IOException("Canceled")
                        )
                    } else {
                        callback?.onResponse(delegate, response)
                    }
                    callback?.onCompleted(delegate)
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                callbackExecutor.execute {
                    callback?.onFailure(delegate, t)
                    callback?.onCompleted(delegate)
                }
            }
        })
    }

    fun LifecycleCall(): LifecycleCall<T?> {
        return RealLifecycleCall<T?>(callbackExecutor, delegate.clone())
    }
}