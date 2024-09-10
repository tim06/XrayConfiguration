package io.github.tim06.xrayConfiguration.policy

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class System(
    @SerialName("statsInboundUplink")
    val statsInboundUplink: Boolean,
    @SerialName("statsInboundDownlink")
    val statsInboundDownLink: Boolean,
    @SerialName("statsOutboundUplink")
    val statsOutboundUplink: Boolean,
    @SerialName("statsOutboundDownlink")
    val statsOutboundDownLink: Boolean,
)
