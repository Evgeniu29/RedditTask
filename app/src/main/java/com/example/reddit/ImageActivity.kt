package com.example.reddit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.reddit.extensions.loadImg
import kotlinx.android.synthetic.main.activity_info.*

class ImageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        val bundle: Bundle = intent.extras!!
        val url = bundle.get("url")

        imageview.let { imageview.loadImg(url.toString()) }

    }

}
