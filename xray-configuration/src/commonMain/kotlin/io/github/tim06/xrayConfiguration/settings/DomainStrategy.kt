package io.github.tim06.xrayConfiguration.settings

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class DomainStrategy {
    @SerialName("AsIs")
    AsIs,

    @SerialName("UseIP")
    UseIP,

    @SerialName("UseIPv4")
    UseIPv4,

    @SerialName("UseIPv6")
    UseIPv6

}