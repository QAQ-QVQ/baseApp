package com.base.toolsframe.networkframe

class HttpError @JvmOverloads constructor(msg: String?, body: Any? = null as Any?) :
    RuntimeException(msg) {
    override lateinit var message: String

    @Transient
    val body: Any?
    override fun toString(): String {
        return "HttpError {msg=" + message + ", body=" + body + '}'
    }

    companion object {
        private const val serialVersionUID = 7714043264330138520L
    }

    init {
        if (body is Throwable) {
            initCause(body as Throwable?)
        }
        message = msg ?: "null"
        this.body = body
    }
}

