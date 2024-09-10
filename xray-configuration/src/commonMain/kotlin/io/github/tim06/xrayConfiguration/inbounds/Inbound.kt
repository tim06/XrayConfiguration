package io.github.tim06.xrayConfiguration.inbounds

import io.github.tim06.xrayConfiguration.Protocol
import io.github.tim06.xrayConfiguration.inbounds.settings.InboundConfigurationObject
import io.github.tim06.xrayConfiguration.serializer.InboundObjectSerializer
import io.github.tim06.xrayConfiguration.settings.StreamSettings
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable(with = InboundObjectSerializer::class)
data class Inbound(
    @SerialName("listen")
    val listen: String? = null,
    @SerialName("port")
    val port: Int? = null,
    @SerialName("protocol")
    val protocol: Protocol? = null,
    @SerialName("settings")
    val settings: InboundConfigurationObject? = null,
    @SerialName("streamSettings")
    val streamSettings: StreamSettings? = null,
    @SerialName("tag")
    val tag: String,
    @SerialName("sniffing")
    val sniffing: Sniffing? = null,
    @SerialName("allocate")
    val allocate: Allocate? = null,
)
