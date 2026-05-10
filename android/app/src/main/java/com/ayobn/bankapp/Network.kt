package com.ayobn.bankapp

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

// ---- Data classes (matching backend DTOs) ----

data class LoginRequest(val customerNumber: Int, val password: String)
data class LoginResponse(val accessToken: String, val tokenType: String, val expiresIn: Long)

data class Account(
    val accountNumber: String,
    val balance: Float,
    val nickname: String?,
)

data class UpdateNicknameRequest(val nickname: String?)

// ---- Retrofit API interface ----

interface BankApi {

    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @GET("api/accounts")
    suspend fun listAccounts(@Header("Authorization") bearer: String): List<Account>

    @GET("api/accounts/{accountNumber}")
    suspend fun getAccount(
        @Header("Authorization") bearer: String,
        @Path("accountNumber") accountNumber: String,
    ): Account

    @PATCH("api/accounts/{accountNumber}")
    suspend fun updateNickname(
        @Header("Authorization") bearer: String,
        @Path("accountNumber") accountNumber: String,
        @Body request: UpdateNicknameRequest,
    ): Account
}

// ---- Singleton holding the Retrofit instance ----

object Network {
    private const val BASE_URL = "http://10.0.2.2:8080/"

    private val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    val api: BankApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(BankApi::class.java)
}