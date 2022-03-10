package com.riocallos.breakingbad.services

import com.riocallos.breakingbad.data.models.Character
import retrofit2.Response
import retrofit2.http.GET

interface CharactersService {

    @GET("characters")
    suspend fun get(): Response<Array<Character>>

}