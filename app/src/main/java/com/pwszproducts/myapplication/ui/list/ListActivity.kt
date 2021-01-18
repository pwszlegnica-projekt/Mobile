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
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import com.pwszproducts.myapplication.R
import com.pwszproducts.myapplication.data.model.ListItem
import com.pwszproducts.myapplication.data.model.Lists.ResultList
import com.pwszproducts.myapplication.data.model.ResultUser
import com.pwszproducts.myapplication.data.model.StaticUserData
import java.nio.charset.Charset

class ListActivity : AppCompatActivity() {

    private val CREATE_LIST = 1
    private val UPDATE_LIST = 2

    private lateinit var viewModel: ListViewModel
    private lateinit var textMessage: TextView
    private var lastId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        val recyclerView = findViewById<RecyclerView>(R.id.recycle_view)

        textMessage = findViewById(R.id.list_message)

        viewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        viewModel.getAdapter().context = this

        downloadList()

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

    fun downloadList() {
        val url = "https://list.kamilcraft.com/api/lists"

        val stringRequest = object: JsonObjectRequest(
            Method.GET, url, null,
            {
                val gson = Gson()
                val user = gson.fromJson(it.toString(), ResultList::class.java)
                val success = user.success
                val lists = user.lists

                if(success && lists != null) {
                    Log.d("LIST", "Status TRUE");
                    for(elementList in lists) {
                        Log.d("LIST", "Loaded: ${elementList.id}");
                        viewModel.addToAdapter(ListItem(elementList.id, elementList.name))
                    }
                    if(viewModel.getAdapter().itemCount > 0) {
                        textMessage.text = ""
                    }
                } else {
                    Log.d("LIST", "Other status: $success, ${it.toString()}");
                }
            },
            {
                val errorMessage = String(it.networkResponse.data, Charsets.UTF_8)
                Log.d("LIST", "Error: $errorMessage");
                Toast.makeText(this,
                    "Wystąpił błąd podczas pobierania danych! ${errorMessage}",
                    Toast.LENGTH_LONG).show()
            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Authorization"] = "Bearer ${StaticUserData.token.token}"
                headers["Accept"] = "application/json"
                headers["Content-Type"] = "application/json"
                return headers
            }
        }

        Volley.newRequestQueue(this).add(stringRequest)
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
        val id: Int = data!!.getIntExtra("id", 0)
        val result: String = data.getStringExtra("name").toString()
        textMessage.text = ""

        viewModel.addToAdapter(ListItem(id, result))
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