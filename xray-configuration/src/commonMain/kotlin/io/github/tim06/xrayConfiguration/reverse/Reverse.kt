package io.github.tim06.xrayConfiguration.reverse

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Reverse(
    @SerialName("bridges")
    val bridges: List<Bridge>,
    @SerialName("portals")
    val portals: List<Portal>,
)
