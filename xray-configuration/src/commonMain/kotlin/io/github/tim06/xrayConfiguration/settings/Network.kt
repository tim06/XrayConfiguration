package io.github.tim06.xrayConfiguration.settings

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class Network {
    @SerialName("tcp")
    TCP,

    @SerialName("kcp")
    KCP,

    @SerialName("ws")
    WS,

    @SerialName("http")
    HTTP,

    @SerialName("quic")
    QUIC,

    @SerialName("grpc")
    GRPC,

    @SerialName("httpupgrade")
    HTTPUPGRADE;

    companion object {
        fun find(name: String): io.github.tim06.xrayConfiguration.settings.Network? {
            return entries.find { it.name.equals(other = name, ignoreCase = true) }
        }
    }
}