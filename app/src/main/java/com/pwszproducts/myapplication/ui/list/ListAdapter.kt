package com.pwszproducts.myapplication.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pwszproducts.myapplication.R
import com.pwszproducts.myapplication.data.model.ListItem

class ListAdapter(private val addedList: MutableList<ListItem>) : RecyclerView.Adapter<ListAdapter.ExampleViewHolder>() {

    private var list: MutableList<ListItem> = addedList

    class ExampleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val listName: TextView = itemView.findViewById(R.id.list_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_element, parent,
            false)

        return ExampleViewHolder(itemView)
    }

    fun addToList(exampleItem: ListItem) {
        list.add(0, exampleItem)

        notifyDataSetChanged()
        notifyItemInserted(list.size - 1)
    }

    fun removeWithList(index: Int) {
        list.removeAt(index)

        notifyDataSetChanged()
        notifyItemInserted(list.size - 1)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ExampleViewHolder, position: Int) {
        val currentItem = list[position]

        holder.listName.text = currentItem.name
    }
}