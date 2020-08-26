package api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private val gsonConverterFactory = GsonConverterFactory.create()

val blizzardService: BlizzardService = Retrofit.Builder()
    .baseUrl(BlizzardService.BASE_URL)
    .addConverterFactory(gsonConverterFactory)
    .build()
    .create(BlizzardService::class.java)

val blizzardOAuthService: BlizzardOAuthService = Retrofit.Builder()
    .baseUrl(BlizzardOAuthService.BASE_URL)
    .addConverterFactory(gsonConverterFactory)
    .build()
    .create(BlizzardOAuthService::class.java)
