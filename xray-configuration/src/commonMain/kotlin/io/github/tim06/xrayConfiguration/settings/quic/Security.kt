package io.github.tim06.xrayConfiguration.settings.quic

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class Security {
    @SerialName("none")
    NONE,

    @SerialName("aes-128-gcm")
    AES_128_GCM,

    @SerialName("chacha20-poly1305")
    CHACHA20_POLY1305,
}