package com.dewakoding.dialogue.ui.chat

import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dewakoding.dialogue.R
import com.dewakoding.dialogue.data.entity.Chat
import com.dewakoding.dialogue.data.entity.Session
import com.dewakoding.dialogue.databinding.ActivityChatBinding
import com.dewakoding.dialogue.listener.OnItemClickListener
import com.dewakoding.dialogue.ui.vocabulary.VocabularyActivity
import com.dewakoding.dialogue.util.CommonCons
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale
import java.util.Objects


@AndroidEntryPoint
class ChatActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private val binding by lazy { ActivityChatBinding.inflate(layoutInflater)}
    private val REQUEST_CODE_SPEECH_INPUT = 1
    private val viewModel: ChatViewModel by viewModels()
    lateinit var adapter: ChatAdapter
    private lateinit var session: Session
    lateinit var listChat: List<Chat>
    private lateinit var textToSpeec: TextToSpeech
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        textToSpeec = TextToSpeech(this, this)
        adapter = ChatAdapter(object: OnItemClickListener{
            override fun onClick(any: Any) {
                if (any is Chat) {
                    speech(any.content)
                }
            }

            override fun onLongClick(any: Any) {

            }
        })
        binding.rvChat.adapter = adapter

        binding.imgBack.setOnClickListener {
            finish()
        }
        session = intent.getSerializableExtra("session") as Session

        binding.tvTitle.text = session.title

        viewModel.getAllChat(session.id!!).observe(this) { list ->
            list.let {
                listChat = list
                adapter.updateList(list)
                if (list.isNotEmpty()) {
                    if (!list.last().isFromUser) {
                        if (viewModel.isAutoSpeech())
                            speech((list.last().content))
                    }
                }
                if (list.size == 0) {
                    viewModel.postToGPT(null, "", session.id!!, true)
                }
                if (list.size > 0) {
                    binding.rvChat.smoothScrollToPosition(list.size - 1)
                }
            }
        }
        binding.imgRecord.setOnClickListener {
           record()
        }

        binding.btnSend.setOnClickListener {
            if (!binding.etMessage.text.isNullOrEmpty()) {
                viewModel.postToGPT(listChat.takeLast(5), binding.etMessage.text.toString(), session.id!!, false)
                viewModel.insert(binding.etMessage.text.toString(), session.id!!, true)
                binding.etMessage.setText("")
            } else {
                Toast.makeText(applicationContext, "Please, write something", Toast.LENGTH_SHORT).show()
            }
        }

        binding.imgType.setOnClickListener {
            val popupMenu = PopupMenu(this, binding.imgType)
            popupMenu.menuInflater.inflate(R.menu.menu_chat,popupMenu.menu)
            popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                when(item.itemId) {
                    R.id.item_writing ->
                        addPrompAsMessage(CommonCons.PROMPT_WRITING)
                    R.id.item_grammar ->
                        addPrompAsMessage(CommonCons.PROMPT_GRAMMAR)
                    R.id.item_reading ->
                        addPrompAsMessage(CommonCons.PROMPT_READING)
                    R.id.item_vocabulary ->
                        vocabulary()
                }
                true
            })
            popupMenu.show()
        }
    }

    fun addPrompAsMessage(str: String) {
        binding.etMessage.setText(str)
    }

    fun speech(str: String) {
        textToSpeec.setSpeechRate(1f)
        textToSpeec!!.speak(str, TextToSpeech.QUEUE_FLUSH, null,"")
    }

    fun vocabulary() {
        val intent = Intent(applicationContext, VocabularyActivity::class.java)
        startActivity(intent)
    }

    fun record() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE,
            "en-US"
        )
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to text")
        intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, 3000);
        intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_POSSIBLY_COMPLETE_SILENCE_LENGTH_MILLIS, 3000);
        intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS, 3500);
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
                val str = binding.etMessage.text.toString()
                binding.etMessage.setText(str + " " + Objects.requireNonNull(res)[0])
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