package com.example.reddit.model

import android.os.Parcel
import android.os.Parcelable
import com.example.reddit.adapter.AdapterConstants
import com.example.reddit.adapter.ViewType
import com.example.reddit.extensions.createParcel

data class RedditNews(

    val after: String?,
    val before: String?,
    val news: List<RedditNewsItem>
) : Parcelable {
    companion object {
        @JvmField
        @Suppress("unused")
        val CREATOR = createParcel { RedditNews(it) }
    }

    protected constructor(parcelIn: Parcel) : this(
        parcelIn.readString(),
        parcelIn.readString(),
        mutableListOf<RedditNewsItem>().apply {
            parcelIn.readTypedList(this, RedditNewsItem.CREATOR)
        }
    )

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(after)
        dest.writeString(before)
        dest.writeTypedList(news)
    }

    override fun describeContents() = 0
}


data class RedditNewsItem(
    val author: String,
    val title: String,
    val numComments: Int,
    val created: Long,
    val thumbnail: String,
    val url: String
) : ViewType, Parcelable {

    override fun getViewType() = AdapterConstants.NEWS

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<RedditNewsItem> =
            object : Parcelable.Creator<RedditNewsItem> {
                override fun createFromParcel(source: Parcel): RedditNewsItem =
                    RedditNewsItem(source)

                override fun newArray(size: Int): Array<RedditNewsItem?> = arrayOfNulls(size)
            }
    }

    constructor(source: Parcel) : this(
        source.readString()!!,
        source.readString().toString(), source.readInt(), source.readLong(),
        source.readString()!!, source.readString()!!
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(author)
        dest?.writeString(title)
        dest?.writeInt(numComments)
        dest?.writeLong(created)
        dest?.writeString(thumbnail)
        dest?.writeString(url)
    }
}