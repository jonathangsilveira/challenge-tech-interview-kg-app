package edu.jgsilveira.challenge.kg.data.remote.source

import edu.jgsilveira.challenge.kg.data.mapper.SportEventsMapper
import edu.jgsilveira.challenge.kg.data.remote.SportEventsApiService
import edu.jgsilveira.challenge.kg.domain.model.SportEvent

internal class SportEventsRemoteDataSourceImpl(
    private val sportEventsApi: SportEventsApiService,
    private val sportEventsMapper: SportEventsMapper
) : SportEventsRemoteDataSource {

    override suspend fun getSportEvents(): List<SportEvent> {
        return sportEventsApi.getSportEvents()
            .map(sportEventsMapper::map)
    }

    override suspend fun filterBySportId(sportId: String): List<SportEvent> {
        return sportEventsApi.getSportById(sportId)
            .map(sportEventsMapper::map)
    }
}