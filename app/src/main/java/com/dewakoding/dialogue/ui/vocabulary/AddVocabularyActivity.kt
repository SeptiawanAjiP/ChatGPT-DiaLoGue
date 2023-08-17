package com.dewakoding.dialogue.ui.vocabulary

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dewakoding.dialogue.R
import com.dewakoding.dialogue.database.entity.Vocabulary
import com.dewakoding.dialogue.databinding.ActivityAddVocabularyBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.Date


/**

Created by
name : Septiawan Aji Pradana
email : septiawanajipradana@gmail.com
website : dewakoding.com

 **/
@AndroidEntryPoint
class AddVocabularyActivity: AppCompatActivity() {
    private val binding by lazy { ActivityAddVocabularyBinding.inflate(layoutInflater) }
    private val viewModel: VocabularyViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.imgCheck.setOnClickListener {
            val english = binding.etEnglish.text.toString()
            val bahasa = binding.etBahasa.text.toString()
            if (!english.isNullOrEmpty() || !!bahasa.isNullOrEmpty()) {
                viewModel.insert(Vocabulary(null, english, bahasa, null, Date().time))
                finish()
            } else {
                Toast.makeText(applicationContext, resources.getString(R.string.fill_in_the_blank), Toast.LENGTH_SHORT).show()
            }
        }
    }
}