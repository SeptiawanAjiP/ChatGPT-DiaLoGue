package com.dewakoding.dialogue.ui.chat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.dewakoding.dialogue.database.entity.Chat
import com.dewakoding.dialogue.database.entity.Session
import com.dewakoding.dialogue.databinding.ActivityChatBinding
import com.dewakoding.dialogue.listener.OnItemClickListener
import java.util.Locale
import java.util.Objects

class ChatActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private val binding by lazy { ActivityChatBinding.inflate(layoutInflater)}
    private val REQUEST_CODE_SPEECH_INPUT = 1
    lateinit var viewModel : ChatViewModel
    lateinit var adapter: ChatAdapter
    private lateinit var session: Session
    lateinit var listChat: List<Chat>
    private lateinit var textToSpeec: TextToSpeech
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        textToSpeec = TextToSpeech(this, this)
        adapter = ChatAdapter(object: OnItemClickListener{
            override fun onClick(chat: Chat) {
                textToSpeec!!.speak(chat.content, TextToSpeech.QUEUE_FLUSH, null,"")
            }
        })
        binding.rvChat.adapter = adapter
        binding.imgBack.setOnClickListener {
            finish()
        }
        session = intent.getSerializableExtra("session") as Session

        binding.tvTitle.text = session.title

        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(
            ChatViewModel::class.java)


        viewModel.getAllChat(session.id!!).observe(this) { list ->
            list.let {
                listChat = list
                adapter.updateList(list)
                if (list.size == 0) {
                    viewModel.postToGPT(null, "", session.id!!, true)
                }
                if (list.size > 0) {
                    binding.rvChat.smoothScrollToPosition(list.size - 1)
                }
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
                viewModel.postToGPT(listChat, Objects.requireNonNull(res)[0], session.id!!, false)
                viewModel.insert(Objects.requireNonNull(res)[0], session.id!!, true)
            }
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = textToSpeec!!.setLanguage(Locale.US)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {

            } else {

            }
        }
    }
}