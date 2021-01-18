package com.pwszproducts.myapplication.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.pwszproducts.myapplication.R
import com.pwszproducts.myapplication.ui.list.ListActivity
import org.json.JSONObject

data class resultLogin (var token: String)
data class resultUser (
        var id: Int,
        var name: String,
        var email: String,
        var email_verified_at: String?,
        var created_at: String,
        var updated_at: String
    )

data class Errors (
        var email: List<String>? = null,
        var password: List<String>? = null
    )

data class resultErrorMessage(
        var message: String,
        var errors: Errors? = null
    )

class LoginActivity: AppCompatActivity() {

    private val REGISTERED = 1
    private lateinit var loginViewModel: LoginActivityViewModel

    private lateinit var emailText: TextView
    private lateinit var emailError: TextView
    private lateinit var passwordText: TextView
    private lateinit var passwordError: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        emailText = findViewById<Button>(R.id.email)
        emailError = findViewById<Button>(R.id.email_error)
        passwordText = findViewById<Button>(R.id.password)
        passwordError = findViewById<Button>(R.id.password_error)

        val buttonLogin = findViewById<Button>(R.id.sign_in)
        val buttonRegister = findViewById<Button>(R.id.register_click)

        buttonLogin.isEnabled = true

        buttonRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivityForResult(intent, REGISTERED)
        }

        buttonLogin.setOnClickListener {
            loginToSystem()
        }
    }

    fun loginToSystem() {
        val url = "https://list.kamilcraft.com/api/login"

        val params: MutableMap<String, String> = HashMap()
        params["email"] = emailText.text.toString()
        params["password"] = passwordText.text.toString()

        val jsonObject = JSONObject(params as Map<*, *>)

        val stringRequest = object: JsonObjectRequest(Method.POST, url, jsonObject,
                { response ->
                    emailError.text = ""
                    passwordError.text = ""

                    val gson = Gson()
                    val myToken = gson.fromJson(response.toString(), resultLogin::class.java)

                    getUser(myToken.token)
                },
                {
                    val statusCode = it.networkResponse.statusCode
                    val response = String(it.networkResponse.data, Charsets.UTF_8)

                    val result = Gson().fromJson(response, resultErrorMessage::class.java)

                    val errors = result.errors
                    if(errors != null) {
                        val email = errors.email
                        val password = errors.password

                        if(email != null)
                            emailError.text = email.first().toString()
                        else
                            emailError.text = ""

                        if(password != null)
                            passwordError.text = password.first().toString()
                        else
                            passwordError.text = ""
                    }

                    Toast.makeText(this,
                            result.message,
                            Toast.LENGTH_SHORT).show()
                }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Accept"] = "application/json"
                headers["Content-Type"] = "application/json"
                return headers
            }
        }

        Volley.newRequestQueue(this).add(stringRequest)
    }

    fun getUser(token: String) {
        val url = "https://list.kamilcraft.com/api/user"

        val stringRequest = object: JsonObjectRequest(Method.GET, url, null,
                { response ->
                    val gson = Gson()
                    val user = gson.fromJson(response.toString(), resultUser::class.java)

                    Toast.makeText(this,
                            "Witaj ${user.name}!",
                            Toast.LENGTH_SHORT).show()
                    openListActivity(token)
                },
                {
                    Toast.makeText(this,
                            "Wystąpił błąd podczas logowania! Spróbuj ponownie później",
                            Toast.LENGTH_SHORT).show()
                }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Authorization"] = "Bearer $token"
                headers["Accept"] = "application/json"
                headers["Content-Type"] = "application/json"
                return headers
            }
        }

        Volley.newRequestQueue(this).add(stringRequest)
    }

    fun openListActivity(token: String) {
        val intent = Intent(this, ListActivity::class.java)
        intent.putExtra("token", token)
        startActivity(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REGISTERED && resultCode == RESULT_OK) {
            Toast.makeText(this, "Witaj ${data?.getStringExtra("name")}!",
                    Toast.LENGTH_SHORT).show()
            intent.getStringExtra("token")?.let { openListActivity(it) }
        }
    }
}