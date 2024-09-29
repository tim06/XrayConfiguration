package io.github.tim06.xrayConfiguration.burstObservatory

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BurstObservatoryObject(
    @SerialName("subjectSelector")
    val subjectSelector: List<String>,
    @SerialName("pingConfig")
    val pingConfig: PingConfigObject,
)
