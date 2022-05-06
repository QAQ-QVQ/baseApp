package com.base.toolsframe.networkframe

interface IBaseCallBack<T> {
    fun onStart() {
        //Do nothing, this is an interface, it will be implemented in a specific class
    }

    fun onCompleted() {
        //Do nothing, this is an interface, it will be implemented in a specific class
    }

    fun onError(error: HttpError?) {
        //Do nothing, this is an interface, it will be implemented in a specific class
    }

    fun onSuccess()
}