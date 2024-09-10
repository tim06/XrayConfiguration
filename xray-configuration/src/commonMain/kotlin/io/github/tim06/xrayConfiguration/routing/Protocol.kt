package io.github.tim06.xrayConfiguration.routing

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class Protocol {
    @SerialName("http")
    HTTP,

    @SerialName("tls")
    TLS,

    @SerialName("bittorrent")
    BITTORRENT
}