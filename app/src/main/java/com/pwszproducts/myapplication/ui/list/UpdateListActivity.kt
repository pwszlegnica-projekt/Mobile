package com.pwszproducts.myapplication.ui.list

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.pwszproducts.myapplication.R

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

    fun update() {
        if(listName.text.length in 3..30) {
            val id: Int = intent.getIntExtra("id", 0)
            val intent = Intent()
            if (id > 0) {
                intent.putExtra("id", id)
                intent.putExtra("name", listName.text.toString())
                intent.putExtra("submit_type", "update")
            } else {
                Toast.makeText(this, "Coś poszło nie tak!", Toast.LENGTH_LONG).show()
            }
            sendIntent(intent)
        } else {
            Toast.makeText(this,
                "Tekst musi posiadać przynajmniej 3 znaki i maksymalnie 30"
                , Toast.LENGTH_SHORT).show()
        }
    }

    fun delete() {
        val id: Int = intent.getIntExtra("id", 0)
        val intent = Intent()
        if(id > 0) {
            intent.putExtra("id", id)
            intent.putExtra("submit_type", "delete")
        } else {
            Toast.makeText(this, "Coś poszło nie tak!", Toast.LENGTH_LONG).show()
        }
        sendIntent(intent)
    }

    fun sendIntent(intent: Intent) {
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}
