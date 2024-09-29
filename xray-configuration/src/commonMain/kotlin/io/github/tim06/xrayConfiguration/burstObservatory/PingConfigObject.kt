package io.github.tim06.xrayConfiguration.burstObservatory

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PingConfigObject(
    @SerialName("destination")
    val destination: String,
    @SerialName("connectivity")
    val connectivity: String,
    @SerialName("interval")
    val interval: String,
    @SerialName("sampling")
    val sampling: Int,
    @SerialName("timeout")
    val timeout: String,
)
