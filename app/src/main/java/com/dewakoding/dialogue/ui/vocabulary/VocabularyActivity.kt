package com.dewakoding.dialogue.ui.vocabulary

import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dewakoding.dialogue.R
import com.dewakoding.dialogue.data.entity.Vocabulary
import com.dewakoding.dialogue.databinding.ActivityVocabularyBinding
import com.dewakoding.dialogue.listener.OnItemClickListener
import com.dewakoding.dialogue.ui.vocabularybydate.VocabularyCountByDateActivity
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
    var param: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        param = intent.getStringExtra("PARAM")

        adapter = VocabularyAdapter(applicationContext, object: OnItemClickListener {
            override fun onClick(any: Any) {
                if (any is Vocabulary) {
                    viewModel.getVocabExample(any)
                }

            }

            override fun onLongClick(any: Any) {
                if (any is Vocabulary)
                    viewModel.delete(any)
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
}