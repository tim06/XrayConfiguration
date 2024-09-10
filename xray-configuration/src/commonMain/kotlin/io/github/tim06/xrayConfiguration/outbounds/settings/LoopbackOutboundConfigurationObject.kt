package io.github.tim06.xrayConfiguration.outbounds.settings

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoopbackOutboundConfigurationObject(
    @SerialName("inboundTag")
    val inboundTag: String,
)
