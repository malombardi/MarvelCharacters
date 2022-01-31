package com.malombardi.marvel.presentation.splash

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.vectordrawable.graphics.drawable.Animatable2Compat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.malombardi.marvel.R
import com.malombardi.marvel.databinding.ActivitySplashBinding
import com.malombardi.marvel.presentation.characters.CharactersActivity
import kotlinx.coroutines.flow.collect

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    private lateinit var viewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[SplashViewModel::class.java]
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
    }

    private fun setupView() {
        lifecycleScope.launchWhenCreated {
            viewModel.animationFinished.collect {
                if (it) {
                    val intent = Intent(this@SplashActivity, CharactersActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK and Intent.FLAG_ACTIVITY_CLEAR_TOP
                    }
                    startActivity(intent)
                    finish()
                }
            }
        }

        with(binding) {
            Glide.with(this@SplashActivity)
                .asGif()
                .load(R.drawable.marvel_intro)
                .listener(object : RequestListener<GifDrawable?> {
                    override fun onLoadFailed(
                        @Nullable e: GlideException?,
                        model: Any?,
                        target: com.bumptech.glide.request.target.Target<GifDrawable?>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        Log.d(TAG, "Resource not loaded")
                        return false
                    }

                    override fun onResourceReady(
                        resource: GifDrawable?,
                        model: Any?,
                        target: com.bumptech.glide.request.target.Target<GifDrawable?>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        Log.d(TAG, "Resource loaded")
                        resource?.toString()?.let { Log.d(TAG, it) }
                        resource?.setLoopCount(1)
                        resource?.registerAnimationCallback(object :
                            Animatable2Compat.AnimationCallback() {
                            override fun onAnimationEnd(drawable: Drawable) {
                                viewModel.notifyAnimationFinished()
                            }
                        })
                        return false
                    }
                }).into(splashImageView)
        }
    }

    companion object {
        const val TAG = "SplashActivity"
    }
}