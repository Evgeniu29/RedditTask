package com.example.reddit

import com.example.reddit.api.RestAPI
import com.example.reddit.model.RedditNews
import com.example.reddit.model.RedditNewsItem
import rx.Observable

class NewsManager(private val api: RestAPI = RestAPI()) {

    fun getNews(after: String, limit: String = "10"): Observable<RedditNews> {
        return Observable.create { subscriber ->

            val callResponse = api.getNews(after, limit)
            val response = callResponse.execute()

            if (response.isSuccessful) {
                val dataResponse = response.body()?.data
                val news = dataResponse?.children?.map {
                    val item = it.data
                    RedditNewsItem(
                        item.author, item.title, item.num_comments,
                        item.created, item.thumbnail, item.url
                    )
                }
                val redditNews = news?.let {
                    RedditNews(
                        dataResponse.after ?: "",
                        dataResponse.before ?: "",
                        it
                    )
                }

                subscriber.onNext(redditNews)
                subscriber.onCompleted()
            } else {
                subscriber.onError(Throwable(response.message()))
            }

        }

    }
}