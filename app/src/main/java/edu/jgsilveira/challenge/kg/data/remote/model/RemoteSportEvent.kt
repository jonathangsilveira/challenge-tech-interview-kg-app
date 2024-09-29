package edu.jgsilveira.challenge.kg.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class RemoteSportEvent(
    @SerialName("i") val i: String,
    @SerialName("d") val d: String,
    @SerialName("e") val e: List<RemoteActiveEvent>
)