package com.example.appinterface.activitys.activityContrato

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.appinterface.Api.RetrofitInstance
import com.example.appinterface.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EliminarContratoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eliminar_contrato)

        val txtId = findViewById<EditText>(R.id.txtId)
        val btnEliminar = findViewById<Button>(R.id.btnEliminar)
        val btnVolver = findViewById<Button>(R.id.btnVolver)

        btnEliminar.setOnClickListener {

            if (txtId.text.toString().isEmpty()) {

                Toast.makeText(
                    this,
                    "Ingrese un ID",
                    Toast.LENGTH_LONG
                ).show()

                return@setOnClickListener
            }

            val id = txtId.text.toString().toInt()

            AlertDialog.Builder(this)
                .setTitle("Eliminar contrato")
                .setMessage("¿Seguro que desea eliminar?")
                .setPositiveButton("Sí") { _, _ ->

                    RetrofitInstance.api.eliminarContrato(id)
                        .enqueue(object : Callback<String> {

                            override fun onResponse(
                                call: Call<String>,
                                response: Response<String>
                            ) {

                                if (response.isSuccessful) {

                                    Toast.makeText(
                                        this@EliminarContratoActivity,
                                        "Contrato eliminado correctamente",
                                        Toast.LENGTH_LONG
                                    ).show()

                                    finish()

                                } else {

                                    Toast.makeText(
                                        this@EliminarContratoActivity,
                                        "Error al eliminar",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            }

                            override fun onFailure(call: Call<String>, t: Throwable) {

                                Toast.makeText(
                                    this@EliminarContratoActivity,
                                    "Error de conexión",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        })
                }
                .setNegativeButton("No", null)
                .show()
        }

        btnVolver.setOnClickListener {
            finish()
        }
    }
}