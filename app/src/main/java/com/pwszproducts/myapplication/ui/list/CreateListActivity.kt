package com.pwszproducts.myapplication.ui.list

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.pwszproducts.myapplication.R

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

    fun comebackToParent() {
        if(editText.text.length in 3..30) {
            val intent = Intent()
            intent.putExtra("name", editText.text.toString())
            setResult(Activity.RESULT_OK, intent)
            finish()
        } else {
            Toast.makeText(this,
                "Tekst musi posiadać przynajmniej 3 znaki i maksymalnie 30"
                , Toast.LENGTH_SHORT).show()
        }
    }

}