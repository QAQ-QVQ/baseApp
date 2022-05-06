package com.base.toolsframe.fileframe.codeutils

import com.base.toolsframe.fileframe.api.IFileObject
import com.blankj.utilcode.util.FileUtils
import java.io.File

class CodeUtilsFlie : IFileObject {

    override fun getFileByPath(filePath: String):File {
       return FileUtils.getFileByPath(filePath)
    }

    override fun isFileExists(filePath: String):Boolean {
      return  FileUtils.isFileExists(filePath)
    }

    override fun rename(file: File, newName: String): Boolean {
        return FileUtils.rename(file,newName)
    }

    override fun isDir(file: File): Boolean {
        return FileUtils.isDir(file)
    }

}