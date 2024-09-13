package io.github.tim06.xrayConfiguration.routing

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class Network {
    @SerialName("tcp")
    TCP,

    @SerialName("udp")
    UDP,

    @SerialName("tcp,udp")
    `TCP_UDP`
}