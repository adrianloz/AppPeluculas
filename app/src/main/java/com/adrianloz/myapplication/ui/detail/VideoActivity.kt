package com.adrianloz.myapplication.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.adrianloz.myapplication.databinding.ActivityVideoBinding
import com.adrianloz.myapplication.utils.Keys
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener









class VideoActivity : AppCompatActivity(){
    private lateinit var binding : ActivityVideoBinding
    var keyUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        intent.extras.let {
            keyUrl = it!!.get("url") as String
        }
        initPlayer()
        Log.e("keyy", Keys.BASE_URL_VIDEO_YOUTUBE + keyUrl!!)
    }

    private fun initPlayer() {
        binding.youtubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
           override fun onReady(youTubePlayer: YouTubePlayer) {
               val videoId = keyUrl
               youTubePlayer.loadVideo(videoId!!, 0f)
           }
       })
    }


}