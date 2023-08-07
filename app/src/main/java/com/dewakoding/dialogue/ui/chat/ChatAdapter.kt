package com.dewakoding.dialogue.ui.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dewakoding.dialogue.R
import com.dewakoding.dialogue.database.entity.Chat
import com.dewakoding.dialogue.databinding.ItemChatGptBinding
import com.dewakoding.dialogue.databinding.ItemChatUserBinding
import com.dewakoding.dialogue.listener.OnItemClickListener

class ChatAdapter(val onItemClickListener: OnItemClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val fullList = ArrayList<Chat>()

    inner class GptViewHolder(private val binding: ItemChatGptBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(chat: Chat) {
            binding.messageText.text = chat.content
            binding.btnSpeaker.setOnClickListener {
                onItemClickListener.onClick(chat)
            }
        }
    }

    inner class UserViewHolder(private val binding: ItemChatUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(chat: Chat) {
            binding.messageText.text = chat.content
            binding.btnSpeaker.setOnClickListener {
                onItemClickListener.onClick(chat)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == 0) {
            val binding = ItemChatGptBinding.inflate(inflater, parent, false)
            GptViewHolder(binding)
        } else {
            val binding = ItemChatUserBinding.inflate(inflater, parent, false)
            UserViewHolder(binding)
        }
    }

    override fun getItemCount(): Int {
        return fullList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val chat = fullList[position]
        if (holder is GptViewHolder) {
            holder.bind(chat)

        } else if (holder is UserViewHolder) {
            holder.bind(chat)
        }

    }

    fun updateList(newList: List<Chat>) {
        fullList.clear()
        fullList.addAll(newList)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return if (fullList[position].isFromUser) { // Gpt
            1
        } else {
            0
        }
    }
}
