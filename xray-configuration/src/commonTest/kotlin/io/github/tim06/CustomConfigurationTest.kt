package io.github.tim06

import io.github.tim06.xrayConfiguration.dns.Dns
import io.github.tim06.xrayConfiguration.dns.DnsHosts
import io.github.tim06.xrayConfiguration.dns.DnsServer
import io.github.tim06.xrayConfiguration.inbounds.Destination
import io.github.tim06.xrayConfiguration.inbounds.Inbound
import io.github.tim06.xrayConfiguration.inbounds.Sniffing
import io.github.tim06.xrayConfiguration.inbounds.settings.HttpInboundConfigurationObject
import io.github.tim06.xrayConfiguration.inbounds.settings.SocksInboundConfigurationObject
import io.github.tim06.xrayConfiguration.log.Log
import io.github.tim06.xrayConfiguration.log.LogLevel
import io.github.tim06.xrayConfiguration.outbounds.Mux
import io.github.tim06.xrayConfiguration.outbounds.Outbound
import io.github.tim06.xrayConfiguration.outbounds.settings.BlackholeOutboundConfigurationObject
import io.github.tim06.xrayConfiguration.outbounds.settings.TrojanOutboundConfigurationObject
import io.github.tim06.xrayConfiguration.routing.DomainStrategy
import io.github.tim06.xrayConfiguration.routing.Routing
import io.github.tim06.xrayConfiguration.routing.Rule
import io.github.tim06.xrayConfiguration.settings.Network
import io.github.tim06.xrayConfiguration.Protocol
import io.github.tim06.xrayConfiguration.XrayConfiguration
import io.github.tim06.xrayConfiguration.outbounds.settings.FreedomOutboundConfigurationObject
import io.github.tim06.xrayConfiguration.settings.Security
import io.github.tim06.xrayConfiguration.settings.StreamSettings
import io.github.tim06.xrayConfiguration.settings.tcp.Tcp
import io.github.tim06.xrayConfiguration.settings.tcp.header.Header
import io.github.tim06.xrayConfiguration.settings.tls.Tls
import kotlin.test.Test
import kotlin.test.assertEquals

class CustomConfigurationTest {

