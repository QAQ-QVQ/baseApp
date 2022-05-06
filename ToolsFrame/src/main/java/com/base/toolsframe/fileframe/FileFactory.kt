package com.base.toolsframe.fileframe

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LifecycleOwner
import com.base.toolsframe.fileframe.api.IFileObject
import com.base.toolsframe.fileframe.codeutils.CodeUtilsFlie
import com.base.toolsframe.fileframe.config.FileObjectConfig
import com.base.toolsframe.networkframe.BaseHttp
import com.base.toolsframe.networkframe.HttpCallback
import com.base.toolsframe.networkframe.api.INetworkObject
import retrofit2.Call
import retrofit2.Response

object  FileFactory {
    /**
     * 获取 file api
     * get file api
     * @param objectType FileObjectConfig
     * @return IfileObject
     */
    private fun getFileObject(objectType: FileObjectConfig) : IFileObject {
       return when(objectType){
            FileObjectConfig.FILEOBJECTCODEUTILS ->  CodeUtilsFlie()
            else ->  CodeUtilsFlie()
        }
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun ss(lifecycleOwner: LifecycleOwner?){
        with(BaseHttp) {
            DEFAULT?.create(INetworkObject::class.java)?.get()?.enqueue(
                lifecycleOwner,
               object :HttpCallback<String>{
                   override fun onStart(call: Call<String>?) {
                       TODO("Not yet implemented")
                   }

                   override fun onCompleted(call: Call<String>?) {
                       TODO("Not yet implemented")
                   }

                   override fun onResponse(call: Call<String>, response: Response<String>) {
                       TODO("Not yet implemented")
                   }

                   override fun onFailure(call: Call<String>, t: Throwable) {
                       TODO("Not yet implemented")
                   }

               }
            )
        }
    }
}