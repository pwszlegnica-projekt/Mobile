package com.pwszproducts.myapplication.ui.products

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.pwszproducts.myapplication.R

class UpdateProductActivity: AppCompatActivity() {

    private lateinit var name : TextView
    private lateinit var entity : TextView
    private lateinit var quantity : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_update)

        name = findViewById(R.id.product_name_text)
        entity = findViewById(R.id.entity_text)
        quantity = findViewById(R.id.quantity_number)

        if(intent.hasExtra("name"))
            name.text = intent.getStringExtra("name")

        if(intent.hasExtra("entity"))
            entity.text = intent.getStringExtra("entity")

        if(intent.hasExtra("quantity"))
            quantity.text = intent.getStringExtra("quantity")

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
        if(name.text.length in 3..30) {
            val id: Int = intent.getIntExtra("id", 0)
            val intent = Intent()
            if (id > 0) {
                intent.putExtra("id", id)
                intent.putExtra("name", name.text.toString())
                intent.putExtra("entity", entity.text.toString())
                intent.putExtra("quantity", quantity.text.toString().toInt())
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