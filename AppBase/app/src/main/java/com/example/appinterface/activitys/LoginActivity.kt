package com.example.appinterface.activitys

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.appinterface.Api.RetrofitInstance
import com.example.appinterface.R
import com.example.appinterface.model.LoginRequest
import com.example.appinterface.model.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val txtCorreo = findViewById<EditText>(R.id.txtCorreo)
        val txtPassword = findViewById<EditText>(R.id.txtPassword)

        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnRegistro = findViewById<TextView>(R.id.btnIrRegistro)

        btnLogin.setOnClickListener {

            val correo = txtCorreo.text.toString()
            val password = txtPassword.text.toString()

            if(correo.isEmpty() || password.isEmpty()){

                Toast.makeText(
                    this,
                    "Complete todos los campos",
                    Toast.LENGTH_LONG
                ).show()

                return@setOnClickListener
            }

            val login = LoginRequest(correo,password)

            RetrofitInstance.usuarioApi.login(login)
                .enqueue(object : Callback<LoginResponse>{

                    override fun onResponse(
                        call: Call<LoginResponse>,
                        response: Response<LoginResponse>
                    ) {

                        if(response.isSuccessful){

                            val token = response.body()?.token

                            val sharedPref =
                                getSharedPreferences("APP_PREFS", MODE_PRIVATE)

                            sharedPref.edit()
                                .putString("TOKEN",token)
                                .apply()

                            Toast.makeText(
                                this@LoginActivity,
                                "Login correcto",
                                Toast.LENGTH_LONG
                            ).show()

                            startActivity(
                                Intent(this@LoginActivity, ProductosActivity::class.java)
                            )

                            finish()

                        }else{

                            Toast.makeText(
                                this@LoginActivity,
                                "Credenciales incorrectas",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {

                        Toast.makeText(
                            this@LoginActivity,
                            "Error conexión",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                })
        }

        btnRegistro.setOnClickListener {

            startActivity(
                Intent(this, RegistroActivity::class.java)
            )
        }
    }
}