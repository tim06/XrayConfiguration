package io.github.tim06.xrayConfiguration.observatory

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ObservatoryObject(
    @SerialName("subjectSelector")
    val subjectSelector: List<String>,
    @SerialName("probeUrl")
    val probeUrl: String,
    @SerialName("probeInterval")
    val probeInterval: String,
    @SerialName("enableConcurrency")
    val enableConcurrency: Boolean,
)
