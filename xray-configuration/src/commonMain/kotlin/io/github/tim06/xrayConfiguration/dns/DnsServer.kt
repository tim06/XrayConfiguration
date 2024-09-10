package io.github.tim06.xrayConfiguration.dns

import io.github.tim06.xrayConfiguration.serializer.DnsServerSerializer
import kotlinx.serialization.Polymorphic
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@Serializable(with = DnsServerSerializer::class)
@Polymorphic
sealed interface DnsServer {

    @JvmInline
    value class DirectDnsServer(val server: String) : DnsServer

    @Serializable
    data class CustomDnsServer(
        @SerialName("address")
        val address: String? = null,
        @SerialName("port")
        val port: Int? = null,
        @SerialName("domains")
        val domains: List<String>? = null,
        @SerialName("expectIPs")
        val expectIPs: List<String>? = null,
        @SerialName("skipFallback")
        val skipFallback: Boolean? = null,
        @SerialName("clientIP")
        val clientIP: String? = null,
        @SerialName("queryStrategy")
        val queryStrategy: QueryStrategy? = null,
    ) : DnsServer
}