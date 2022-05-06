package com.base.toolsframe.networkframe

import android.content.Context
import android.net.*
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import com.base.ui.BaseApplication

private val TAG = "NetworkLiveData"

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class NetworkLiveData(context: Context) : LiveData<Int>() {

    private var networkCallback: ConnectivityManager.NetworkCallback

    private var request: NetworkRequest

    private var manager: ConnectivityManager

    init {
        networkCallback = NetworkCallbackImpl()
        request = NetworkRequest.Builder().build()
        manager = context
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    override fun onActive() {
        super.onActive()
        manager.registerNetworkCallback(request, networkCallback)
    }

    override fun onInactive() {
        super.onInactive()
        manager.unregisterNetworkCallback(networkCallback)
    }

    object NetworkLiveDataHolder {
        val INSTANCE = NetworkLiveData(BaseApplication.getApplicationContext())
    }

    companion object {
        fun getInstance(): NetworkLiveData {
            return NetworkLiveDataHolder.INSTANCE
        }
    }

    class NetworkCallbackImpl : ConnectivityManager.NetworkCallback() {

        override fun onAvailable(network: Network) {
            super.onAvailable(network)
//            MLog.d(TAG, "onAvailable: " + "网络已连接")

            getInstance().postValue(NetworkState.CONNECT)
        }

        override fun onLost(network: Network) {
            super.onLost(network)
//            MLog.d(TAG, "onLost: " + "网络断开")
            getInstance().postValue(NetworkState.NONE)
        }

        override fun onUnavailable() {
            super.onUnavailable()
//            MLog.d(TAG, "onUnavailable: ")
        }

        override fun onBlockedStatusChanged(network: Network, blocked: Boolean) {
            super.onBlockedStatusChanged(network, blocked)
//            MLog.d(TAG, "onBlockedStatusChanged: ")
        }

        override fun onLosing(network: Network, maxMsToLive: Int) {
            super.onLosing(network, maxMsToLive)
//            MLog.d(TAG, "onLosing: ")
        }

        override fun onLinkPropertiesChanged(network: Network, linkProperties: LinkProperties) {
            super.onLinkPropertiesChanged(network, linkProperties)
//            MLog.d(TAG, "onLinkPropertiesChanged: ")
        }

        override fun onCapabilitiesChanged(
            network: Network,
            networkCapabilities: NetworkCapabilities
        ) {
            super.onCapabilitiesChanged(network, networkCapabilities)
            if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
//                MLog.d("WIFI: ", "WIFI已连接")
                getInstance().postValue(NetworkState.WIFI)
//                getInstance().postValue(NetworkState.CONNECT)
            } else if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
//                MLog.d("CELLULAR: ", "移动网络已连接")
                getInstance().postValue(NetworkState.CELLULAR)
//                getInstance().postValue(NetworkState.CONNECT)
            }
        }
    }

    /**
     * 判断网络是否连接
     */
    fun isNetworkConnected(context: Context?): Boolean {
        if (context != null) {
            val mConnectivityManager = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                val mNetworkInfo = mConnectivityManager.activeNetworkInfo
                if (mNetworkInfo != null) {
                    return mNetworkInfo.isConnected
                }
            } else {
                val network = mConnectivityManager.activeNetwork ?: return false
                val status = mConnectivityManager.getNetworkCapabilities(network)
                    ?: return false
                if (status.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)) {
                    return true
                }
            }
        }
        return false
    }


    /**
     * 判断是否是WiFi连接
     */
    fun isWifiConnected(context: Context?): Boolean {
        if (context != null) {
            val mConnectivityManager = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                val mWiFiNetworkInfo = mConnectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                if (mWiFiNetworkInfo != null) {
                    return mWiFiNetworkInfo.isConnected
                }
            } else {
                val network = mConnectivityManager.activeNetwork ?: return false
                val status = mConnectivityManager.getNetworkCapabilities(network)
                    ?: return false
                if (status.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    return true
                }
            }
        }
        return false
    }

    /**
     * 判断是否是数据网络连接
     */
    @RequiresApi(Build.VERSION_CODES.Q)
    fun isMobileConnected(context: Context?): Boolean {
        if (context != null) {
            val mConnectivityManager = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                val mMobileNetworkInfo = mConnectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                if (mMobileNetworkInfo != null) {
                    return mMobileNetworkInfo.isConnected
                }
            } else {
                val network = mConnectivityManager.activeNetwork ?: return false
                val status = mConnectivityManager.getNetworkCapabilities(network)
                    ?: return false
                status.transportInfo
                if (status.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    return true
                }

            }
        }
        return false
    }
}
object NetworkState {

    // 无网络
    const val NONE = 0

    // 网络连接
    const val CONNECT = 1

    // WIFI
    const val WIFI = 2

    // 移动网络
    const val CELLULAR = 3

}

