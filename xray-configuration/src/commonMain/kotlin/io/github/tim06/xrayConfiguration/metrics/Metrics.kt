package io.github.tim06.xrayConfiguration.metrics

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Metrics(
    @SerialName("tag")
    val tag: String,
)
