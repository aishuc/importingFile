package com.example.demo

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream


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

        if (requestCode == PICKFILE_RESULT_CODE && resultCode == Activity.RESULT_OK) {
            val content_describer: Uri? = data?.data
            val srck = content_describer?.path
            val source = File(srck)
            Log.d("src is ", source.toString())
            val filekapath = content_describer?.lastPathSegment
            val text: TextView = findViewById(R.id.fileNameT)

            val srcFile :File = File(filekapath)
            val srcfilename : String = srcFile.name
            text.setText(srcFile.name)
            Log.d("Filepath is ", filekapath!!)

            var dest =
                File(Environment.getExternalStorageDirectory().absolutePath + "/resume/$srcfilename")
            Log.d("Destination is ", dest.toString())
            val SetToFolder: TextView = findViewById(R.id.folder)
            SetToFolder.setEnabled(true)


          Log.d("Destination is ", dest.toString());
            try {
                val src : InputStream? = contentResolver.openInputStream(content_describer)
              /*  val inChannel: FileChannel = FileInputStream(attr.src).getChannel()
                val outChannel: FileChannel = FileOutputStream(dst).getChannel()*/
                Log.d("ip ", "inputstream $src ")
                var out = FileOutputStream(dest)
                val buffer = ByteArray(1024)
                var len: Int
                if (src != null) {
                    while (src.read(buffer).also { len = it } != -1) {
                        out.write(buffer, 0, len)
                    }
                }
               // val out : OutputStream = FileOutputStream(dest)
               // Log.d("op", "outputstream $out ")
               //copyFile(src, dest)
                Log.d("copied to ", dest.toString());
            } catch (e: IOException) {
                e.printStackTrace()
            }

    }
    }


         /*   @Throws(IOException::class)
            private fun copyFile(src: InputStream?, dest: File) {


                Log.d("copy1", "copyFile: t")

              val inp = FileInputStream(src).channel
                Log.d("cop2", "copyFile: t")
              val out = FileOutputStream(dest).channel
                Log.d("cop3", "copyFile: t")
                try {

                 //  FileUtils.copy(src,FileOutputStream(dest))
                    inp!!.transferTo(0, inp.size(), out)
                    Log.d("cop", "copyFile: dest")
                } catch (e: Exception) {
                    Log.d("Exception", e.toString())
                } finally {
                    inp?.close()
                    out?.close()
                }
            }*/
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











