package io.github.tim06.xrayConfiguration.inbounds

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class Destination {
    @SerialName("http")
    HTTP,
    @SerialName("tls")
    TLS,
    @SerialName("quic")
    QUIC,
    @SerialName("fakedns")
    FAKEDNS,
    @SerialName("fakedns_others")
    `FAKEDNS_OTHERS`,
}