package com.jerryjin.kit.utils.kotlin_ext

import android.content.Context
import android.net.Uri
import androidx.documentfile.provider.DocumentFile
import okio.buffer
import okio.sink
import okio.source
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

/**
 * Author: Jerry
 *
 * Created at: 2022/03/25 12:47
 *
 * GitHub: https://github.com/JerryJin93
 *
 * Blog:
 *
 * Email: jerry93@foxmail.com

 * WeChat: AcornLake
 *
 * Version: 1.0.0
 *
 * Description: File extensions for Kotlin.
 */
infix fun File.writeTo(dest: File) =
    BufferedOutputStream(FileOutputStream(dest)).sink().buffer().use { sink ->
        FileInputStream(this).source().buffer().use { source ->
            val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
            while (source.read(buffer) != -1)
                sink.write(buffer)
        }
    }

infix fun Uri.getRealFileNameBy(context: Context) = DocumentFile.fromSingleUri(context, this)?.name

fun List<File>.getFileWithLongestFileName(): File =
    fold(this[0]) { fileWithLongestFileNameForNow, element ->
        if (fileWithLongestFileNameForNow.name.length > element.name.length) fileWithLongestFileNameForNow
        else element
    }