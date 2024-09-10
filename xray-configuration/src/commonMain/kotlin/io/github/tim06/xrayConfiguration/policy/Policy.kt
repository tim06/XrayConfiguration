package io.github.tim06.xrayConfiguration.policy

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Policy(
    @SerialName("levels")
    val levels: Map<String, Level>,
    @SerialName("system")
    val system: System
)