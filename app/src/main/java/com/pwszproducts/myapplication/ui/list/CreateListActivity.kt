package com.pwszproducts.myapplication.ui.list

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.pwszproducts.myapplication.R
import com.pwszproducts.myapplication.data.model.Lists.ResultListCreate
import com.pwszproducts.myapplication.data.model.Lists.ResultListUpdate
import com.pwszproducts.myapplication.data.model.StaticUserData
import org.json.JSONObject

class CreateListActivity: AppCompatActivity() {

    private lateinit var editText : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_create)

        editText = findViewById(R.id.list_name_text)
        val addButton = findViewById<Button>(R.id.add_list)

        addButton.setOnClickListener {
            comebackToParent()
        }
    }

    fun sendCreate() {
        val url = "https://list.kamilcraft.com/api/lists"

        val params: MutableMap<String, String> = HashMap()
        params["name"] = String(editText.text.toString().toByteArray(), Charsets.UTF_8)

        val jsonObject = JSONObject(params as Map<*, *>)

        val stringRequest = object: JsonObjectRequest(
            Method.POST, url, jsonObject,
            {
                val result = Gson().fromJson(it.toString(), ResultListCreate::class.java)

                if(result.success) {

                    val intent = Intent()
                    intent.putExtra("id", result.listId)
                    intent.putExtra("name", editText.text.toString())
                    setResult(Activity.RESULT_OK, intent)
                    finish()

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

    fun comebackToParent() {
        if(editText.text.length in 3..30) {
            sendCreate()
        } else {
            Toast.makeText(this,
                "Tekst musi posiadać przynajmniej 3 znaki i maksymalnie 30"
                , Toast.LENGTH_SHORT).show()
        }
    }

}