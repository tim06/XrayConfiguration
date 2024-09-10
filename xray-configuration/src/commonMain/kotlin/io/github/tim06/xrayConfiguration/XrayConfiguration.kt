package io.github.tim06.xrayConfiguration

import io.github.tim06.xrayConfiguration.api.Api
import io.github.tim06.xrayConfiguration.dns.Dns
import io.github.tim06.xrayConfiguration.dns.DnsServer
import io.github.tim06.xrayConfiguration.fakedns.FakeDns
import io.github.tim06.xrayConfiguration.inbounds.Destination
import io.github.tim06.xrayConfiguration.inbounds.Inbound
import io.github.tim06.xrayConfiguration.inbounds.Sniffing
import io.github.tim06.xrayConfiguration.inbounds.settings.HttpInboundConfigurationObject
import io.github.tim06.xrayConfiguration.inbounds.settings.SocksInboundConfigurationObject
import io.github.tim06.xrayConfiguration.log.Log
import io.github.tim06.xrayConfiguration.log.LogLevel
import io.github.tim06.xrayConfiguration.metrics.Metrics
import io.github.tim06.xrayConfiguration.outbounds.Outbound
import io.github.tim06.xrayConfiguration.outbounds.settings.ShadowsocksOutboundConfigurationObject
import io.github.tim06.xrayConfiguration.outbounds.settings.TrojanOutboundConfigurationObject
import io.github.tim06.xrayConfiguration.outbounds.settings.VlessOutboundConfigurationObject
import io.github.tim06.xrayConfiguration.outbounds.settings.VmessOutboundConfigurationObject
import io.github.tim06.xrayConfiguration.policy.Policy
import io.github.tim06.xrayConfiguration.reverse.Reverse
import io.github.tim06.xrayConfiguration.routing.DomainStrategy
import io.github.tim06.xrayConfiguration.routing.Routing
import io.github.tim06.xrayConfiguration.routing.Rule
import io.github.tim06.xrayConfiguration.stats.Stats
import io.github.tim06.xrayConfiguration.transport.Transport
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString

@Serializable
data class XrayConfiguration(
    @SerialName("log")
    val log: Log? = null,
    @SerialName("api")
    val api: Api? = null,
    @SerialName("dns")
    val dns: Dns? = null,
    @SerialName("routing")
    val routing: Routing? = null,
    @SerialName("policy")
    val policy: Policy? = null,
    @SerialName("inbounds")
    val inbounds: List<Inbound>? = null,
    @SerialName("outbounds")
    val outbounds: List<Outbound>? = null,
    @SerialName("transport")
    val transport: Transport? = null,
    @SerialName("stats")
    val stats: Stats? = null,
    @SerialName("reverse")
    val reverse: Reverse? = null,
    @SerialName("fakedns")
    val fakedns: List<FakeDns>? = null,
    @SerialName("metrics")
    val metrics: Metrics? = null,
) {

    fun raw(): String = json.encodeToString(this)

    fun domain(): String? {
        return when (val settings = outbounds?.firstOrNull()?.settings) {
            is ShadowsocksOutboundConfigurationObject -> {
                val server = settings.servers.firstOrNull()
                server?.run { "$address:$port" }
            }

            is TrojanOutboundConfigurationObject -> {
                val server = settings.servers.firstOrNull()
                server?.run { "$address:$port" }
            }

            is VlessOutboundConfigurationObject -> {
                val server = settings.vnext.firstOrNull()
                server?.run { "$address:$port" }
            }

            is VmessOutboundConfigurationObject -> {
                val server = settings.vnext.firstOrNull()
                server?.run { "$address:$port" }
            }

            else -> error("Unknown outbound configuration for take domain!")
        }
    }

    companion object {
        fun fromJson(jsonString: String): XrayConfiguration {
            return json.decodeFromString<XrayConfiguration>(jsonString)
        }

        fun XrayConfiguration.addMinimalSettings(): XrayConfiguration {
            return copy(
                dns = Dns(
                    servers = listOf(
                        DnsServer.DirectDnsServer("1.1.1.1"),
                        DnsServer.DirectDnsServer("8.8.8.8"),
                    ),
                ),
                inbounds = listOf(
                    Inbound(
                        listen = "127.0.0.1",
                        port = 10808,
                        protocol = Protocol.SOCKS,
                        settings = SocksInboundConfigurationObject(
                            auth = SocksInboundConfigurationObject.Auth.NOAUTH,
                            udp = true,
                            userLevel = 8
                        ),
                        sniffing = Sniffing(
                            destOverride = listOf(Destination.HTTP, Destination.TLS),
                            enabled = true,
                            routeOnly = false
                        ),
                        tag = "socks"
                    ),
                    Inbound(
                        listen = "127.0.0.1",
                        port = 10809,
                        protocol = Protocol.HTTP,
                        settings = HttpInboundConfigurationObject(
                            userLevel = 8
                        ),
                        tag = "http"
                    )
                ),
                log = Log(level = LogLevel.Info),
                routing = Routing(
                    domainStrategy = DomainStrategy.IPIfNonMatch,
                    rules = listOf(
                        Rule(
                            ip = listOf("1.1.1.1"),
                            outboundTag = "proxy",
                            port = "53"
                        ),
                        Rule(
                            ip = listOf("8.8.8.8"),
                            outboundTag = "proxy",
                            port = "53"
                        ),
                        Rule(
                            outboundTag = "proxy",
                            port = "0-65535"
                        )
                    )
                )
            )
        }
    }
}