package io.github.tim06

import io.github.tim06.xrayConfiguration.Protocol
import io.github.tim06.xrayConfiguration.XrayConfiguration
import io.github.tim06.xrayConfiguration.api.Api
import io.github.tim06.xrayConfiguration.dns.Dns
import io.github.tim06.xrayConfiguration.dns.DnsServer
import io.github.tim06.xrayConfiguration.dns.QueryStrategy
import io.github.tim06.xrayConfiguration.inbounds.Destination
import io.github.tim06.xrayConfiguration.inbounds.Inbound
import io.github.tim06.xrayConfiguration.inbounds.Sniffing
import io.github.tim06.xrayConfiguration.inbounds.settings.DokoDemoInboundConfigurationObject
import io.github.tim06.xrayConfiguration.inbounds.settings.Method
import io.github.tim06.xrayConfiguration.inbounds.settings.ShadowsocksInboundConfigurationObject
import io.github.tim06.xrayConfiguration.log.Log
import io.github.tim06.xrayConfiguration.outbounds.Mux
import io.github.tim06.xrayConfiguration.outbounds.Outbound
import io.github.tim06.xrayConfiguration.outbounds.settings.FreedomOutboundConfigurationObject
import io.github.tim06.xrayConfiguration.outbounds.settings.VlessOutboundConfigurationObject
import io.github.tim06.xrayConfiguration.policy.Level
import io.github.tim06.xrayConfiguration.policy.Policy
import io.github.tim06.xrayConfiguration.policy.System
import io.github.tim06.xrayConfiguration.routing.DomainStrategy
import io.github.tim06.xrayConfiguration.routing.Network
import io.github.tim06.xrayConfiguration.routing.Routing
import io.github.tim06.xrayConfiguration.routing.Rule
import io.github.tim06.xrayConfiguration.settings.Security
import io.github.tim06.xrayConfiguration.settings.Sockopt
import io.github.tim06.xrayConfiguration.settings.StreamSettings
import io.github.tim06.xrayConfiguration.settings.tls.Tls
import io.github.tim06.xrayConfiguration.settings.ws.Ws
import io.github.tim06.xrayConfiguration.stats.Stats
import io.github.tim06.xrayConfiguration.transport.Transport
import kotlin.test.Test
import kotlin.test.assertEquals

class ConfigurationTest {

