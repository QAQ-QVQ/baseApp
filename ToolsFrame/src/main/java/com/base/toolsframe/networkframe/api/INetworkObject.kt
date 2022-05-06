package com.base.toolsframe.networkframe.api

import com.base.toolsframe.networkframe.LifecycleCall


interface INetworkObject {

    fun get(): LifecycleCall<String>
}