    @Test
    fun customConfigEqualsTest() {
        val config =
            "{\"dns\":{\"hosts\":{\"domain:googleapis.cn\":\"googleapis.com\"},\"servers\":[\"1.1.1.1\",{\"address\":\"223.5.5.5\",\"domains\":[\"geosite:cn\",\"geosite:geolocation-cn\"],\"expectIPs\":[\"geoip:cn\"],\"port\":53}]},\"inbounds\":[{\"listen\":\"127.0.0.1\",\"port\":10808,\"protocol\":\"socks\",\"settings\":{\"auth\":\"noauth\",\"udp\":true,\"userLevel\":8},\"sniffing\":{\"destOverride\":[\"http\",\"tls\"],\"enabled\":true,\"routeOnly\":false},\"tag\":\"socks\"},{\"listen\":\"127.0.0.1\",\"port\":10809,\"protocol\":\"http\",\"settings\":{\"userLevel\":8},\"tag\":\"http\"}],\"log\":{\"loglevel\":\"info\"},\"outbounds\":[{\"mux\":{\"concurrency\":-1,\"enabled\":false,\"xudpConcurrency\":8,\"xudpProxyUDP443\":\"\"},\"protocol\":\"trojan\",\"settings\":{\"servers\":[{\"address\":\"121.127.46.131\",\"flow\":\"\",\"level\":8,\"method\":\"chacha20-poly1305\",\"ota\":false,\"password\":\"ypDt8RkT7J\",\"port\":42118}]},\"streamSettings\":{\"network\":\"tcp\",\"security\":\"tls\",\"tcpSettings\":{\"header\":{\"type\":\"none\"}},\"tlsSettings\":{\"allowInsecure\":true,\"alpn\":[\"http/1.1\"],\"fingerprint\":\"chrome\",\"serverName\":\"wildlydrowse.com\",\"show\":false}},\"tag\":\"proxy\"},{\"protocol\":\"freedom\",\"settings\":{},\"tag\":\"direct\"},{\"protocol\":\"blackhole\",\"settings\":{\"response\":{\"type\":\"http\"}},\"tag\":\"block\"}],\"remarks\":\"ttls\",\"routing\":{\"domainStrategy\":\"IPIfNonMatch\",\"rules\":[{\"ip\":[\"1.1.1.1\"],\"outboundTag\":\"proxy\",\"port\":\"53\"},{\"ip\":[\"223.5.5.5\"],\"outboundTag\":\"direct\",\"port\":\"53\"},{\"domain\":[\"domain:googleapis.cn\"],\"outboundTag\":\"proxy\"},{\"ip\":[\"geoip:private\"],\"outboundTag\":\"direct\"},{\"ip\":[\"geoip:cn\"],\"outboundTag\":\"direct\"},{\"domain\":[\"geosite:cn\"],\"outboundTag\":\"direct\"},{\"domain\":[\"geosite:geolocation-cn\"],\"outboundTag\":\"direct\"},{\"outboundTag\":\"proxy\",\"port\":\"0-65535\"}]}}"

        val model = XrayConfiguration(
            dns = Dns(
                hosts = mapOf(
                    "domain:googleapis.cn" to DnsHosts.DnsHostsServer("googleapis.com"),
                ),
                servers = listOf(
                    DnsServer.DirectDnsServer(server = "1.1.1.1"),
                    DnsServer.CustomDnsServer(
                        address = "223.5.5.5",
                        domains = listOf("geosite:cn", "geosite:geolocation-cn"),
                        expectIPs = listOf("geoip:cn"),
                        port = 53
                    )
                )
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
                        enabled = true,
                        destOverride = listOf(Destination.HTTP, Destination.TLS),
                        routeOnly = false,
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
            outbounds = listOf(
                Outbound(
                    mux = Mux(
                        enabled = false,
                        concurrency = -1,
                        xudpConcurrency = 8,
                        xudpProxyUDP443 = ""
                    ),
                    protocol = Protocol.TROJAN,
                    settings = TrojanOutboundConfigurationObject(
                        servers = listOf(
                            TrojanOutboundConfigurationObject.Server(
                                address = "121.127.46.131",
                                password = "ypDt8RkT7J",
                                port = 42118,
                                level = 8
                            )
                        )
                    ),
                    streamSettings = StreamSettings(
                        network = Network.TCP,
                        security = Security.TLS,
                        tcpSettings = Tcp(
                            header = Header(
                                type = "none"
                            )
                        ),
                        tlsSettings = Tls(
                            serverName = "wildlydrowse.com",
                            allowInsecure = true,
                            alpn = listOf("http/1.1"),
                            fingerprint = "chrome"
                        )
                    ),
                    tag = "proxy"
                ),
                Outbound(
                    protocol = Protocol.FREEDOM,
                    tag = "direct",
                    settings = FreedomOutboundConfigurationObject()
                ),
                Outbound(
                    protocol = Protocol.BLACKHOLE,
                    settings = BlackholeOutboundConfigurationObject(
                        response = BlackholeOutboundConfigurationObject.Response(
                            type = BlackholeOutboundConfigurationObject.Response.Type.HTTP
                        )
                    ),
                    tag = "block"
                )
            ),
            routing = Routing(
                domainStrategy = DomainStrategy.IPIfNonMatch,
                rules = listOf(
                    Rule(
                        ip = listOf("1.1.1.1"),
                        outboundTag = "proxy",
                        port = "53"
                    ),
                    Rule(
                        ip = listOf("223.5.5.5"),
                        outboundTag = "direct",
                        port = "53"
                    ),
                    Rule(
                        domain = listOf("domain:googleapis.cn"),
                        outboundTag = "proxy"
                    ),
                    Rule(
                        ip = listOf("geoip:private"),
                        outboundTag = "direct"
                    ),
                    Rule(
                        ip = listOf("geoip:cn"),
                        outboundTag = "direct"
                    ),
                    Rule(
                        domain = listOf("geosite:cn"),
                        outboundTag = "direct"
                    ),
                    Rule(
                        domain = listOf("geosite:geolocation-cn"),
                        outboundTag = "direct"
                    ),
                    Rule(
                        outboundTag = "proxy",
                        port = "0-65535"
                    )
                )
            )
        )

        val decodedModel = XrayConfiguration.fromJson(config)
        assertEquals(model.dns, decodedModel.dns)
        assertEquals(model.inbounds, decodedModel.inbounds)
        assertEquals(model.outbounds, decodedModel.outbounds)
        assertEquals(model.routing, decodedModel.routing)
    }
}