package edu.jgsilveira.challenge.kg.data.remote.source

import edu.jgsilveira.challenge.kg.domain.model.SportEvent

internal interface SportEventsRemoteDataSource {
    suspend fun getSportEvents(): List<SportEvent>
    suspend fun filterBySportId(sportId: String): List<SportEvent>
}