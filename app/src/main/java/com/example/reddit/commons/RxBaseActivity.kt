package com.example.reddit.commons

import androidx.appcompat.app.AppCompatActivity
import rx.subscriptions.CompositeSubscription

open class RxBaseActivity() : AppCompatActivity() {

    protected var subscriptions = CompositeSubscription()

    override fun onResume() {
        super.onResume()
        subscriptions = CompositeSubscription()
    }

    override fun onPause() {
        super.onPause()
        if (!subscriptions.isUnsubscribed) {
            subscriptions.unsubscribe()
        }
        subscriptions.clear()
    }
}