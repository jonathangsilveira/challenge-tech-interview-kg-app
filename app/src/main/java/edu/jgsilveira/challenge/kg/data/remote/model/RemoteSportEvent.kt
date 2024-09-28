package edu.jgsilveira.challenge.kg.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
internal data class RemoteSportEvent(
    val i: String,
    val d: String,
    val e: List<RemoteActiveEvent>
)