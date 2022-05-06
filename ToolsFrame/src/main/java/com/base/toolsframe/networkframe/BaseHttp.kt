package com.base.toolsframe.networkframe

import retrofit2.Retrofit

object BaseHttp {
        private const val TAG = "BaseHttp"

        @Volatile
        var DEFAULT: Retrofit? = null

        @Volatile
        var OTHER: Retrofit? = null
        fun <T> create(service: Class<T>?): T {
            val retrofit = DEFAULT
            return if (retrofit == null) {
                throw IllegalStateException("DEFAULT == null")
            } else {
                retrofit.create(service)
            }
        }

        fun <T> createOther(service: Class<T>?): T {
            val retrofit = OTHER
            return if (retrofit == null) {
                throw IllegalStateException("OTHER == null")
            } else {
                retrofit.create(service)
            }
        }

    init {
        throw AssertionError("No instances.")
    }
}
