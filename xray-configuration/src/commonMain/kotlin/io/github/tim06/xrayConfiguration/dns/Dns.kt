package io.github.tim06.xrayConfiguration.dns

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Dns(
    @SerialName("hosts")
    val hosts: Map<String, DnsHosts>? = null,
    @SerialName("servers")
    val servers: List<DnsServer>,
    @SerialName("clientIp")
    val clientIp: String? = null,
    @SerialName("queryStrategy")
    val queryStrategy: QueryStrategy? = null,
    @SerialName("disableCache")
    val disableCache: Boolean? = null,
    @SerialName("disableFallback")
    val disableFallback: Boolean? = null,
    @SerialName("disableFallbackIfMatch")
    val disableFallbackIfMatch: Boolean? = null,
    @SerialName("tag")
    val tag: String? = null,
)
