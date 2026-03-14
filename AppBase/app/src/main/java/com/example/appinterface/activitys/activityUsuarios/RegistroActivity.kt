package com.example.appinterface.activitys.activityUsuarios

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.appinterface.Api.RetrofitInstance
import com.example.appinterface.R
import com.example.appinterface.model.Usuario
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        val primerNombre = findViewById<EditText>(R.id.txtPrimerNombre)
        val segundoNombre = findViewById<EditText>(R.id.txtSegundoNombre)
        val primerApellido = findViewById<EditText>(R.id.txtPrimerApellido)
        val segundoApellido = findViewById<EditText>(R.id.txtSegundoApellido)
        val documento = findViewById<EditText>(R.id.txtDocumento)
        val correo = findViewById<EditText>(R.id.txtCorreo)
        val direccion = findViewById<EditText>(R.id.txtDireccion)
        val password = findViewById<EditText>(R.id.txtPassword)

        val btnRegistrar = findViewById<Button>(R.id.btnRegistrar)

        btnRegistrar.setOnClickListener {

            if(
                primerNombre.text.isEmpty() ||
                primerApellido.text.isEmpty() ||
                documento.text.isEmpty() ||
                correo.text.isEmpty() ||
                password.text.isEmpty()
            ){
                Toast.makeText(
                    this,
                    "Complete todos los campos",
                    Toast.LENGTH_LONG
                ).show()
                return@setOnClickListener
            }

            val usuario = Usuario(
                null,
                primerNombre.text.toString(),
                segundoNombre.text.toString(),
                primerApellido.text.toString(),
                segundoApellido.text.toString(),
                documento.text.toString().toInt(),
                correo.text.toString(),
                direccion.text.toString(),
                password.text.toString()
            )

            RetrofitInstance.usuarioApi.registrarUsuario(usuario)
                .enqueue(object : Callback<Void> {

                    override fun onResponse(
                        call: Call<Void>,
                        response: Response<Void>
                    ) {

                        if(response.isSuccessful){

                            Toast.makeText(
                                this@RegistroActivity,
                                "Usuario creado correctamente",
                                Toast.LENGTH_LONG
                            ).show()

                            finish()

                        }else{

                            Toast.makeText(
                                this@RegistroActivity,
                                "Error al crear usuario",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<Void>, t: Throwable) {

                        Toast.makeText(
                            this@RegistroActivity,
                            "Error conectando con la API",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                })
        }
    }
}