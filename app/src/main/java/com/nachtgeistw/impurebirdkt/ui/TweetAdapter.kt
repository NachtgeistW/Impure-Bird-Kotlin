/*
 * Copyright (c) 2020/5/6 (YYYY/MM/DD)
 * Created by NachtgeistW.
 * This file is used to:
 *
 */

package com.nachtgeistw.impurebirdkt.ui

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nachtgeistw.impurebirdkt.R
import com.nachtgeistw.impurebirdkt.activity.DetailPageActivity
import com.nachtgeistw.impurebirdkt.databinding.*
import twitter4j.Status
import twitter4j.TwitterException

class TweetAdapter(private var tweetList: List<Status>) :
    RecyclerView.Adapter<TweetAdapter.TweetViewHolder>() {
    private var PIC_NONE = 0
    private var PIC_ONE = 1
    private var PIC_TWO = 2
    private var PIC_THREE = 3
    private var PIC_FOUR = 4

    private lateinit var inflater: LayoutInflater
    private lateinit var binding: TweetItemBinding
    private lateinit var binding1: TweetItem1Binding
    private lateinit var binding2: TweetItem2Binding
    private lateinit var binding3: TweetItem3Binding
    private lateinit var binding4: TweetItem4Binding

    inner class TweetViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val userName: TextView = itemView.findViewById(R.id.tweet_user_name)
        val userText: TextView = itemView.findViewById(R.id.tweet_user_text)
        val userAvatar: ImageView = itemView.findViewById(R.id.tweet_user_avatar)
        lateinit var pic1: ImageView
        lateinit var pic2: ImageView
        lateinit var pic3: ImageView
        lateinit var pic4: ImageView
        val like: ImageView = itemView.findViewById(R.id.not_like)
        val rt: ImageView = itemView.findViewById(R.id.not_rt)
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
                binding1 = TweetItem1Binding.inflate(inflater)
                val holder = TweetViewHolder(binding1.root)
                holder.pic1 = binding1.tweetPic1
                holder.itemView.setOnClickListener {
                    startDetailPageActivity(holder, parent.context)
                }
                return holder
            }
            PIC_TWO -> {
                binding2 = TweetItem2Binding.inflate(inflater)
                val holder = TweetViewHolder(binding2.root)
                holder.pic1 = binding2.tweetPic1
                holder.pic2 = binding2.tweetPic2
                holder.itemView.setOnClickListener {
                    startDetailPageActivity(holder, parent.context)
                }
                return holder
            }
            PIC_THREE -> {
                binding3 = TweetItem3Binding.inflate(inflater)
                val holder = TweetViewHolder(binding3.root)
                holder.pic1 = binding3.tweetPic1
                holder.pic2 = binding3.tweetPic2
                holder.pic3 = binding3.tweetPic3
                holder.itemView.setOnClickListener {
                    startDetailPageActivity(holder, parent.context)
                }
                return holder
            }
            PIC_FOUR -> {
                binding4 = TweetItem4Binding.inflate(inflater)
                val holder = TweetViewHolder(binding4.root)
                holder.pic1 = binding4.tweetPic1
                holder.pic2 = binding4.tweetPic2
                holder.pic3 = binding4.tweetPic3
                holder.pic4 = binding4.tweetPic4
                holder.itemView.setOnClickListener {
                    startDetailPageActivity(holder, parent.context)
                }
                return holder
            }
            else -> {
                binding = TweetItemBinding.inflate(inflater)
                val holder = TweetViewHolder(binding.root)
                holder.itemView.setOnClickListener {
                    startDetailPageActivity(holder, parent.context)
                }
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
                    Glide.with(holder.pic1.context)
                        .load(tweet.mediaEntities[0].mediaURLHttps)
                        .into(holder.pic1)
                }
                2 -> {
                    Glide.with(holder.pic1.context)
                        .load(tweet.mediaEntities[0].mediaURLHttps)
                        .into(holder.pic1)
                    Glide.with(holder.pic2.context)
                        .load(tweet.mediaEntities[1].mediaURLHttps)
                        .into(holder.pic2)
                }
                3 -> {
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

    private fun startDetailPageActivity(holder: TweetViewHolder, context: Context) {
        val position = holder.adapterPosition
        val tweet = tweetList[position]
        val intent = Intent(context, DetailPageActivity::class.java)
        var picNum = 0

        //detail data of tweet
        intent.apply {
            putExtra("user_name", tweet.user.name)
            putExtra("user_text", tweet.text)
            putExtra("user_avatar", tweet.user.get400x400ProfileImageURLHttps())
            putExtra("is_rt_by_me", tweet.isRetweetedByMe)
            putExtra("is_like_by_me", tweet.isFavorited)
            putExtra("tweet_id", tweet.id)
            if (tweet.mediaEntities != null) {
                for (media in tweet.mediaEntities) {
                    putExtra("pic$picNum", media.mediaURLHttps)
                    picNum++
                }
                putExtra("pic_num", picNum) //把图片数也传过去
            }
        }

        context.startActivity(intent)
    }

}
//https://www.tuicool.com/articles/3Unmiir