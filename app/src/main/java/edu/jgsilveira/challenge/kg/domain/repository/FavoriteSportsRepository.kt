package edu.jgsilveira.challenge.kg.domain.repository

interface FavoriteSportsRepository {
    suspend fun addToFavorite(sportEventId: String)
    suspend fun removeFromFavorite(sportEventId: String)
    suspend fun getFavoriteIds(): List<String>
}