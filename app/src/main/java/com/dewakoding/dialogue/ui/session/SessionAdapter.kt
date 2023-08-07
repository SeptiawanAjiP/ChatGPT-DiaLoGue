package com.dewakoding.dialogue.ui.session

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.dewakoding.dialogue.R
import com.dewakoding.dialogue.database.entity.Session
import com.dewakoding.dialogue.databinding.ItemSessionBinding


/**

Created by
name : Septiawan Aji Pradana
email : septiawanajipradana@gmail.com
website : dewakoding.com

 **/
class SessionAdapter(private val context: Context, val listener: NotesClickListener): RecyclerView.Adapter<SessionAdapter.ViewHolder>() {
    private val sessionList = ArrayList<Session>()
    private val fullList = ArrayList<Session>()

    class ViewHolder(val binding: ItemSessionBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder (
        ItemSessionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount() = sessionList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val session = sessionList.get(position)
        holder.binding.cvItem.setCardBackgroundColor(holder.itemView.resources.getColor(randomColor()))

        holder.binding.tvTitle.setText(sessionList.get(position).title)
        holder.binding.tvSubTitle.setText(sessionList.get(position).description)

        holder.binding.cvItem.setOnClickListener {
            listener.onClicked(session)
        }

        holder.binding.cvItem.setOnLongClickListener {
            listener.onLongClicked(session, holder.binding.cvItem)
            true
        }

    }

    fun updateList(newList: List<Session>) {
        fullList.clear()
        fullList.addAll(newList)

        sessionList.clear()
        sessionList.addAll(fullList)
        notifyDataSetChanged()
    }

    fun randomColor(): Int {
        val list = ArrayList<Int>()
        list.add(R.color.color_1)
        list.add(R.color.color_2)
        list.add(R.color.color_3)
        list.add(R.color.color_4)
        list.add(R.color.color_5)
        list.add(R.color.color_6)
        return list.random()
    }

    interface NotesClickListener {
        fun onClicked(session: Session)
        fun onLongClicked(session: Session, cardView: CardView)
    }
}