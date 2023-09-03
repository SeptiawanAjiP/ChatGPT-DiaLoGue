package com.dewakoding.dialogue.ui.vocabularybydate

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dewakoding.androiddatatable.data.Column
import com.dewakoding.androiddatatable.listener.OnWebViewComponentClickListener
import com.dewakoding.dialogue.data.entity.VocabularyCountByDate
import com.dewakoding.dialogue.databinding.ActivityVocabCountByDateBinding
import com.dewakoding.dialogue.ui.vocabulary.VocabularyActivity
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint


/**

Created by
name : Septiawan Aji Pradana
email : septiawanajipradana@gmail.com
website : dewakoding.com

 **/
@AndroidEntryPoint
class VocabularyCountByDateActivity: AppCompatActivity() {

    private val binding by lazy { ActivityVocabCountByDateBinding.inflate(layoutInflater) }
    private val viewModel: VocabularyCountByDateViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val column = ArrayList<Column>()
        column.add(Column("date", "Date"))
        column.add(Column("count", "Total"))

        val listData = ArrayList<VocabularyCountByDate>()
        viewModel.allVocabCountByDate.observe(this) { list ->
            list.forEach {
                listData.add(it)
            }
            binding.dtvTable.setTable(column, listData, true)
        }

        binding.dtvTable.setOnClickListener(object: OnWebViewComponentClickListener {
            override fun onRowClicked(dataStr: String) {
                val dataClicked = Gson().fromJson(dataStr, VocabularyCountByDate::class.java)

                val intent = Intent(applicationContext, VocabularyActivity::class.java)
                intent.putExtra("PARAM", dataClicked.date)
                startActivity(intent)

            }
        })

        binding.imgBack.setOnClickListener {
            finish()
        }






    }
}