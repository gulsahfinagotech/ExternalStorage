package com.gulsahozaltun.externalstorage

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.os.Environment.getExternalStoragePublicDirectory
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import java.io.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val fileName = findViewById(R.id.editTextFile) as EditText
        val fileData = findViewById(R.id.editTextData) as EditText
        val saveButton = findViewById<Button>(R.id.button_save) as Button
        val viewButton = findViewById(R.id.button_view) as Button
        getHardwareId(this)
        saveButton.setOnClickListener(View.OnClickListener {
            myExternalFile = File(getExternalFilesDir(filepath), fileName.text.toString())
            try {
                val fileOutPutStream = FileOutputStream(myExternalFile)
                fileOutPutStream.write(fileData.text.toString().toByteArray())
                fileOutPutStream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            Toast.makeText(applicationContext, "data save", Toast.LENGTH_SHORT).show()
        })
        viewButton.setOnClickListener(View.OnClickListener {
            myExternalFile = File(getExternalFilesDir(filepath), fileName.text.toString())

            val filename = fileName.text.toString()
            myExternalFile = File(getExternalFilesDir(filepath), filename)
            if (filename != null && filename.trim() != "") {
                var fileInputStream = FileInputStream(myExternalFile)
                var inputStreamReader: InputStreamReader = InputStreamReader(fileInputStream)
                val bufferedReader: BufferedReader = BufferedReader(inputStreamReader)
                val stringBuilder: StringBuilder = StringBuilder()
                var text: String? = null
                while ({ text = bufferedReader.readLine(); text }() != null) {
                    stringBuilder.append(text)
                }
                fileInputStream.close()
                //Displaying data on EditText
                Toast.makeText(applicationContext, stringBuilder.toString(), Toast.LENGTH_SHORT)
                    .show()
            }
        })

        if (!isExternalStorageAvailable || isExternalStorageReadOnly) {
            saveButton.isEnabled = false
        }
    }
    fun getHardwareId(context: Context): String? {
        val value=Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID)
        Toast.makeText(this,value.toString(),Toast.LENGTH_SHORT).show()
        Log.e("DENEME",value.toString())
        return value

    }


}

                //fbd47655091040e5
    public val filepath = "MyFileStorage"
    internal var myExternalFile: File? = null
    public val isExternalStorageReadOnly: Boolean
        get() {
            val extStorageState = Environment.getExternalStorageState()
            return if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
                true
            } else {
                false
            }
        }
    private val isExternalStorageAvailable: Boolean
        get() {
            val extStorageState = Environment.getExternalStorageState()
            return if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
                true
            } else {
                false
            }
        }




