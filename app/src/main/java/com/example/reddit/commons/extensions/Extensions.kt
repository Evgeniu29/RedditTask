@file:JvmName("ExtensionsUtils")

package com.example.reddit.extensions

import android.Manifest
import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.bumptech.glide.Glide
import com.example.reddit.R


fun ViewGroup.inflate(layoutId: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)
}

fun ImageView.loadImg(imageUrl: String) {

        Glide.with(context)
            .load(imageUrl).placeholder(R.drawable.noimage).error(R.drawable.noimage)
            .fallback(R.drawable.noimage)
            .into(this);

}

fun <T> androidLazy(initializer: () -> T) : Lazy<T> = lazy(LazyThreadSafetyMode.NONE, initializer)

inline fun <reified T : Parcelable> createParcel(
    crossinline createFromParcel: (Parcel) -> T?): Parcelable.Creator<T> =
    object : Parcelable.Creator<T> {
        override fun createFromParcel(source: Parcel): T? = createFromParcel(source)
        override fun newArray(size: Int): Array<out T?> = arrayOfNulls(size)
    }

fun isNullOrEmpty(str: String?): Boolean {
    return if (str != null && !str.isEmpty()) false else true
}


fun showToast(context: Context?) {
    val toast = Toast.makeText(context, "File is saved", Toast.LENGTH_SHORT);

    toast.setGravity(Gravity.CENTER, 0, 0)

    toast.show()
}


fun checkPermission(context: Context?) {
    if (Build.VERSION.SDK_INT >= 23) {
        ActivityCompat.requestPermissions(
            (context as Activity?)!!,
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            1
        )



    }
}



