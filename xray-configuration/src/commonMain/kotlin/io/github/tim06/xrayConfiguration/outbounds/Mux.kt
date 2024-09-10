package io.github.tim06.xrayConfiguration.outbounds

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Mux(
    @SerialName("enabled")
    val enabled: Boolean,
    @SerialName("concurrency")
    val concurrency: Int,
    @SerialName("xudpConcurrency")
    val xudpConcurrency: Int,
    @SerialName("xudpProxyUDP443")
    val xudpProxyUDP443: String,
)
