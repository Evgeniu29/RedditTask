package com.example.reddit.news.adapter

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.RecyclerView
import com.example.reddit.ImageActivity
import com.example.reddit.R
import com.example.reddit.adapter.ViewType
import com.example.reddit.adapter.ViewTypeDelegateAdapter
import com.example.reddit.extensions.*
import com.example.reddit.model.RedditNewsItem
import kotlinx.android.synthetic.main.news_item.view.*


@Suppress("DEPRECATION")
class NewsDelegateAdapter : ViewTypeDelegateAdapter {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return NewsViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        holder as NewsViewHolder
        holder.bind(item as RedditNewsItem)
    }

    inner class NewsViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        parent.inflate(R.layout.news_item)
    ) {

        private val imgThumbnail = itemView.photo
        private val description = itemView.description
        private val author = itemView.author
        private val comments = itemView.comments
        private val time = itemView.time
        private val save = itemView.save

        fun bind(item: RedditNewsItem) {


            item.thumbnail.let { imgThumbnail.loadImg(it) }
            description.text = item.title
            author.text = item.author
            comments.text = "${item.numComments} comments"
            time.text = item.created.getFriendlyTime()

            itemView.setOnClickListener() {
                var i = Intent(it.getContext(), ImageActivity::class.java)
                i.putExtra("url", item.url)
                it.getContext().startActivity(i)
            }

            save.setOnClickListener() {
                if (Build.VERSION.SDK_INT >= 23) {
                    ActivityCompat.requestPermissions(
                        (it.context as Activity?)!!,
                        arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        1
                    )


                    val savedImageURL = MediaStore.Images.Media.insertImage(
                        it.context.getContentResolver(),
                        imgThumbnail.getDrawable().toBitmap(),
                        item.title,
                        "Image from reddit"
                    )

                    Uri.parse(savedImageURL)

                    showToast(it.context)

                }

            }
        }
    }
}



