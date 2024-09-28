package edu.jgsilveira.challenge.kg.data.local

internal class InMemoryFavoriteSportEventLocalDataSource : FavoriteSportEventLocalDataSource {
    private val sportEventIds = mutableSetOf<String>()

    override suspend fun add(sportEventId: String) {
        sportEventIds.add(sportEventId)
    }

    override suspend fun remove(sportEventId: String) {
        sportEventIds.remove(sportEventId)
    }

    override suspend fun clear() {
        sportEventIds.clear()
    }

    override suspend fun getSportEventIds(): List<String> {
        return sportEventIds.toList()
    }
}