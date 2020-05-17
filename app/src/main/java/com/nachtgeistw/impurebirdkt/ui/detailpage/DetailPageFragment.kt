/*
 * Copyright (c) 2020/5/14 (YYYY/MM/DD)
 * Created by NachtgeistW.
 * This file is used to:
 *
 */

package com.nachtgeistw.impurebirdkt.ui.detailpage

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.nachtgeistw.impurebirdkt.R
import com.nachtgeistw.impurebirdkt.activity.HomepageActivity
import com.nachtgeistw.impurebirdkt.util.Util

class DetailPageFragment : Fragment() {

    companion object {
        fun newInstance() =
            DetailPageFragment()
    }

    private lateinit var viewModel: DetailPageViewModel

    lateinit var pic1: ImageView
    lateinit var pic2: ImageView
    lateinit var pic3: ImageView
    lateinit var pic4: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val intent: Intent? = activity?.intent

        return if (intent == null) {
            Util.showToastLong("Can't load tweet now. Please try again later.")
            startActivity(Intent(context, HomepageActivity::class.java))
            inflater.inflate(R.layout.tweet_item, container, false)
        } else {
            val picNum = intent.getIntExtra("pic_num", 0)
            val root = when (picNum) {
                1 -> inflater.inflate(R.layout.tweet_item1, container, false)
                2 -> inflater.inflate(R.layout.tweet_item2, container, false)
                else -> inflater.inflate(R.layout.tweet_item, container, false)
            }

            val userName: TextView = root.findViewById(R.id.tweet_user_name)
            val userText: TextView = root.findViewById(R.id.tweet_user_text)
            val userAvatar: ImageView = root.findViewById(R.id.tweet_user_avatar)
            userName.text = intent.getStringExtra("user_name")
            userText.text = intent.getStringExtra("user_text")
            Glide.with(userAvatar.context)
                .load(intent.getStringExtra("user_avatar"))
                .into(userAvatar)
            when (picNum) {
                1 -> {
                    pic1 = root.findViewById(R.id.tweet_pic_1)
                    val url = intent.getStringExtra("pic0")
                    Glide.with(pic1.context).load(url).into(pic1)
                }
                2 -> {
                    pic1 = root.findViewById(R.id.tweet_pic_1)
                    pic2 = root.findViewById(R.id.tweet_pic_2)
                    val url1 = intent.getStringExtra("pic0")
                    val url2 = intent.getStringExtra("pic1")
                    Glide.with(pic1.context).load(url1).into(pic1)
                    Glide.with(pic2.context).load(url2).into(pic2)
                }
                else -> {
                }
            }
            //        detailViewModel.text.observe(viewLifecycleOwner, Observer {
            //            textView.text = it
            //        })
            root //return
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetailPageViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
