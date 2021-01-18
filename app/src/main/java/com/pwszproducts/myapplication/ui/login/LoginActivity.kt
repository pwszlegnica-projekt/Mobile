package com.pwszproducts.myapplication.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.pwszproducts.myapplication.R
import com.pwszproducts.myapplication.ui.list.ListActivity
import com.pwszproducts.myapplication.ui.products.CreateProductActivity

class LoginActivity: AppCompatActivity() {

    private lateinit var loginViewModel: LoginActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val buttonLogin = findViewById<Button>(R.id.sign_in)
        val buttonRegister = findViewById<Button>(R.id.register_click)

        buttonLogin.isEnabled = true

        buttonRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        buttonLogin.setOnClickListener {
            openNewActivity()
        }

        /*val queue = Volley.newRequestQueue(this)
        val url = "https://www.google.com"

        // Request a string response from the provided URL.
        val stringRequest = object: StringRequest(Request.Method.GET, url,
            { response ->
                // Display the first 500 characters of the response string.
                //textView.text = "Response is: ${response.substring(0, 500)}"
            },
            { /*textView.text = "That didn't work!"*/ }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Content-Type"] = ""
                //headers["Authorization"] = "Basic <<YOUR BASE64 USER:PASS>>"
                return headers
            }
        }

        // Add the request to the RequestQueue.
        queue.add(stringRequest)*/
    }

    fun openNewActivity() {
        val intent = Intent(this, ListActivity::class.java)
        startActivity(intent)
    }
}