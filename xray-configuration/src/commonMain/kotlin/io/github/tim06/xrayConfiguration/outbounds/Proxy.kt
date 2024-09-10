package io.github.tim06.xrayConfiguration.outbounds

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Proxy(
    @SerialName("tag")
    val tag: String,
)
