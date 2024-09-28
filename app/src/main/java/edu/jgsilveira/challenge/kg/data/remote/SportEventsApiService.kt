package edu.jgsilveira.challenge.kg.data.remote

import edu.jgsilveira.challenge.kg.data.remote.model.RemoteSportEvent
import retrofit2.http.GET
import retrofit2.http.Query

internal interface SportEventsApiService {

    @GET("sports")
    suspend fun getSportEvents(): List<RemoteSportEvent>

    @GET("sports")
    suspend fun getSportById(@Query("i") sportId: String): List<RemoteSportEvent>
}