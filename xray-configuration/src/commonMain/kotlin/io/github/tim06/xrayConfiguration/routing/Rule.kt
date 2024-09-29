package io.github.tim06.xrayConfiguration.routing

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @param type Currently, only the option "field" is supported.
 */
@Serializable
data class Rule(
    @SerialName("domainMatcher")
    val domainMatcher: DomainMatcher? = null,
    @SerialName("type")
    val type: String = "field",
    @SerialName("domain")
    val domain: List<String>? = null,
    @SerialName("ip")
    val ip: List<String>? = null,
    @SerialName("port")
    val port: String? = null,
    @SerialName("sourcePort")
    val sourcePort: String? = null,
    @SerialName("network")
    val network: Network? = null,
    @SerialName("source")
    val source: List<String>? = null,
    @SerialName("user")
    val user: List<String>? = null,
    @SerialName("inboundTag")
    val inboundTag: List<String>? = null,
    @SerialName("protocol")
    val protocol: List<Protocol>? = null,
    @SerialName("attrs")
    val attrs: Map<String, String>? = null,
    @SerialName("outboundTag")
    val outboundTag: String? = null,
    @SerialName("balancerTag")
    val balancerTag: String? = null,
)
