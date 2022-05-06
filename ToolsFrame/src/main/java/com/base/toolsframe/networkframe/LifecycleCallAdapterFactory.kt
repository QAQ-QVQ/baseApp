package com.base.toolsframe.networkframe

import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.util.concurrent.Executor
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.SkipCallbackExecutor
import retrofit2.CallAdapter.Factory


class LifecycleCallAdapterFactory : Factory() {

    override operator fun get(
        returnType: Type?,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        return if (CallAdapter.Factory.getRawType(returnType) != LifecycleCall::class.java) {
            null
        } else if (returnType !is ParameterizedType) {
            throw IllegalArgumentException("CompletableCall return type must be parameterized as CompletableCall<Foo> or CompletableCall<? extends Foo>")
        } else {
            val responseType =
                CallAdapter.Factory.getParameterUpperBound(0, returnType as ParameterizedType?)
            val executor = if (Utils.isAnnotationPresent(
                    annotations,
                    SkipCallbackExecutor::class.java
                )
            ) null else retrofit.callbackExecutor()
            object : CallAdapter<Any?, LifecycleCall<*>> {
                override fun responseType(): Type {
                    return responseType
                }

                override fun adapt(call: Call<Any?>): LifecycleCall<*> {
                    return RealLifecycleCall((executor ?: DefaultExecutor.get()), call)
                }
            }
        }
    }
}