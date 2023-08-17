package com.dewakoding.dialogue.ui.vocabulary

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dewakoding.dialogue.database.entity.Chat
import com.dewakoding.dialogue.database.entity.Vocabulary
import com.dewakoding.dialogue.databinding.ActivityVocabularyBinding
import com.dewakoding.dialogue.listener.OnItemClickListener
import com.dewakoding.dialogue.ui.session.SessionAdapter
import com.dewakoding.dialogue.ui.session.SessionViewModel
import dagger.hilt.android.AndroidEntryPoint


/**

Created by
name : Septiawan Aji Pradana
email : septiawanajipradana@gmail.com
website : dewakoding.com

 **/
@AndroidEntryPoint
class VocabularyActivity: AppCompatActivity() {
    private val binding by lazy { ActivityVocabularyBinding.inflate(layoutInflater) }
    private val viewModel: VocabularyViewModel by viewModels()
    lateinit var adapter: VocabularyAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        adapter = VocabularyAdapter(object: OnItemClickListener {
            override fun onClick(any: Any) {
                if (any is Vocabulary) {
                    viewModel.postToGPT(any)
                }

            }
        })
        binding.rvVocabulary.adapter = adapter

        viewModel.allVocab.observe(this) { list ->
            list.let {
                adapter.updateList(it)
            }

        }

        binding.floatingActionButton.setOnClickListener {
            val intent = Intent(applicationContext, AddVocabularyActivity::class.java)
            startActivity(intent)
        }
    }
}