    @Test
    fun testSerialization() {
        val model = XrayConfiguration(
            log = Log(),
            inbounds = listOf(
                Inbound(
                    listen = "127.0.0.1",
                    tag = "socks5",
                    port = 60703,
                    protocol = Protocol.SHADOWSOCKS,
                    settings = ShadowsocksInboundConfigurationObject(
                        password = "123456",
                        method = Method.`CHACHA20-IETF-POLY1305`,
                        level = 8,
                        network = Network.TCP_UDP
                    ),
                    sniffing = Sniffing(
                        enabled = true,
                        domainsExcluded = listOf("courier.push.apple.com"),
                        destOverride = listOf(Destination.TLS, Destination.HTTP, Destination.QUIC)
                    )
                ),
                Inbound(
                    listen = "[::1]",
                    port = 60704,
                    tag = "api",
                    protocol = Protocol.DOKODEMO,
                    settings = DokoDemoInboundConfigurationObject(
                        address = "[::1]"
                    )
                ),
                Inbound(
                    listen = "127.0.0.1",
                    port = 60705,
                    tag = "inDns",
                    protocol = Protocol.DOKODEMO,
                    settings = DokoDemoInboundConfigurationObject(
                        address = "8.8.8.8",
                        port = 53,
                        timeout = 10,
                        userLevel = 0,
                        network = Network.TCP_UDP
                    )
                )
            ),
            outbounds = listOf(
                Outbound(
                    tag = "proxy",
                    protocol = Protocol.VLESS,
                    settings = VlessOutboundConfigurationObject(
                        vnext = listOf(
                            VlessOutboundConfigurationObject.Server(
                                port = 443,
                                address = "151.101.4.34",
                                users = listOf(
                                    VlessOutboundConfigurationObject.Server.User(
                                        id = "1e3a2038-e4c7-402d-a831-73e0b4e4bbf6",
                                        encryption = Security.NONE,
                                        flow = VlessOutboundConfigurationObject.Server.User.Flow.EMPTY,
                                        level = 8
                                    )
                                )
                            ),
                        )
                    ),
                    mux = Mux(
                        enabled = false,
                        xudpConcurrency = 128,
                        concurrency = 50,
                        xudpProxyUDP443 = "allow"
                    ),
                    streamSettings = StreamSettings(
                        wsSettings = Ws(
                            path = "/google.com",
                            headers = mapOf(
                                "host" to "Leonfinland.org"
                            )
                        ),
                        security = Security.TLS,
                        tlsSettings = Tls(
                            serverName = "yandex.ru",
                            allowInsecure = true,
                            alpn = emptyList(),
                            fingerprint = "chrome"
                        ),
                        network = io.github.tim06.xrayConfiguration.settings.Network.WS
                    )
                ),
                Outbound(
                    tag = "fragment",
                    protocol = Protocol.FREEDOM,
                    settings = FreedomOutboundConfigurationObject(
                        userLevel = 8,
                        fragment = mapOf(
                            "interval" to "10-100",
                            "length" to "80-250",
                            "packets" to "tlshello"
                        )
                    ),
                    streamSettings = StreamSettings(
                        sockopt = Sockopt(tcpNoDelay = true)
                    )
                ),
            ),
            dns = Dns(
                tag = "dnsQuery",
                disableFallback = true,
                disableFallbackIfMatch = true,
                servers = listOf(
                    DnsServer.CustomDnsServer(
                        address = "8.8.8.8",
                        skipFallback = false
                    )
                ),
                queryStrategy = QueryStrategy.UseIP,
                disableCache = true
            ),
            api = Api(
                tag = "api",
                services = listOf("StatsService")
            ),
            stats = Stats,
            routing = Routing(
                domainStrategy = DomainStrategy.AsIs,
                balancers = emptyList(),
                rules = listOf(
                    Rule(
                        inboundTag = listOf("api"),
                        type = "field",
                        outboundTag = "api"
                    ),
                    Rule(
                        inboundTag = listOf("inDns"),
                        type = "field",
                        outboundTag = "outDns"
                    ),
                    Rule(
                        inboundTag = listOf("dnsQuery"),
                        type = "field",
                        outboundTag = "proxy"
                    )
                )
            ),
            policy = Policy(
                levels = mapOf(
                    "8" to Level(
                        bufferSize = 0,
                        handshake = 4,
                        downLinkOnly = 1,
                        uplinkOnly = 1,
                        statsUserDownLink = false,
                        statsUserUplink = false,
                        connIdle = 30
                    )
                ),
                system = System(
                    statsInboundUplink = true,
                    statsInboundDownLink = true,
                    statsOutboundUplink = true,
                    statsOutboundDownLink = true
                )
            ),
            transport = Transport()
        )
        val decodedModel = XrayConfiguration.fromJson(initial)
        assertEquals(model, decodedModel)
    }

    @Test
    fun testDeserialization() {
        val model = XrayConfiguration.fromJson(initial)
        assertEquals(model.outbounds?.first()?.protocol, Protocol.VLESS)
    }

