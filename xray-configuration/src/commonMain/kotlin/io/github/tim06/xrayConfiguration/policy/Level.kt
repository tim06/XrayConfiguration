package io.github.tim06.xrayConfiguration.policy

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Level(
    @SerialName("handshake")
    val handshake: Int? = null,
    @SerialName("connIdle")
    val connIdle: Int,
    @SerialName("uplinkOnly")
    val uplinkOnly: Int? = null,
    @SerialName("downlinkOnly")
    val downLinkOnly: Int? = null,
    @SerialName("statsUserUplink")
    val statsUserUplink: Boolean? = null,
    @SerialName("statsUserDownlink")
    val statsUserDownLink: Boolean? = null,
    @SerialName("bufferSize")
    val bufferSize: Int? = null,
)
