package com.base.toolsframe.fileframe.api

import com.blankj.utilcode.util.FileUtils
import java.io.File

interface IFileObject {
    /**
     * 根据文件路径获取文件
     * Return the file by path.
     * @param filePath String?
     */
    fun getFileByPath(filePath:String):File

    /**
     * 判断文件是否存在
     * Return whether the file exists.
     * @param filePath String
     */
    fun isFileExists(filePath:String):Boolean

    /**
     * 判断文件是否存在
     * Return whether the file exists.
     * @param file File
     */
    fun isFileExists(file:File):Boolean{
        return if (file.exists()) {
            true
        } else isFileExists(file.absolutePath)
    }

    /**
     * 重命名文件
     * Rename the file.
     * @param filePath String?
     * @param newName String?
     * @return Boolean
     */
    fun rename(filePath: String, newName: String): Boolean{
        return rename(FileUtils.getFileByPath(filePath), newName)
    }

    /**
     * 重命名文件
     * Rename the file.
     * @param file File?
     * @param newName String?
     * @return Boolean
     */
    fun rename(file: File, newName: String): Boolean

    /**
     * Return whether it is a directory.
     * 是否是文件夹
     * @param dirPath String?
     * @return Boolean
     */
    fun isDir(dirPath: String): Boolean{
        return isDir(getFileByPath(dirPath))
    }

    /**
     * Return whether it is a directory.
     * 是否是文件夹
     * @param file File?
     * @return Boolean
     */
    fun isDir(file: File): Boolean
}