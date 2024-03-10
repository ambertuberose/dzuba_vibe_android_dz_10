package com.example.catfacts.network

import retrofit2.http.GET
import com.example.catfacts.model.CatFact

interface ApiService {
    @GET("fact")
    suspend fun getCatFact(): CatFact
}