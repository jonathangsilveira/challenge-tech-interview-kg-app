package edu.jgsilveira.challenge.kg.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class RemoteActiveEvent(
    @SerialName("d") val d: String,
    @SerialName("i") val i: String,
    @SerialName("sh") val sh: String,
    @SerialName("si") val si: String,
    @SerialName("tt") val tt: Int
)