package com.lazday.mainbadminton.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lazday.mainbadminton.R
import com.lazday.mainbadminton.database.TaskModel
import com.lazday.mainbadminton.databinding.AdapterPlayingBinding
import com.lazday.mainbadminton.util.timeToString

class PlayingAdapter(
    private var items: ArrayList<TaskModel>,
    private var listener: AdapterListener,
    private var isHideGrade: Boolean = true
): RecyclerView.Adapter<PlayingAdapter.ViewHolder>() {

    class ViewHolder(val binding: AdapterPlayingBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder (
        AdapterPlayingBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.binding.textTask.apply {
            text = " ${item.name} "
            setTextColor(
                colorPosition(
                    view = holder.itemView,
                    position = position,
                    countPlay = item.countPlay
                )
            )
//            textStrike(
//                textView = this,
//                position = position,
//                countPlay = item.countPlay
//            )
        }
        holder.binding.textGrade.apply {
            visibility = if (isHideGrade) View.GONE else View.VISIBLE
            text = " grade ${item.grade.toUpperCase()}"
        }
        holder.binding.textDate.apply {
            visibility = if (isHideGrade) View.VISIBLE else View.GONE
            text = timeToString( item.updated )
        }
        holder.binding.imageCheck.setOnClickListener {
            listener.onPlay(item)
        }

        holder.binding.textCount.apply {
            text = "${item.countPlay}x"
            setTextColor(
                colorPosition(
                    view = holder.itemView,
                    position = position,
                    countPlay = item.countPlay
                )
            )
        }
        holder.binding.imgAdd.setOnClickListener {
            listener.onCountAdd(item)
        }
        holder.binding.imgMin.apply {
            visibility = if (item.countPlay > 0) View.VISIBLE else View.INVISIBLE
            setOnClickListener {
                listener.onCountMin(item)
            }
        }
    }

    interface AdapterListener {
        fun onPlay(taskModel: TaskModel)
        fun onCountAdd(taskModel: TaskModel)
        fun onCountMin(taskModel: TaskModel)
    }

    fun addList( list: List<TaskModel>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    fun hideGrade(isHide: Boolean) {
        isHideGrade = isHide
        notifyDataSetChanged()
    }

    private fun colorPosition(
        view: View,
        position: Int,
        countPlay: Int
    ): Int {
        return view.resources.getColor(
            if (position < 4 && countPlay != 0) R.color.pink_500
            else if (position < 8 && countPlay != 0) R.color.grey_500
            else R.color.black
        )
    }
}