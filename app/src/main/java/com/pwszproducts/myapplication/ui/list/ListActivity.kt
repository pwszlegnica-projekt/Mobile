package com.pwszproducts.myapplication.ui.list

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.pwszproducts.myapplication.R
import com.pwszproducts.myapplication.data.model.ListItem

class ListActivity : AppCompatActivity() {

    private val REQUEST_FORM = 1
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
            openNewActivity()
        }

        Log.d("INTENT", "Is intent ${intent.hasExtra("data")}")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_FORM &&
            resultCode == Activity.RESULT_OK) {
            val result: String = data?.getStringExtra("name").toString();
            viewModel.getAdapter().addToList(ListItem(result))
        }
    }

    fun openNewActivity() {
        val intent = Intent(this, CreateListActivity::class.java)
        startActivityForResult(intent, REQUEST_FORM)
    }

}