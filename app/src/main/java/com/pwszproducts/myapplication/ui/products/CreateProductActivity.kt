package com.pwszproducts.myapplication.ui.products

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.pwszproducts.myapplication.R

class CreateProductActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_create)

        val buttonAdd = findViewById<Button>(R.id.add_submit)

        buttonAdd.setOnClickListener {
            openNewActivity()
        }
    }

    fun openNewActivity() {
        val intent = Intent(this, UpdateProductActivity::class.java)
        startActivity(intent)
    }

}