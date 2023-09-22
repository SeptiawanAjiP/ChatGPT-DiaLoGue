package com.dewakoding.dialogue.ui.vocabulary

import android.content.Intent
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.PopupMenu
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dewakoding.dialogue.R
import com.dewakoding.dialogue.data.entity.Vocabulary
import com.dewakoding.dialogue.databinding.ActivityVocabularyBinding
import com.dewakoding.dialogue.listener.OnVocabClickListener
import com.dewakoding.dialogue.ui.vocabularybydate.VocabularyCountByDateActivity
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale


/**

Created by
name : Septiawan Aji Pradana
email : septiawanajipradana@gmail.com
website : dewakoding.com

 **/
@AndroidEntryPoint
class VocabularyActivity: AppCompatActivity(), TextToSpeech.OnInitListener {
    private val binding by lazy { ActivityVocabularyBinding.inflate(layoutInflater) }
    private val viewModel: VocabularyViewModel by viewModels()
    lateinit var adapter: VocabularyAdapter
    var param: String? = null
    private lateinit var textToSpeec: TextToSpeech
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        textToSpeec = TextToSpeech(this, this)
        param = intent.getStringExtra("PARAM")

        adapter = VocabularyAdapter(applicationContext, object: OnVocabClickListener {
            override fun onDelete(any: Any) {
                if (any is Vocabulary)
                    viewModel.delete(any)
            }

            override fun onListen(any: Any) {
                if (any is Vocabulary)
                    speech(any.english)
            }

            override fun onSentencesExample(any: Any) {
                if (any is Vocabulary) {
                    viewModel.getVocabExample(any)
                }
            }

        })
        binding.rvVocabulary.adapter = adapter

        if (param == null) {
            binding.tvTitle.setText("All Vocabulary")
            viewModel.allVocab.observe(this) { list ->
                list.let {
                    adapter.updateList(it)
                }
            }
        } else {
            binding.tvTitle.setText("Vocabulary ($param)")
            binding.imgSetting.visibility = View.GONE
            viewModel.getVocabCountByDate(param!!).observe(this) {list ->
                list.let {
                    adapter.updateList(it)
                }

            }
        }

        binding.floatingActionButton.setOnClickListener {
            val intent = Intent(applicationContext, AddVocabularyActivity::class.java)
            startActivity(intent)
        }

        binding.imgBack.setOnClickListener {
            finish()
        }

        binding.imgSetting.setOnClickListener {
            val popupMenu = PopupMenu(this, binding.imgSetting)
            popupMenu.menuInflater.inflate(R.menu.menu_vocabulary, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                when(item.itemId) {
                    R.id.item_vocab_by_date->
                      openVocabCountByDate()
                }
                true
            })
            popupMenu.show()
        }

    }

    fun openVocabCountByDate() {
        val intent = Intent(applicationContext, VocabularyCountByDateActivity::class.java)
        startActivity(intent)
    }

    fun speech(str: String) {
        textToSpeec.setSpeechRate(1f)
        textToSpeec!!.speak(str, TextToSpeech.QUEUE_FLUSH, null,"")
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