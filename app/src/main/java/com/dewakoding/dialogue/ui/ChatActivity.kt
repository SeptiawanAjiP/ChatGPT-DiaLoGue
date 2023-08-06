package com.dewakoding.dialogue.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.dewakoding.dialogue.databinding.ActivityChatBinding
import com.dewakoding.dialogue.viewmodel.ChatViewModel
import java.util.Locale
import java.util.Objects

class ChatActivity : AppCompatActivity() {
    private val binding by lazy { ActivityChatBinding.inflate(layoutInflater)}
    private val REQUEST_CODE_SPEECH_INPUT = 1
    lateinit var viewModel : ChatViewModel
    lateinit var adapter: ChatAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        adapter = ChatAdapter()
        binding.RVContainer.adapter = adapter

        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(ChatViewModel::class.java)


        viewModel.allChats.observe(this) { list ->

            list.let {
                adapter.updateList(list)
                binding.RVContainer.smoothScrollToPosition(list.size - 1)
            }

        }
        binding.btnRecord.setOnClickListener {
           record()
        }

    }

    fun record() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE,
            Locale.getDefault()
        )
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to text")
        try {
            startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT)
        } catch (e: Exception) {
            Toast
                .makeText(
                    this@ChatActivity, " " + e.message,
                    Toast.LENGTH_SHORT
                )
                .show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_SPEECH_INPUT) {
            if (resultCode == RESULT_OK && data != null) {
                val res: ArrayList<String> =
                    data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS) as ArrayList<String>
                viewModel.postToGPT(Objects.requireNonNull(res)[0])
                viewModel.insert(Objects.requireNonNull(res)[0], true)
            }
        }
    }
}