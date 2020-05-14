/*
 * Copyright (c) 2020/5/6 (YYYY/MM/DD)
 * Created by NachtgeistW.
 * This file is used to:
 *
 */

package com.nachtgeistw.impurebirdkt.ui

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.nachtgeistw.impurebirdkt.R
import com.nachtgeistw.impurebirdkt.databinding.*
import twitter4j.Status
import twitter4j.TwitterException
import java.lang.Exception

class TweetAdapter(private var tweetList: List<Status>) :
    RecyclerView.Adapter<TweetAdapter.TweetViewHolder>() {
    private var PIC_NONE = 0
    private var PIC_ONE = 1
    private var PIC_TWO = 2
    private var PIC_THREE = 3
    private var PIC_FOUR = 4

    private lateinit var inflater: LayoutInflater
    private lateinit var binding: HomepageTweetItemBinding
    private lateinit var binding1: HomepageTweetItem1Binding
    private lateinit var binding2: HomepageTweetItem2Binding
    private lateinit var binding3: HomepageTweetItem3Binding
    private lateinit var binding4: HomepageTweetItem4Binding

    inner class TweetViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val userName: TextView = itemView.findViewById(R.id.homepage_tweet_user_name)
        val userText: TextView = itemView.findViewById(R.id.homepage_tweet_user_text)
        val userAvatar: ImageView = itemView.findViewById(R.id.homepage_tweet_user_avatar)
        lateinit var pic1: ImageView
        lateinit var pic2: ImageView
        lateinit var pic3: ImageView
        lateinit var pic4: ImageView

    }

    override fun getItemCount() = tweetList.size

    // Check the media num in Status
    override fun getItemViewType(position: Int): Int {
        return when (tweetList[position].mediaEntities.size) {
            1 -> PIC_ONE
            2 -> PIC_TWO
            3 -> PIC_THREE
            4 -> PIC_FOUR
            else -> PIC_NONE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TweetViewHolder {
        Log.i("Twitter", "${javaClass.simpleName} > onCreateViewHolder")

        inflater = LayoutInflater.from(parent.context)

//        TODO("做PicActivity的跳转")
        when (viewType) {
            PIC_ONE -> {
                binding1 = HomepageTweetItem1Binding.inflate(inflater)
                val holder = TweetViewHolder(binding1.root)
                holder.pic1 = binding1.homepageTweetPic1
                holder.itemView.setOnClickListener {
                    val position = holder.adapterPosition
                    val pic1 = tweetList[position]
                }
                return holder
            }
            PIC_TWO -> {
                binding2 = HomepageTweetItem2Binding.inflate(inflater)
                val holder = TweetViewHolder(binding2.root)
                holder.pic1 = binding2.homepageTweetPic1
                holder.pic2 = binding2.homepageTweetPic2
                return holder
            }
            PIC_THREE -> {
                binding3 = HomepageTweetItem3Binding.inflate(inflater)
                val holder = TweetViewHolder(binding3.root)
                holder.pic1 = binding3.homepageTweetPic1
                holder.pic2 = binding3.homepageTweetPic2
                holder.pic3 = binding3.homepageTweetPic3
                return holder
            }
            PIC_FOUR -> {
                binding4 = HomepageTweetItem4Binding.inflate(inflater)
                val holder = TweetViewHolder(binding4.root)
                holder.pic1 = binding4.homepageTweetPic1
                holder.pic2 = binding4.homepageTweetPic2
                holder.pic3 = binding4.homepageTweetPic3
                holder.pic4 = binding4.homepageTweetPic4
                return holder
            }
            else -> {
                binding = HomepageTweetItemBinding.inflate(inflater)
                val holder = TweetViewHolder(binding.root)
                return holder
            }
        }
    }

    override fun onBindViewHolder(holder: TweetViewHolder, position: Int) {
        Log.e("Twitter", "onBindViewHolder > $position")

        val tweet = tweetList[position]
        holder.userName.text = tweet.user.name
        holder.userText.text = tweet.text
        Glide.with(holder.userAvatar.context)
            .load(tweet.user.get400x400ProfileImageURLHttps())
            .into(holder.userAvatar)
        val picNum = tweet.mediaEntities.size

        try {
            when (picNum) {
                1 -> {
                    Log.i(
                        "Twitter", "onBindViewHolder > " +
                                "${tweet.user.name} > ${tweet.mediaEntities[0].mediaURLHttps}"
                    )
                    Glide.with(holder.pic1.context)
                        .load(tweet.mediaEntities[0].mediaURLHttps)
                        .into(holder.pic1)
                }
                2 -> {
                    Log.i(
                        "Twitter", "onBindViewHolder > " +
                                "${tweet.user.name} > ${tweet.mediaEntities[0].mediaURLHttps}"
                    )
                    Glide.with(holder.pic1.context)
                        .load(tweet.mediaEntities[0].mediaURLHttps)
                        .into(holder.pic1)
                    Glide.with(holder.pic2.context)
                        .load(tweet.mediaEntities[1].mediaURLHttps)
                        .into(holder.pic2)
                }
                3 -> {
                    Log.i(
                        "Twitter", "onBindViewHolder > " +
                                "${tweet.user.name} > ${tweet.mediaEntities[0].mediaURLHttps}"
                    )
                    Glide.with(holder.pic1.context)
                        .load(tweet.mediaEntities[0].mediaURLHttps)
                        .into(holder.pic1)
                    Glide.with(holder.pic2.context)
                        .load(tweet.mediaEntities[1].mediaURLHttps)
                        .into(holder.pic2)
                    Glide.with(holder.pic3.context)
                        .load(tweet.mediaEntities[2].mediaURLHttps)
                        .into(holder.pic3)
                }
                4 -> {
                    Log.i(
                        "Twitter", "onBindViewHolder > " +
                                "${tweet.user.name} > ${tweet.mediaEntities[0].mediaURLHttps}"
                    )
                    Glide.with(holder.pic1.context)
                        .load(tweet.mediaEntities[0].mediaURLHttps)
                        .into(holder.pic1)
                    Glide.with(holder.pic2.context)
                        .load(tweet.mediaEntities[1].mediaURLHttps)
                        .into(holder.pic2)
                    Glide.with(holder.pic3.context)
                        .load(tweet.mediaEntities[2].mediaURLHttps)
                        .into(holder.pic3)
                    Glide.with(holder.pic4.context)
                        .load(tweet.mediaEntities[3].mediaURLHttps)
                        .into(holder.pic4)
                }
            }
        } catch (e: TwitterException) {
            Log.e("Twitter", "onBindViewHolder > $position > $picNum > ${e.errorMessage}")
            e.printStackTrace()
        } catch (e: Exception) {
            Log.e("Twitter", "onBindViewHolder > $position > $picNum > ${e.message}")
            e.printStackTrace()
        }

    }

}
//https://www.tuicool.com/articles/3Unmiir