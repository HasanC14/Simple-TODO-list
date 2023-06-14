package com.hasanchodhury.curd.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hasanchodhury.curd.model.TaskListModel

class TaskListAdapter(tasklist: List<TaskListModel>, internal var context: Context) :
    RecyclerView.Adapter<TaskListAdapter.TaskViewHolder>() {
        inner  class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view){

        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}