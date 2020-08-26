package com.ajm.employee

import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import java.io.File

class SingleAddActivity : AppCompatActivity() {

    var image: AppCompatImageView? = null
    var PARAM_IMAGE = "param_image"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_add)

        image = findViewById(R.id.image)
        if (intent.extras!!.containsKey(PARAM_IMAGE)) {
            val uriimage: Uri = Uri.parse(intent.extras!!.getString(PARAM_IMAGE))
            Log.d("uriimage", uriimage.getPath().toString())
            Log.d("uriimage", uriimage.toString())
            val photoUpload = File(uriimage.getPath().toString())
            val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream((uriimage)))
            image?.setImageBitmap(bitmap)

        }
    }
}
