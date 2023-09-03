package com.dewakoding.dialogue.ui.session

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.PopupMenu
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.dewakoding.dialogue.R
import com.dewakoding.dialogue.data.entity.Session
import com.dewakoding.dialogue.databinding.ActivitySessionBinding
import com.dewakoding.dialogue.ui.setting.SettingActivity
import com.dewakoding.dialogue.ui.chat.ChatActivity
import com.dewakoding.dialogue.ui.vocabulary.VocabularyActivity
import com.dewakoding.dialogue.ui.vocabularybydate.VocabularyCountByDateActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SessionActivity : AppCompatActivity(), SessionAdapter.NotesClickListener {
    private val binding by lazy { ActivitySessionBinding.inflate(layoutInflater) }
    private val viewModel: SessionViewModel by viewModels()
    lateinit var adapter: SessionAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initUi()

        viewModel.allNotes.observe(this) { list ->
            list.let {
                adapter.updateList(it)
            }
        }
    }

    private val updateNote = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val note = result.data?.getSerializableExtra("session") as Session
            if (note != null) {
                viewModel.updateNote(note)
            }
        }
    }

    private fun initUi() {
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = StaggeredGridLayoutManager(2, LinearLayout.VERTICAL)
        adapter = SessionAdapter(this, this)

        binding.recyclerView.adapter = adapter

        val getContent = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val note = result.data?.getSerializableExtra("session") as Session
                if (note != null) {
                    viewModel.insertNote(note)
                }
            }
        }

        binding.floatingActionButton.setOnClickListener {
            val intent = Intent(this, AddSessionActivity::class.java)
            getContent.launch(intent)
        }

        binding.imgSetting.setOnClickListener {
            val popupMenu = PopupMenu(this, binding.imgSetting)
            popupMenu.menuInflater.inflate(R.menu.menu_main_page, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                when(item.itemId) {
                    R.id.item_setting ->
                        openSetting()
                    R.id.item_vocabulary ->
                        openVocabulary()
                }
                true
            })
            popupMenu.show()
        }
    }

    fun openVocabulary() {
        val intent = Intent(applicationContext, VocabularyActivity::class.java)
//        startActivity(intent)
//        val intent = Intent(applicationContext, VocabularyCountByDateActivity::class.java)
        startActivity(intent)
    }

    fun openSetting() {
        val intent = Intent(this, SettingActivity::class.java)
        startActivity(intent)
    }

    override fun onClicked(session: Session) {
        val intent = Intent(applicationContext, ChatActivity::class.java)
        intent.putExtra("session", session)
        updateNote.launch(intent)
    }

    override fun onLongClicked(session: Session, cardView: CardView) {

    }
}