    private companion object {
        const val initial = "{\n" +
                "  \"log\": {},\n" +
                "  \"inbounds\": [\n" +
                "    {\n" +
                "      \"settings\": {\n" +
                "        \"password\": \"123456\",\n" +
                "        \"ota\": true,\n" +
                "        \"udp\": false,\n" +
                "        \"method\": \"chacha20-ietf-poly1305\",\n" +
                "        \"level\": 8,\n" +
                "        \"network\": \"tcp,udp\"\n" +
                "      },\n" +
                "      \"listen\": \"127.0.0.1\",\n" +
                "      \"tag\": \"socks5\",\n" +
                "      \"port\": 60703,\n" +
                "      \"protocol\": \"shadowsocks\",\n" +
                "      \"sniffing\": {\n" +
                "        \"domainsExcluded\": [\n" +
                "          \"courier.push.apple.com\"\n" +
                "        ],\n" +
                "        \"destOverride\": [\n" +
                "          \"tls\",\n" +
                "          \"http\",\n" +
                "          \"quic\"\n" +
                "        ],\n" +
                "        \"enabled\": true\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"settings\": {\n" +
                "        \"address\": \"[::1]\"\n" +
                "      },\n" +
                "      \"listen\": \"[::1]\",\n" +
                "      \"port\": 60704,\n" +
                "      \"tag\": \"api\",\n" +
                "      \"protocol\": \"dokodemo-door\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"settings\": {\n" +
                "        \"timeout\": 10,\n" +
                "        \"port\": 53,\n" +
                "        \"userLevel\": 0,\n" +
                "        \"network\": \"tcp,udp\",\n" +
                "        \"address\": \"8.8.8.8\"\n" +
                "      },\n" +
                "      \"listen\": \"127.0.0.1\",\n" +
                "      \"port\": 60705,\n" +
                "      \"tag\": \"inDns\",\n" +
                "      \"protocol\": \"dokodemo-door\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"outbounds\": [\n" +
                "    {\n" +
                "      \"tag\": \"proxy\",\n" +
                "      \"protocol\": \"vless\",\n" +
                "      \"settings\": {\n" +
                "        \"vnext\": [\n" +
                "          {\n" +
                "            \"port\": 443,\n" +
                "            \"address\": \"151.101.4.34\",\n" +
                "            \"users\": [\n" +
                "              {\n" +
                "                \"encryption\": \"none\",\n" +
                "                \"level\": 8,\n" +
                "                \"email\": \"\",\n" +
                "                \"flow\": \"\",\n" +
                "                \"id\": \"1e3a2038-e4c7-402d-a831-73e0b4e4bbf6\"\n" +
                "              }\n" +
                "            ]\n" +
                "          }\n" +
                "        ]\n" +
                "      },\n" +
                "      \"mux\": {\n" +
                "        \"enabled\": false,\n" +
                "        \"xudpConcurrency\": 128,\n" +
                "        \"concurrency\": 50,\n" +
                "        \"xudpProxyUDP443\": \"allow\"\n" +
                "      },\n" +
                "      \"streamSettings\": {\n" +
                "        \"wsSettings\": {\n" +
                "          \"path\": \"/google.com\",\n" +
                "          \"headers\": {\n" +
                "            \"host\": \"Leonfinland.org\"\n" +
                "          }\n" +
                "        },\n" +
                "        \"security\": \"tls\",\n" +
                "        \"tlsSettings\": {\n" +
                "          \"serverName\": \"yandex.ru\",\n" +
                "          \"allowInsecure\": true,\n" +
                "          \"alpn\": [],\n" +
                "          \"fingerprint\": \"chrome\"\n" +
                "        },\n" +
                "        \"network\": \"ws\"\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"tag\": \"fragment\",\n" +
                "      \"protocol\": \"freedom\",\n" +
                "      \"settings\": {\n" +
                "        \"userLevel\": 8,\n" +
                "        \"fragment\": {\n" +
                "          \"interval\": \"10-100\",\n" +
                "          \"length\": \"80-250\",\n" +
                "          \"packets\": \"tlshello\"\n" +
                "        }\n" +
                "      },\n" +
                "      \"streamSettings\": {\n" +
                "        \"sockopt\": {\n" +
                "          \"tcpNoDelay\": true\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  ],\n" +
                "  \"api\": {\n" +
                "    \"tag\": \"api\",\n" +
                "    \"services\": [\n" +
                "      \"StatsService\"\n" +
                "    ]\n" +
                "  },\n" +
                "  \"dns\": {\n" +
                "    \"disableFallback\": true,\n" +
                "    \"tag\": \"dnsQuery\",\n" +
                "    \"disableFallbackIfMatch\": true,\n" +
                "    \"servers\": [\n" +
                "      {\n" +
                "        \"skipFallback\": false,\n" +
                "        \"address\": \"8.8.8.8\"\n" +
                "      }\n" +
                "    ],\n" +
                "    \"queryStrategy\": \"UseIP\",\n" +
                "    \"disableCache\": true\n" +
                "  },\n" +
                "  \"stats\": {},\n" +
                "  \"routing\": {\n" +
                "    \"domainStrategy\": \"AsIs\",\n" +
                "    \"balancers\": [],\n" +
                "    \"rules\": [\n" +
                "      {\n" +
                "        \"inboundTag\": [\n" +
                "          \"api\"\n" +
                "        ],\n" +
                "        \"type\": \"field\",\n" +
                "        \"outboundTag\": \"api\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"inboundTag\": [\n" +
                "          \"inDns\"\n" +
                "        ],\n" +
                "        \"type\": \"field\",\n" +
                "        \"outboundTag\": \"outDns\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"inboundTag\": [\n" +
                "          \"dnsQuery\"\n" +
                "        ],\n" +
                "        \"type\": \"field\",\n" +
                "        \"outboundTag\": \"proxy\"\n" +
                "      }\n" +
                "    ]\n" +
                "  },\n" +
                "  \"policy\": {\n" +
                "    \"levels\": {\n" +
                "      \"8\": {\n" +
                "        \"bufferSize\": 0,\n" +
                "        \"handshake\": 4,\n" +
                "        \"downlinkOnly\": 1,\n" +
                "        \"uplinkOnly\": 1,\n" +
                "        \"statsUserDownlink\": false,\n" +
                "        \"statsUserUplink\": false,\n" +
                "        \"connIdle\": 30\n" +
                "      }\n" +
                "    },\n" +
                "    \"system\": {\n" +
                "      \"statsOutboundDownlink\": true,\n" +
                "      \"statsInboundDownlink\": true,\n" +
                "      \"statsOutboundUplink\": true,\n" +
                "      \"statsInboundUplink\": true\n" +
                "    }\n" +
                "  },\n" +
                "  \"transport\": {}\n" +
                "}"
    }
}