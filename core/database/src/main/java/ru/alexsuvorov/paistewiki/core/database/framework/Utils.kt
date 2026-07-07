package ru.alexsuvorov.paistewiki.core.database.framework

import android.util.Log
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.Scanner
import java.util.zip.ZipInputStream

internal object Utils {
    private val TAG = SQLiteAssetHelper::class.java.simpleName

    fun splitSqlScript(script: String, delim: Char): List<String> {
        val statements = ArrayList<String>()
        var sb = StringBuilder()
        var inLiteral = false
        val content = script.toCharArray()
        for (i in 0 until script.length) {
            if (content[i] == '"') {
                inLiteral = !inLiteral
            }
            if (content[i] == delim && !inLiteral) {
                if (sb.isNotEmpty()) {
                    statements.add(sb.toString().trim())
                    sb = StringBuilder()
                }
            } else {
                sb.append(content[i])
            }
        }
        if (sb.isNotEmpty()) {
            statements.add(sb.toString().trim())
        }
        return statements
    }

    @Throws(IOException::class)
    fun writeExtractedFileToDisk(`in`: InputStream, outs: OutputStream) {
        val buffer = ByteArray(1024)
        var length: Int
        while (`in`.read(buffer).also { length = it } > 0) {
            outs.write(buffer, 0, length)
        }
        outs.flush()
        outs.close()
        `in`.close()
    }

    @Throws(IOException::class)
    fun getFileFromZip(zipFileStream: InputStream): ZipInputStream? {
        val zis = ZipInputStream(zipFileStream)
        if (zis.nextEntry != null) {
            Log.w(TAG, "extracting file...")
            return zis
        }
        return null
    }

    fun convertStreamToString(`is`: InputStream): String? {
        val s = Scanner(`is`).useDelimiter("\\A")
        return if (s.hasNext()) s.next() else ""
    }
}
