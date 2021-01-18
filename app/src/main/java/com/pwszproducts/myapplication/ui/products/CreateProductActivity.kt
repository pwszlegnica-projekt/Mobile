package com.pwszproducts.myapplication.ui.products

import android.app.Activity
import android.content.Entity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.pwszproducts.myapplication.R

class CreateProductActivity: AppCompatActivity() {

    private lateinit var name : EditText
    private lateinit var entity : EditText
    private lateinit var quantity : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_create)

        name = findViewById(R.id.product_name_text)
        entity = findViewById(R.id.entity_text)
        quantity = findViewById(R.id.quantity_number)

        val buttonAdd = findViewById<Button>(R.id.add_submit)

        buttonAdd.setOnClickListener {
            comebackToParent()
        }
    }

    fun comebackToParent() {
        if(name.text.length in 3..30) {
            val intent = Intent()
            intent.putExtra("name", name.text.toString())
            intent.putExtra("entity", entity.text.toString())
            intent.putExtra("quantity", quantity.text.toString().toInt())
            intent.putExtra("checked", 0)
            setResult(Activity.RESULT_OK, intent)
            finish()
        } else {
            Toast.makeText(this,
                "Tekst musi posiadać przynajmniej 3 znaki i maksymalnie 30"
                , Toast.LENGTH_SHORT).show()
        }
    }

}