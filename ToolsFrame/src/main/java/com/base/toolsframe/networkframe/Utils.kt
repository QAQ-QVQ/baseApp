package com.base.toolsframe.networkframe

import android.content.Context
import okhttp3.ResponseBody
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

object Utils {
    var TAG = "base_Network"

    fun Utils() {}

    fun isAnnotationPresent(annotations: Array<Annotation>, cls: Class<out Annotation?>): Boolean {
        val var3 = annotations.size
        for (var4 in 0 until var3) {
            val annotation = annotations[var4]
            if (cls.isInstance(annotation)) {
                return true
            }
        }
        return false
    }

    @Throws(IOException::class)
    fun writeToFile(value: ResponseBody, path: String?): File? {
        val file = File(path)
        return if (file.exists() && !file.delete()) {
            throw IOException("failed to delete file:" + file.path)
        } else {
            val tmp = File(file.path + ".tmp")
            if (tmp.exists() && !tmp.delete()) {
                throw IOException("failed to delete tmp file:" + tmp.path)
            } else {
                val `is` = value.byteStream()
                if (!tmp.createNewFile()) {
                    throw IOException("failed to create file:" + tmp.path)
                } else {
                    val fos = FileOutputStream(tmp)
                    val var8: File
                    try {
                        val buffer = ByteArray(8096)
                        var c: Int
                        while (`is`.read(buffer).also { c = it } != -1) {
                            fos.write(buffer, 0, c)
                        }
                        if (!tmp.renameTo(file)) {
                            throw IOException("failed to rename file:" + tmp.path)
                        }
                        var8 = file
                    } catch (var10: Throwable) {
                        try {
                            fos.close()
                        } catch (var9: Throwable) {
                            var10.addSuppressed(var9)
                        }
                        throw var10
                    }
                    fos.close()
                    var8
                }
            }
        }
    }

    fun getLanguage(context: Context): String? {
        val locale = context.resources.configuration.locale
        return locale.language
    }
}
