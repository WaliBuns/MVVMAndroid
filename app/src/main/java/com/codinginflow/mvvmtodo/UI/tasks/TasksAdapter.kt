package com.codinginflow.mvvmtodo.UI.tasks

import android.text.style.TabStopSpan
import android.view.LayoutInflater
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.codinginflow.mvvmtodo.data.Task
import com.codinginflow.mvvmtodo.databinding.ItemTaskBinding
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil


//use list adapter instead of recyclerview because we get a new list, rather than a single event change
class TasksAdapter(private val listener: OnItemClickListener): ListAdapter<Task, TasksAdapter.TasksViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksViewHolder {
        val binding =  ItemTaskBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return TasksViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TasksViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class TasksViewHolder(private val binding: ItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                root.setOnClickListener {
                    val position = adapterPosition
                    if(position != RecyclerView.NO_POSITION){
                        val task = getItem(position)
                        listener.onItemClick(task)
                    }
                }
                checkBoxCompleted.setOnClickListener {
                    val position = adapterPosition
                    if(position != RecyclerView.NO_POSITION){
                        val task = getItem(position)
                        listener.onCheckBoxClick(task,checkBoxCompleted.isChecked)
                    }
                }
            }
        }

        fun bind(task: Task) {
            //binding.apply lets you write without having to write binding over and over again
            binding.apply {
                checkBoxCompleted.isChecked = task.completed
                textViewName.text = task.name
                textViewName.paint.isStrikeThruText = task.completed
                labelPriority.isVisible = task.important


            }
        }


    }


    interface OnItemClickListener{
        fun onItemClick(task:Task)
        fun onCheckBoxClick(task:Task, isChecked: Boolean)
    }
    //List adapter needs DFiffutil to tell the difference between 2 list
    class DiffCallback: DiffUtil.ItemCallback<Task>(){
        override fun areItemsTheSame(oldItem: Task, newItem: Task)=
            //this callback knows if we have to move the item around, as the new list can have the same items in a different location
            oldItem.id == newItem.id


        override fun areContentsTheSame(oldItem: Task, newItem: Task)=
            //because we use a data class, we invoke equals sign
            oldItem == newItem
    }
}

