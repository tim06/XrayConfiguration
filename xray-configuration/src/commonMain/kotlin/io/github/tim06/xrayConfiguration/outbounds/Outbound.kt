package io.github.tim06.xrayConfiguration.outbounds

import io.github.tim06.xrayConfiguration.Protocol
import io.github.tim06.xrayConfiguration.outbounds.settings.OutboundConfigurationObject
import io.github.tim06.xrayConfiguration.serializer.OutboundObjectSerializer
import io.github.tim06.xrayConfiguration.settings.StreamSettings
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable(with = OutboundObjectSerializer::class)
data class Outbound(
    @SerialName("sendThrough")
    val sendThrough: String? = null,
    @SerialName("protocol")
    val protocol: Protocol? = null,
    @SerialName("settings")
    val settings: OutboundConfigurationObject? = null,
    @SerialName("streamSettings")
    val streamSettings: StreamSettings? = null,
    @SerialName("tag")
    val tag: String? = null,
    @SerialName("proxySettings")
    val proxySettings: Proxy? = null,
    @SerialName("mux")
    val mux: Mux? = null,
)
