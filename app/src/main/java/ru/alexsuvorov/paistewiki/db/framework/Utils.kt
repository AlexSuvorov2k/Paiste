package ru.alexsuvorov.paistewiki.db.framework

import android.util.Log
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.Scanner
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream

internal object Utils {
    private val TAG: String = SQLiteAssetHelper::class.java.getSimpleName()

    fun splitSqlScript(script: String, delim: Char): MutableList<String?> {
        val statements: MutableList<String?> = ArrayList<String?>()
        var sb = StringBuilder()
        var inLiteral = false
        val content = script.toCharArray()
        for (i in 0..<script.length) {
            if (content[i] == '"') {
                inLiteral = !inLiteral
            }
            if (content[i] == delim && !inLiteral) {
                if (sb.length > 0) {
                    statements.add(sb.toString().trim { it <= ' ' })
                    sb = StringBuilder()
                }
            } else {
                sb.append(content[i])
            }
        }
        if (sb.length > 0) {
            statements.add(sb.toString().trim { it <= ' ' })
        }
        return statements
    }

    @Throws(IOException::class)
    fun writeExtractedFileToDisk(`in`: InputStream, outs: OutputStream) {
        val buffer = ByteArray(1024)
        var length: Int
        while ((`in`.read(buffer).also { length = it }) > 0) {
            outs.write(buffer, 0, length)
        }
        outs.flush()
        outs.close()
        `in`.close()
    }

    @Throws(IOException::class)
    fun getFileFromZip(zipFileStream: InputStream?): ZipInputStream? {
        val zis = ZipInputStream(zipFileStream)
        val ze: ZipEntry?
        while ((zis.getNextEntry().also { ze = it }) != null) {
            Log.w(Utils.TAG, "extracting file: '" + ze!!.getName() + "'...")
            return zis
        }
        return null
    }

    fun convertStreamToString(`is`: InputStream?): String? {
        return Scanner(`is`).useDelimiter("\\A").next()
    }
}
