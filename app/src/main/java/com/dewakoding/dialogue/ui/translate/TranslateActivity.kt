package com.dewakoding.dialogue.ui.translate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dewakoding.dialogue.R
import com.dewakoding.dialogue.databinding.ActivityTranslateBinding

class TranslateActivity : AppCompatActivity() {
    private val binding by lazy { ActivityTranslateBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.webview.settings.javaScriptEnabled = true
        binding.webview.loadUrl("https://translate.google.com/")
    }
}