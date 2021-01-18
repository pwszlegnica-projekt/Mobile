package com.pwszproducts.myapplication.ui.list

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.pwszproducts.myapplication.R
import com.pwszproducts.myapplication.data.model.ListItem
import com.pwszproducts.myapplication.data.model.Lists.ResultList
import com.pwszproducts.myapplication.data.model.Lists.ResultListDelete
import com.pwszproducts.myapplication.data.model.Lists.ResultListUpdate
import com.pwszproducts.myapplication.data.model.StaticUserData
import org.json.JSONObject

class UpdateListActivity: AppCompatActivity() {

    private lateinit var listName: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_update)

        listName = findViewById(R.id.list_name)

        if(intent.hasExtra("name"))
            listName.text = intent.getStringExtra("name")

        val updateButton = findViewById<Button>(R.id.update_submit)
        val deleteButton = findViewById<Button>(R.id.delete_submit)

        updateButton.setOnClickListener {
            update()
        }

        deleteButton.setOnClickListener {
            delete()
        }
    }

    fun sendUpdate() {
        val url = "https://list.kamilcraft.com/api/list/${intent.getIntExtra("id", 0)}"

        val params: MutableMap<String, String> = HashMap()
        params["name"] = String(listName.text.toString().toByteArray(), Charsets.UTF_8)

        val jsonObject = JSONObject(params as Map<*, *>)

        val stringRequest = object: JsonObjectRequest(
            Method.PUT, url, jsonObject,
            {
                val result = Gson().fromJson(it.toString(), ResultListUpdate::class.java)

                if(result.success) {

                    val id: Int = intent.getIntExtra("id", 0)
                    val intent = Intent()
                    intent.putExtra("id", id)
                    intent.putExtra("name", listName.text.toString())
                    intent.putExtra("submit_type", "update")
                    sendIntent(intent)

                } else {
                    Toast.makeText(this, "Coś poszło nie tak!", Toast.LENGTH_LONG).show()
                }
            },
            {
                val errorMessage = String(it.networkResponse.data, Charsets.UTF_8)
                Log.d("LIST", "Error: $errorMessage");
                Toast.makeText(this,
                    "Wystąpił błąd podczas przesyłania! ${errorMessage}",
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

    fun update() {
        if(listName.text.length in 3..30) {
            val id: Int = intent.getIntExtra("id", 0)
            if (id > 0) {
                sendUpdate()
            } else {
                Toast.makeText(this, "Coś poszło nie tak!", Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(this,
                "Tekst musi posiadać przynajmniej 3 znaki i maksymalnie 30"
                , Toast.LENGTH_SHORT).show()
        }
    }

    fun sendDelete() {
        val id: Int = intent.getIntExtra("id", 0)
        val url = "https://list.kamilcraft.com/api/list/$id"

        val params: MutableMap<String, String> = HashMap()
        params["name"] = String(listName.text.toString().toByteArray(), Charsets.UTF_8)

        val jsonObject = JSONObject(params as Map<*, *>)

        val stringRequest = object: JsonObjectRequest(
            Method.DELETE, url, jsonObject,
            {
                val result = Gson().fromJson(it.toString(), ResultListDelete::class.java)

                if(result.success) {

                    val intent = Intent()
                    intent.putExtra("id", id)
                    intent.putExtra("submit_type", "delete")
                    sendIntent(intent)

                } else {
                    Toast.makeText(this, "Coś poszło nie tak!", Toast.LENGTH_LONG).show()
                }
            },
            {
                val errorMessage = String(it.networkResponse.data, Charsets.UTF_8)
                Log.d("LIST", "Error: $errorMessage");
                Toast.makeText(this,
                    "Wystąpił błąd podczas przesyłania! ${errorMessage}",
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

    fun delete() {
        val id: Int = intent.getIntExtra("id", 0)
        if(id > 0) {
            sendDelete()
        } else {
            Toast.makeText(this, "Coś poszło nie tak!", Toast.LENGTH_LONG).show()
        }

    }

    fun sendIntent(intent: Intent) {
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}
