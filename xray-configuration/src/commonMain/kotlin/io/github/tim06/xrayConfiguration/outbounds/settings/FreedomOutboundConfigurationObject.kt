package io.github.tim06.xrayConfiguration.outbounds.settings

import io.github.tim06.xrayConfiguration.settings.DomainStrategy
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @param redirect String format 0.0.0.0:0000
 */
@Serializable
data class FreedomOutboundConfigurationObject(
    @SerialName("domainStrategy")
    val domainStrategy: DomainStrategy? = null,
    @SerialName("redirect")
    val redirect: String? = null,
    @SerialName("userLevel")
    val userLevel: Int? = null,
    @SerialName("fragment")
    val fragment: Map<String, String>? = null,
    @SerialName("proxyProtocol")
    val proxyProtocol: Int? = null,
) : OutboundConfigurationObject
