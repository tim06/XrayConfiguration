package io.github.tim06.xrayConfiguration

import io.github.tim06.xrayConfiguration.api.Api
import io.github.tim06.xrayConfiguration.burstObservatory.BurstObservatoryObject
import io.github.tim06.xrayConfiguration.burstObservatory.PingConfigObject
import io.github.tim06.xrayConfiguration.dns.Dns
import io.github.tim06.xrayConfiguration.dns.DnsServer
import io.github.tim06.xrayConfiguration.dns.QueryStrategy
import io.github.tim06.xrayConfiguration.fakedns.FakeDns
import io.github.tim06.xrayConfiguration.inbounds.Destination
import io.github.tim06.xrayConfiguration.inbounds.Inbound
import io.github.tim06.xrayConfiguration.inbounds.Sniffing
import io.github.tim06.xrayConfiguration.inbounds.settings.HttpInboundConfigurationObject
import io.github.tim06.xrayConfiguration.inbounds.settings.SocksInboundConfigurationObject
import io.github.tim06.xrayConfiguration.log.Log
import io.github.tim06.xrayConfiguration.log.LogLevel
import io.github.tim06.xrayConfiguration.metrics.Metrics
import io.github.tim06.xrayConfiguration.observatory.ObservatoryObject
import io.github.tim06.xrayConfiguration.outbounds.Outbound
import io.github.tim06.xrayConfiguration.outbounds.settings.ShadowsocksOutboundConfigurationObject
import io.github.tim06.xrayConfiguration.outbounds.settings.TrojanOutboundConfigurationObject
import io.github.tim06.xrayConfiguration.outbounds.settings.VlessOutboundConfigurationObject
import io.github.tim06.xrayConfiguration.outbounds.settings.VmessOutboundConfigurationObject
import io.github.tim06.xrayConfiguration.policy.Policy
import io.github.tim06.xrayConfiguration.reverse.Reverse
import io.github.tim06.xrayConfiguration.routing.*
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
    @SerialName("observatory")
    val observatory: ObservatoryObject? = null,
    @SerialName("burstObservatory")
    val burstObservatory: BurstObservatoryObject? = null,
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
            val dnsServers = listOf(
                DnsServer.DirectDnsServer("1.1.1.1"),
                DnsServer.DirectDnsServer("8.8.8.8"),
            )

            val inbounds = listOf(
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
            )

            val balancerRule = Rule(
                inboundTag = listOf(
                    "socks",
                    "http"
                ),
                balancerTag = "balancer"
            )

            val isMultipleOutbounds = outbounds != null && outbounds.count() > 1
            val defaultRulesTag = if (isMultipleOutbounds) "direct" else "proxy"
            val routingDefaultRules = mutableListOf(
                Rule(
                    ip = listOf("77.88.8.8"),
                    outboundTag = defaultRulesTag,
                    port = "53"
                ),
                Rule(
                    ip = listOf("1.1.1.1", "8.8.8.8"),
                    outboundTag = if (isMultipleOutbounds) "balancer" else "proxy",
                    port = "53"
                )
            ).apply {
                if (isMultipleOutbounds) {
                    add(balancerRule)
                }
            }

            val balancer = Balancer(
                tag = "balancer",
                selector = outbounds?.mapNotNull { it.tag } ?: emptyList(),
            )
            return copy(
                dns = Dns(
                    servers = dnsServers,
                    disableCache = true,
                    queryStrategy = QueryStrategy.UseIPv4
                ),
                inbounds = inbounds,
                log = Log(level = LogLevel.Info),
                routing = routing?.let { routing ->
                    routing.copy(
                        domainStrategy = routing.domainStrategy ?: DomainStrategy.IPIfNonMatch,
                        rules = routing.rules?.let { current ->
                            val mutable = current.toMutableList()
                            mutable.addAll(routingDefaultRules)
                            mutable
                        } ?: routingDefaultRules
                    )
                } ?: Routing(
                    domainStrategy = DomainStrategy.IPIfNonMatch,
                    rules = routingDefaultRules,
                    balancers = listOf(balancer).takeIf { isMultipleOutbounds }
                ),
                burstObservatory = BurstObservatoryObject(
                    subjectSelector = listOf("outbound"),
                    pingConfig = PingConfigObject(
                        destination = "http://www.gstatic.com/generate_204",
                        interval = "20s",
                        connectivity = "",
                        timeout = "2s",
                        sampling = 3
                    )
                ).takeIf { isMultipleOutbounds }
            )
        }
    }
}