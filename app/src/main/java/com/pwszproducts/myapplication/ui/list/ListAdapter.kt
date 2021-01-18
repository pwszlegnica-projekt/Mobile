package com.pwszproducts.myapplication.ui.list

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pwszproducts.myapplication.R
import com.pwszproducts.myapplication.data.model.ListItem
import com.pwszproducts.myapplication.ui.products.ProductListActivity

class ListAdapter(private val addedList: MutableList<ListItem>) :
    RecyclerView.Adapter<ListAdapter.ExampleViewHolder>() {

    lateinit var context: Context
    private val UPDATE_LIST = 2
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

    fun removeWithList(id: Int) {
        val itemList = list.find { it.id == id }
        list.remove(itemList)

        notifyDataSetChanged()
        notifyItemInserted(list.size - 1)
    }

    fun updateInList(id: Int, name: String) {
        list.filter { it.id == id }.forEach {
            it.name = name
        }
        notifyDataSetChanged()
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ExampleViewHolder, position: Int) {
        val currentItem = list[position]

        holder.listName.text = currentItem.name

        holder.itemView.setOnClickListener {
            val intent = Intent(context, ProductListActivity::class.java)
            intent.putExtra("id", currentItem.id)
            intent.putExtra("title", currentItem.name)
            (context as Activity).startActivity(intent)
        }

        holder.itemView.findViewById<Button>(R.id.update_element).setOnClickListener {
            Log.d("ADAPTER", "Clicked edit")
            val intent = Intent(context, UpdateListActivity::class.java)
            intent.putExtra("id", currentItem.id)
            intent.putExtra("name", currentItem.name)
            (context as Activity).startActivityForResult(intent, UPDATE_LIST)
        }
    }
}