package com.example.appinterface.Api

import com.example.appinterface.model.LoginRequest
import com.example.appinterface.model.LoginResponse
import com.example.appinterface.model.Usuario
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface UsuarioApiService {

    @POST("api/auth/registro")
    fun registrarUsuario(
        @Body usuario: Usuario
    ): Call<Void>

    @POST("api/auth/login")
    fun login(
        @Body login: LoginRequest
    ): Call<LoginResponse>
}