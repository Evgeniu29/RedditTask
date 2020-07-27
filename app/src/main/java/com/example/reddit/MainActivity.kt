package com.example.reddit


import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.reddit.commons.InfiniteScrollListener
import com.example.reddit.commons.RxBaseActivity
import com.example.reddit.model.RedditNews
import com.example.reddit.news.adapter.NewsAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import rx.schedulers.Schedulers

class MainActivity : RxBaseActivity() {

    private var newsList: RecyclerView? = null

    private var redditNews: RedditNews? = null

    private val newsManager by lazy { NewsManager() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initNewList()

        initAdapter()

        requestNews()

    }

    private fun requestNews() {
        val subscription = newsManager.getNews(redditNews?.after ?: "")
            .subscribeOn(Schedulers.io())
            .subscribe(
                { retrievedNews ->
                    redditNews = retrievedNews
                    (newsList?.adapter as NewsAdapter).addNews(retrievedNews.news)
                },
                { e ->
                    this.newsList?.let {
                        Snackbar.make(it, e.message ?: "", Snackbar.LENGTH_LONG).show()
                    }
                }
            )
        subscriptions.add(subscription)
    }

    private fun initAdapter() {
        if (news_list.adapter == null) {
            news_list.adapter = NewsAdapter()
        }
    }

    private fun initNewList() {
        newsList = findViewById(R.id.news_list) as RecyclerView?
        newsList?.setHasFixedSize(true) // use this setting to improve performance
        val linearLayout = LinearLayoutManager(this)
        newsList?.layoutManager = linearLayout
        newsList?.clearOnScrollListeners()
        newsList?.addOnScrollListener(InfiniteScrollListener({ requestNews() }, linearLayout))
    }


}
