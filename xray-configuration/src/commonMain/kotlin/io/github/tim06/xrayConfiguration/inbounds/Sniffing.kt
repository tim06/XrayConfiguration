package io.github.tim06.xrayConfiguration.inbounds

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Sniffing(
    @SerialName("enabled")
    val enabled: Boolean,
    @SerialName("destOverride")
    val destOverride: List<Destination>,
    @SerialName("metadataOnly")
    val metadataOnly: Boolean? = null,
    @SerialName("domainsExcluded")
    val domainsExcluded: List<String>? = null,
    @SerialName("routeOnly")
    val routeOnly: Boolean? = null,
)
