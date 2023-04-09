package ru.startandroid.develop.files

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.File
import java.io.FileNotFoundException
import java.io.FileReader
import java.io.FileWriter
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStreamWriter

const val LOG_TAG = "myLogs"
const val FILENAME = "file"
const val DIR_SD = "MyFiles"
const val FILENAME_SD = "fileSD"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onClick(v: View) {
        when(v.id) {
            R.id.btnWrite ->  writeFile()
            R.id.btnRead -> readFile()
            R.id.btnWriteSD -> writeFileSD()
            R.id.btnReadSD -> readFileSD()
        }
    }

    private fun writeFile() {
        try {
            val bw = BufferedWriter(OutputStreamWriter(openFileOutput(FILENAME, MODE_PRIVATE)))
            bw.write("Содержание файла")
            bw.close()
            Log.d(LOG_TAG, "Файл записан")
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun readFile() {
        try {
            val br = BufferedReader(InputStreamReader(openFileInput(FILENAME)))
            var str: String? = ""
            while (br.readLine().also { str = it } != null) {
                Log.d(LOG_TAG, str!!)
            }
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun writeFileSD() {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Log.d(LOG_TAG, "SD-карта не доступна: ${Environment.getExternalStorageState()}")
            return
        }
        var sdPath: File = Environment.getExternalStorageDirectory()
        sdPath = File(sdPath.absolutePath + "/" + DIR_SD)
        sdPath.mkdirs()
        val sdFile = File(sdPath, FILENAME_SD)

        try {
            val bw = BufferedWriter(FileWriter(sdFile))
            bw.write("Содержимое файла на SD")
            bw.close()
            Log.d(LOG_TAG, "Файл записан на SD: ${sdFile.absolutePath}")
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun readFileSD() {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Log.d(LOG_TAG, "SD-карта не доступна: ${Environment.getExternalStorageState()}")
            return
        }
        var sdPath: File = Environment.getExternalStorageDirectory()
        sdPath = File(sdPath.absolutePath + "/" + DIR_SD)
        val sdFile = File(sdPath, FILENAME_SD)
        try {
            val br = BufferedReader(FileReader(sdFile))
            var str: String? = ""
            while (br.readLine().also { str = it } != null) {
                Log.d(LOG_TAG, str!!)
            }
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        catch (e: IOException) {
            e.printStackTrace()
        }
    }
}