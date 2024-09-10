package io.github.tim06.xrayConfiguration.settings.kcp

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Kcp(
    @SerialName("mtu")
    val mtu: Int,
    @SerialName("tti")
    val tti: Int,
    @SerialName("uplinkCapacity")
    val uplinkCapacity: Int,
    @SerialName("downlinkCapacity")
    val downlinkCapacity: Int,
    @SerialName("congestion")
    val congestion: Boolean,
    @SerialName("readBufferSize")
    val readBufferSize: Int,
    @SerialName("writeBufferSize")
    val writeBufferSize: Int,
    @SerialName("header")
    val header: Header,
    @SerialName("seed")
    val seed: String,
)
