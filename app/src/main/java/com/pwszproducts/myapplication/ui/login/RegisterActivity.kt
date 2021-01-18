package com.pwszproducts.myapplication.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.pwszproducts.myapplication.R

class RegisterActivity: AppCompatActivity() {

    private lateinit var submitButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        submitButton = findViewById(R.id.submit)

        submitButton.setOnClickListener {
            registerOK()
        }
    }

    fun registerOK() {
        val intent = Intent()
        intent.putExtra("token", "onfienufreinergni")
        intent.putExtra("name", "Kamil Niemczycki")
        setResult(RESULT_OK, intent)
        finish()
    }

}