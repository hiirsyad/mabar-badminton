package com.lazday.mainbadminton.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lazday.mainbadminton.database.TaskModel
import com.lazday.mainbadminton.databinding.AdapterPlayerBinding
import com.lazday.mainbadminton.util.dateTimeToString

class PlayerAdapter(
    var items: ArrayList<TaskModel>,
    var listener: AdapterListener
): RecyclerView.Adapter<PlayerAdapter.ViewHolder>() {

    class ViewHolder(val binding: AdapterPlayerBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder (
        AdapterPlayerBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.binding.textTask.text = item.name
        holder.binding.textTask.paintFlags = holder.binding.textTask.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        holder.binding.textDate.text = dateTimeToString( item.updated )
//        holder.binding.textDate.text = "grade ${item.grade.toUpperCase()}"
        holder.binding.textGrade.text = item.grade.toUpperCase()
        holder.binding.imageCheck.setOnClickListener {
            listener.onPlay(item)
        }
        holder.itemView.setOnClickListener {
            listener.onDetail(item)
        }
    }

    fun addList(list: List<TaskModel>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    interface AdapterListener {
        fun onPlay(taskModel: TaskModel)
        fun onDetail(taskModel: TaskModel)
    }
}