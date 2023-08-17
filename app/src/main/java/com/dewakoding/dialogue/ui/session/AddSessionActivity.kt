package com.dewakoding.dialogue.ui.session

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.dewakoding.dialogue.database.entity.Session
import com.dewakoding.dialogue.databinding.ActivityAddSessionBinding
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class AddSessionActivity : AppCompatActivity() {
    private val binding by lazy { ActivityAddSessionBinding.inflate(layoutInflater) }
    private lateinit var session: Session
    var isUpdate = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        try {
            isUpdate = true
        } catch (e: Exception) {
            e.printStackTrace()
        }

        binding.imgCheck.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val noteDesc = binding.etDescription.text.toString()
            Toast.makeText(applicationContext, "Success created a session", Toast.LENGTH_SHORT).show()

            if (!title.isNullOrEmpty() && !noteDesc.isNullOrEmpty()) {
                session = Session(null, title, noteDesc, Date().time)
                val intent = Intent()
                intent.putExtra("session", session)
                setResult(Activity.RESULT_OK, intent)
                finish()

            }

        }

        binding.imgBack.setOnClickListener {
            finish()
        }
    }
}