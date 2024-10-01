package io.github.tim06.xrayConfiguration.routing

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Balancer(
    @SerialName("tag")
    val tag: String,
    @SerialName("selector")
    val selector: List<String>,
    @SerialName("fallbackTag")
    val fallbackTag: String,
    @SerialName("strategy")
    val strategy: Strategy = Strategy(),
)
