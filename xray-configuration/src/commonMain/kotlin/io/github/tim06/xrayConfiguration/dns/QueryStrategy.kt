package io.github.tim06.xrayConfiguration.dns

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class QueryStrategy {
    @SerialName("UseIP")
    UseIP,

    @SerialName("UseIPv4")
    UseIPv4,

    @SerialName("UseIPv6")
    UseIPv6,
}