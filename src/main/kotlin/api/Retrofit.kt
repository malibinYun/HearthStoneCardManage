package api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private val gsonConverterFactory = GsonConverterFactory.create()

val blizzardService = Retrofit.Builder()
    .baseUrl(BlizzardService.BASE_URL)
    .addConverterFactory(gsonConverterFactory)
    .build()
    .create(BlizzardService::class.java)

val blizzardOathService = Retrofit.Builder()
    .baseUrl(BlizzardOathService.BASE_URL)
    .addConverterFactory(gsonConverterFactory)
    .build()
    .create(BlizzardOathService::class.java)
