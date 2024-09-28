package edu.jgsilveira.challenge.kg.data.repository

import edu.jgsilveira.challenge.kg.data.remote.source.SportEventsRemoteDataSource
import edu.jgsilveira.challenge.kg.domain.model.SportEvent
import edu.jgsilveira.challenge.kg.domain.repository.SportEventsRepository

internal class SportEventsRepositoryImpl(
    private val remoteDataSource: SportEventsRemoteDataSource
) : SportEventsRepository {

    override suspend fun getActiveEvents(): List<SportEvent> {
        return remoteDataSource.getSportEvents()
    }

    override suspend fun getActiveEventsBySport(sportId: String): List<SportEvent> {
        return remoteDataSource.filterBySportId(sportId)
    }
}