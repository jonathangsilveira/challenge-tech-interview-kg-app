package edu.jgsilveira.challenge.kg.data.mapper

import edu.jgsilveira.challenge.kg.data.remote.model.RemoteActiveEvent
import edu.jgsilveira.challenge.kg.domain.mapper.Mapper
import edu.jgsilveira.challenge.kg.domain.model.ActiveEvent

private const val COMPETITORS_DELIMITER = " - "
private const val COMPETITORS_LIMIT_COUNT = 2
private const val HOME_COMPETITOR_INDEX = 0
private const val AWAY_COMPETITOR_INDEX = 1

internal class ActiveEventMapper : Mapper<RemoteActiveEvent, ActiveEvent> {

    override fun map(
        from: RemoteActiveEvent
    ): ActiveEvent {
        val competitors = from.d.split(
            regex = COMPETITORS_DELIMITER.toRegex(),
            limit = COMPETITORS_LIMIT_COUNT
        )
        return ActiveEvent(
            id = from.i,
            homeCompetitor = competitors[HOME_COMPETITOR_INDEX],
            awayCompetitor = competitors[AWAY_COMPETITOR_INDEX],
            sportId = from.si,
            startsAt = from.tt * 1000L
        )
    }
}