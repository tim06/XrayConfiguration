package io.github.tim06.xrayConfiguration.dns

import io.github.tim06.xrayConfiguration.serializer.DnsHostsSerializer
import kotlinx.serialization.Polymorphic
import kotlinx.serialization.Serializable

@Serializable(with = DnsHostsSerializer::class)
@Polymorphic
sealed interface DnsHosts {

    @Serializable
    data class DnsHostsServer(val server: String) : DnsHosts

    @Serializable
    data class DnsHostsServers(val servers: List<String>) : DnsHosts

}
