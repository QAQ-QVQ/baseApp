package com.base.toolsframe.fileframe

import com.base.toolsframe.fileframe.codeutils.CodeUtilsFlie

object  FileFactory {

    fun getFileObject(objectType: FileObjectConfig) : FileObject{
       return when(objectType){
            FileObjectConfig.FILEOBJECTCODEUTILS ->  CodeUtilsFlie()
            else ->  CodeUtilsFlie()
        }
    }

    fun getFileObject():FileObject{
        return getFileObject(FileObjectConfig.DEFAULT)
    }
}