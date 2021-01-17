package com.pwszproducts.myapplication.ui.list

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.pwszproducts.myapplication.R
import com.pwszproducts.myapplication.data.model.ListItem

class ListActivity : AppCompatActivity() {

    private lateinit var viewModel: ListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        val recyclerView = findViewById<RecyclerView>(R.id.recycle_view)
        viewModel = ViewModelProvider(this).get(ListViewModel::class.java)

        recyclerView.adapter = viewModel.getAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        val addListButton = findViewById<FloatingActionButton>(R.id.add_list)

        addListButton.setOnClickListener {
            viewModel.getAdapter().addToList(ListItem("Nowy tytuł"))
            openNewActivity()
        }
    }

    fun openNewActivity() {
        val intent = Intent(this, CreateListActivity::class.java)
        startActivity(intent)
    }

}