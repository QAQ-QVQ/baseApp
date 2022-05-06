package com.base.toolsframe.networkframe

import androidx.annotation.MainThread
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import retrofit2.Call
import retrofit2.Response
import java.util.concurrent.atomic.AtomicBoolean

class LifecycleCallback<T> internal constructor(
    private val lifecycleCall: LifecycleCall<T>?,
    private val delegate: HttpCallback<T>?,
    private val owner: LifecycleOwner
) :
    HttpCallback<T>, LifecycleObserver {
    private val once = AtomicBoolean()
    override fun onResponse(call: Call<T>?, response: Response<T>?) {
        if (!once.get()) {
            delegate?.onResponse(call, response)
        }
    }

    override fun onFailure(call: Call<T>?, t: Throwable?) {
        if (!once.get()) {
            delegate?.onFailure(call, t)
        }
    }

    override fun onStart(call: Call<T>?) {
        if (!once.get()) {
            delegate?.onStart(call)
        }
    }

    override fun onCompleted(call: Call<T>?) {
        if (!once.get()) {
            delegate?.onCompleted(call)
            DefaultExecutor.get().executeOnMainThread {
                owner.lifecycle.removeObserver(this)
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    @MainThread
    fun onChanged(owner: LifecycleOwner, event: Lifecycle.Event) {
        if (event == Lifecycle.Event.ON_DESTROY && once.compareAndSet(false, true)) {
            lifecycleCall?.delegate()!!.cancel()
            owner.lifecycle.removeObserver(this)
        }
    }

    init {
        DefaultExecutor.get().executeOnMainThread {
            if (owner.lifecycle
                    .currentState == Lifecycle.State.DESTROYED
            ) {
                once.set(true)
                lifecycleCall?.delegate()!!.cancel()
            } else {
                owner.lifecycle.addObserver(this)
            }
        }
    }
}

