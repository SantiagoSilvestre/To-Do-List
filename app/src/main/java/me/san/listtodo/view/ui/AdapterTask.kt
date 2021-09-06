package me.san.listtodo.view.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import me.san.listtodo.R
import me.san.listtodo.databinding.ItemTaskBinding
import me.san.listtodo.model.TaskModel
import me.san.listtodo.service.listener.TaskListener

class AdapterTask : RecyclerView.Adapter<AdapterTask.TaskViewHolder>() {

    private var mTaskList: List<TaskModel> = arrayListOf()
    private lateinit var mListener: TaskListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        val binding = ItemTaskBinding.inflate(inflate, parent, false)
        return TaskViewHolder(binding, mListener)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(mTaskList[position])
    }

    override fun getItemCount(): Int {
        return mTaskList.count()
    }

    fun updateTasks(list: List<TaskModel>) {
        mTaskList = list
        notifyDataSetChanged()
    }

    fun attachListener(listener: TaskListener) {
        mListener = listener
    }

    inner class TaskViewHolder(private val binding: ItemTaskBinding, private val listener: TaskListener) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: TaskModel) {
            binding.tvTitle.text = item.title
            binding.tvDate.text = "${item.date} ${item.hour}"
            binding.ivMore.setOnClickListener {
                showPopup(item)
            }
        }

        fun showPopup(item: TaskModel) {
            val ivMore = binding.ivMore
            val popupMenu = PopupMenu(ivMore.context, ivMore)
            popupMenu.menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.action_edit -> {
                        listener.onClick(item.id)
                    }
                    R.id.action_delete -> {
                        listener.onDelete(item.id)
                    }
                }
                return@setOnMenuItemClickListener true
            }
            popupMenu.show()
        }
    }
}