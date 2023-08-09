package com.dewakoding.dialogue.ui.session

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.dewakoding.dialogue.database.AppDatabase
import com.dewakoding.dialogue.database.entity.Session
import com.dewakoding.dialogue.databinding.ActivitySessionBinding
import com.dewakoding.dialogue.ui.setting.SettingActivity
import com.dewakoding.dialogue.ui.chat.ChatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SessionActivity : AppCompatActivity(), SessionAdapter.NotesClickListener {
    private val binding by lazy { ActivitySessionBinding.inflate(layoutInflater) }
    lateinit var viewModel: SessionViewModel
    lateinit var adapter: SessionAdapter
    private lateinit var databse: AppDatabase
    lateinit var selectedSession: Session
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initUi()

        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(SessionViewModel::class.java)

        viewModel.allNotes.observe(this) { list ->
            list.let {
                adapter.updateList(it)
            }
        }

        databse = AppDatabase.getDatabase(applicationContext)
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
            val intent = Intent(this, SettingActivity::class.java)
            getContent.launch(intent)
        }
    }

    override fun onClicked(session: Session) {
        val intent = Intent(applicationContext, ChatActivity::class.java)
        intent.putExtra("session", session)
        updateNote.launch(intent)
    }

    override fun onLongClicked(session: Session, cardView: CardView) {

    }
}