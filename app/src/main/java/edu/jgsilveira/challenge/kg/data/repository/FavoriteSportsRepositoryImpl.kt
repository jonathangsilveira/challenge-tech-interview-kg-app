package edu.jgsilveira.challenge.kg.data.repository

import edu.jgsilveira.challenge.kg.data.local.FavoriteSportEventLocalDataSource
import edu.jgsilveira.challenge.kg.domain.repository.FavoriteSportsRepository

internal class FavoriteSportsRepositoryImpl(
    private val localDataSource: FavoriteSportEventLocalDataSource
) : FavoriteSportsRepository {

    override suspend fun addToFavorite(sportEventId: String) {
        localDataSource.add(sportEventId)
    }

    override suspend fun removeFromFavorite(sportEventId: String) {
        localDataSource.clear()
    }

    override suspend fun getFavoriteIds(): List<String> {
        return localDataSource.getSportEventIds()
    }
}