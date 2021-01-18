package com.pwszproducts.myapplication.ui.products

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.pwszproducts.myapplication.R
import com.pwszproducts.myapplication.data.model.ListItem
import com.pwszproducts.myapplication.data.model.ProductItem

class ProductListAdapter(private val list: MutableList<ProductItem>) :
    RecyclerView.Adapter<ProductListAdapter.ExampleViewHolder>() {

    lateinit var context: Context
    private val UPDATE_LIST = 2

    class ExampleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productName: TextView = itemView.findViewById(R.id.product_name)
        val quantityEntity: TextView = itemView.findViewById(R.id.quantity_entity)
        val checkBox: CheckBox = itemView.findViewById(R.id.checkBox)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.product_list_element, parent,
            false)

        return ExampleViewHolder(itemView)
    }

    fun addToList(item: ProductItem) {
        list.add(0, item)

        notifyDataSetChanged()
        notifyItemInserted(list.size - 1)
    }

    fun removeWithList(id: Int) {
        val itemList = list.find { it.id == id }
        list.remove(itemList)

        notifyDataSetChanged()
        notifyItemInserted(list.size - 1)
    }

    fun updateInList(id: Int, name: String, entity: String, quantity: Int, checked: Int) {
        list.filter { it.id == id }.forEach {
            it.name = name
            it.entity = entity
            it.quantity = quantity

        }
        notifyDataSetChanged()
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ExampleViewHolder, position: Int) {
        val currentItem = list[position]

        holder.productName.text = currentItem.name
        holder.quantityEntity.text = "${currentItem.quantity} ${currentItem.entity}"
        holder.checkBox.isChecked = currentItem.checked == 1

        holder.checkBox.setOnClickListener {
            val status = holder.checkBox.isChecked
            if(status) {
                list[position].checked = 1
                Toast.makeText(context, "Zaznaczono", Toast.LENGTH_SHORT).show()
            } else {
                list[position].checked = 0
                Toast.makeText(context, "Odznaczono", Toast.LENGTH_SHORT).show()
            }
        }

        holder.itemView.setOnClickListener {
            Log.d("ADAPTER", "Clicked update product")
            val intent = Intent(context, UpdateProductActivity::class.java)
            intent.putExtra("id", currentItem.id)
            intent.putExtra("name", currentItem.name)
            intent.putExtra("entity", currentItem.entity)
            intent.putExtra("quantity", currentItem.quantity.toString())
            (context as Activity).startActivityForResult(intent, UPDATE_LIST)
        }
    }
}