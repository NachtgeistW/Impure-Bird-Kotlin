/*
 * Copyright (c) 2020/5/6 (YYYY/MM/DD)
 * Created by NachtgeistW.
 * This file is used to:
 *
 */

package com.nachtgeistw.impurebirdkt.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.nachtgeistw.impurebirdkt.R
import com.nachtgeistw.impurebirdkt.util.Util
import kotlinx.android.synthetic.main.activity_send_tweet.*

class SendTweetActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_tweet)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)
    }

    //把按钮（菜单）设置到标题栏
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.send_tweet_main, menu)
        return true
    }

    //监听菜单按钮
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            // Send key
            R.id.action_send -> {
                if (edit_text_tweet_info.text.isEmpty()) {
                    Util.showToastShort(getString(R.string.warning_tweet_content_is_empty))
                    return super.onOptionsItemSelected(item)
                } else {
                    val tweetContent: String = edit_text_tweet_info.text.toString()
                    val intent = Intent()
                    intent.putExtra("tweet_content", tweetContent)
                    //把获取到的推文内容传回上级Activity
                    setResult(Activity.RESULT_OK, intent)
                    finish()
                }
            }
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
}
