package io.github.tim06.xrayConfiguration.inbounds

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Allocate(
    @SerialName("strategy")
    val strategy: Strategy,
    @SerialName("refresh")
    val refresh: Int,
    @SerialName("concurrency")
    val concurrency: Int,
)
