/*
 * Copyright (c) 2020/5/6 (YYYY/MM/DD)
 * Created by NachtgeistW.
 * This file is used to:
 *
 */

package com.nachtgeistw.impurebirdkt.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nachtgeistw.impurebirdkt.R
import com.nachtgeistw.impurebirdkt.databinding.HomepageTweetItemBinding
import twitter4j.Status

class TweetAdapter(private var tweetList: List<Status>) :
    RecyclerView.Adapter<TweetAdapter.TweetViewHolder>() {
//    private lateinit var tweetItemBinding: HomepageTweetItemBinding

    inner class TweetViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val userName: TextView = itemView.findViewById(R.id.homepage_tweet_user_name)
        val userText: TextView = itemView.findViewById(R.id.homepage_tweet_user_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TweetViewHolder {
        Log.i("Twitter", "${javaClass.simpleName} > onCreateViewHolder")

        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.homepage_tweet_item, parent, false)
//        tweetItemBinding = HomepageTweetItemBinding.inflate(inflater)
        return TweetViewHolder(view)
    }

    override fun getItemCount() = tweetList.size

    override fun onBindViewHolder(holder: TweetViewHolder, position: Int) {
        val tweet: Status = tweetList[position]
        holder.userName.text = tweet.user.name
        holder.userText.text = tweet.text
    }
}
//https://www.tuicool.com/articles/3Unmiir