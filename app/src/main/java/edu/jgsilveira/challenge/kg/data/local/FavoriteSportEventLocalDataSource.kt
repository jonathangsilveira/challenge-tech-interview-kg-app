package edu.jgsilveira.challenge.kg.data.local

internal interface FavoriteSportEventLocalDataSource {
    suspend fun add(sportEventId: String)
    suspend fun remove(sportEventId: String)
    suspend fun clear()
    suspend fun getSportEventIds(): List<String>
}