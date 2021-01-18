package com.pwszproducts.myapplication.ui.list

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.pwszproducts.myapplication.R
import com.pwszproducts.myapplication.data.model.ListItem

class ListActivity : AppCompatActivity() {

    private val CREATE_LIST = 1
    private val UPDATE_LIST = 2

    private lateinit var viewModel: ListViewModel
    private lateinit var textMessage: TextView
    private var lastId: Int = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        val recyclerView = findViewById<RecyclerView>(R.id.recycle_view)

        textMessage = findViewById(R.id.list_message)

        viewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        viewModel.getAdapter().context = this

        if(viewModel.getAdapter().itemCount == 0) {
            textMessage.text = "Nie posiadasz żadnych elementów na liście"
        }

        recyclerView.adapter = viewModel.getAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        val addListButton = findViewById<FloatingActionButton>(R.id.add_list)

        addListButton.setOnClickListener {
            createListActivity()
        }

        Log.d("INTENT", "Is intent ${intent.hasExtra("data")}")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == CREATE_LIST && resultCode == Activity.RESULT_OK) {
            createList(data)
        } else if(requestCode == UPDATE_LIST) {
            updateList(data)
        }
    }

    fun createList(data: Intent?) {
        val result: String = data?.getStringExtra("name").toString()
        textMessage.text = ""

        viewModel.addToAdapter(ListItem(++lastId, result))
    }

    fun createListActivity() {
        val intent = Intent(this, CreateListActivity::class.java)
        startActivityForResult(intent, CREATE_LIST)
    }

    fun updateList(data: Intent?) {
        val id: Int? = data?.getIntExtra("id", 0)
        val submitType: String = data?.getStringExtra("submit_type").toString()
        if(id != null && id > 0){
            if(submitType == "update") {
                val name: String? = data.getStringExtra("name")
                if (name != null) {
                    Log.d("EDIT", "Edycja elementu o id: $id")
                    viewModel.getAdapter().updateInList(id, name)
                        Toast
                            .makeText(this, "Edycja listy zakończona!", Toast.LENGTH_SHORT)
                            .show()
                }
            } else if(submitType == "delete") {
                Log.d("DELETE", "Usunięcie elementu o id: $id")
                viewModel.getAdapter().removeWithList(id)
                Toast
                    .makeText(this, "Usunięto listę!", Toast.LENGTH_SHORT)
                    .show()
                if(viewModel.getAdapter().itemCount == 0) {
                    textMessage.text = "Nie posiadasz żadnych elementów na liście"
                }
            }
        }
    }

}