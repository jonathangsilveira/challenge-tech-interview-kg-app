package edu.jgsilveira.challenge.kg.data.mapper

import edu.jgsilveira.challenge.kg.data.remote.model.RemoteSportEvent
import edu.jgsilveira.challenge.kg.domain.mapper.Mapper
import edu.jgsilveira.challenge.kg.domain.model.SportEvent

internal class SportEventsMapper(
    private val activeEventMapper: ActiveEventMapper
) : Mapper<RemoteSportEvent, SportEvent> {

    override fun map(
        from: RemoteSportEvent
    ): SportEvent = SportEvent(
        id = from.i,
        name = from.d,
        activeEvents = from.e.map(activeEventMapper::map)
    )
}