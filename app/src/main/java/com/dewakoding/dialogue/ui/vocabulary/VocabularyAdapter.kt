package com.dewakoding.dialogue.ui.vocabulary

import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dewakoding.dialogue.database.entity.Session
import com.dewakoding.dialogue.database.entity.Vocabulary
import com.dewakoding.dialogue.databinding.ItemVocabularyBinding
import com.dewakoding.dialogue.listener.OnItemClickListener


/**

Created by
name : Septiawan Aji Pradana
email : septiawanajipradana@gmail.com
website : dewakoding.com

 **/
class VocabularyAdapter(val onItemClickListener: OnItemClickListener): RecyclerView.Adapter<VocabularyAdapter.ViewHolder>() {
    private val vocabList = ArrayList<Vocabulary>()
    class ViewHolder(val binding: ItemVocabularyBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemVocabularyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount() = vocabList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val vocab = vocabList.get(position)
        if (!vocab.example.isNullOrEmpty()) {
            holder.binding.imgExample.visibility = View.GONE
        } else {
            holder.binding.imgExample.visibility = View.VISIBLE
        }

        holder.binding.imgExample.setOnClickListener {
            onItemClickListener.onClick(vocab)
        }

        holder.binding.tvBahasa.setText(vocab.bahasa)
        holder.binding.tvEnglish.setText(vocab.english)
        holder.binding.tvExample.setText(vocab.example)
    }

    fun updateList(newVocabList: List<Vocabulary>) {
        vocabList.clear()
        vocabList.addAll(newVocabList)
        notifyDataSetChanged()
    }


}