package com.example.demo

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.FileUtils
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val PICKFILE_RESULT_CODE = 0
        setContentView(R.layout.activity_main)
        val button1: View = findViewById(R.id.selectFile)
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                1)
        }
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
        }

        button1.setOnClickListener {
            // adding Runtime permissions

            val chooseFile = Intent(Intent.ACTION_GET_CONTENT)
            chooseFile.addCategory(Intent.CATEGORY_OPENABLE)
            chooseFile.type = "text/plain"
            startActivityForResult(
                Intent.createChooser(chooseFile, "Choose a file"),
                PICKFILE_RESULT_CODE
            )
            //  showFileChooser()
        }
    }
    val PICKFILE_RESULT_CODE = 0

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        var dest: File? = null
        var source: File? = null
        if (requestCode == PICKFILE_RESULT_CODE && resultCode == Activity.RESULT_OK) {
            val contentDescriber: Uri? = data?.data
            val src = contentDescriber!!.path
            source = File(src)
            Log.d("src is ", source.toString())
            val filename = contentDescriber.lastPathSegment
            if (filename != null) {
                Log.d("FileName is ", filename)
            }

             dest =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            /* File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                 .absolutePath + File.separator + "/GSW/" + filename)*/
            Log.d("Destination is ", dest.toString());
            try {
                Log.d("tryblock ", dest.toString());

                copyFile(source, dest)
                Log.d("copied to ", dest.toString());
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
    }


            @Throws(IOException::class)
            private fun copyFile(src: File, dest: File) {

                Log.d("copy1", "copyFile: t")
                val inp = FileInputStream(src).channel
                Log.d("cop2", "copyFile: t")
               val out = FileOutputStream(dest).channel
                Log.d("cop3", "copyFile: t")
                try {
                   // FileUtils.copy(FileInputStream(src),FileOutputStream(dest))
                    inp!!.transferTo(0, inp.size(), out)
                    Log.d("cop", "copyFile: dest")
                } catch (e: Exception) {
                    Log.d("Exception", e.toString())
                } finally {
                    inp?.close()
                    out?.close()
                }
            }
    }

       /* try {
            Log.d("tryblock ", destination.toString());
            if (source != null) {
                if (destination != null) {
                    Log.d("try2 ", destination.toString());
                    copyFile(source, destination)
                    Log.d("copied to ", destination.toString());
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }*/











