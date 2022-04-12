package com.gulsahozaltun.externalstorage

import android.Manifest
import android.R.attr
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gulsahozaltun.externalstorage.databinding.ActivityMain2Binding
import android.R.attr.data

import android.os.Environment
import android.view.View
import android.widget.Toast
import java.io.*
import androidx.core.app.ActivityCompat





class MainActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityMain2Binding
    lateinit var data: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        ActivityCompat.requestPermissions(
            this, arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.MANAGE_EXTERNAL_STORAGE
            ), 1
        )

        //File dosyaYolu=Environment.getExternalStoragePublicDirectory("kendiKlasorum");

        //File dosyaYolu=Environment.getExternalStoragePublicDirectory("kendiKlasorum");
        val dosyaYolu: File =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES)

        val file = File(dosyaYolu, "myExternalPublic.txt")





        binding.buttonSave.setOnClickListener {
            saveToExternalPublic()
        }

        binding.buttonView.setOnClickListener {
            loadFromExternalPublic()
        }


    }


    fun saveToExternalPublic() {
        val data: String = binding.editTextData.getText().toString()

        //File dosyaYolu=Environment.getExternalStoragePublicDirectory("kendiKlasorum");
        val dosyaYolu =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES)
        val file = File(dosyaYolu, "myExternalPublic.txt")
        writeToFile(file, data)
    }


    fun loadFromExternalPublic() {
        val dosyaYolu =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES)
        val dosya = File(dosyaYolu, "myExternalPublic.txt")
        val dosyaIcerigi = readFromFile(dosya)
        binding.editTextData.setText(dosyaIcerigi)
    }




    private fun writeToFile(file: File, data:String) {


         var fos:FileOutputStream?=null
        try {

            fos = FileOutputStream(file);
            fos.write(data.toByteArray())

        } catch (e: FileNotFoundException) {
            e.printStackTrace();
        } catch (e: IOException) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                    Toast.makeText(this, "Dosyaya Yazıldı", Toast.LENGTH_SHORT).show();
                } catch (e:IOException ) {
                    e.printStackTrace();
                }
            }
        }
    }


    private fun readFromFile(veriOkunacakDosya: File): String? {
        val buffer = StringBuffer()
        var fis: FileInputStream? = null
        try {
            fis = FileInputStream(veriOkunacakDosya)
            var read: Int
            while (fis.read().also { read = it } != -1) {
                buffer.append(read.toChar())
            }
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (fis != null) {
                try {
                    fis.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        return buffer.toString()
    }





}