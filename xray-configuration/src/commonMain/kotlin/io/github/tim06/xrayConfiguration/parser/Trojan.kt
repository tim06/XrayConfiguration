package io.github.tim06.xrayConfiguration.parser

import io.github.tim06.xrayConfiguration.Protocol
import io.github.tim06.xrayConfiguration.XrayConfiguration
import io.github.tim06.xrayConfiguration.outbounds.Outbound
import io.github.tim06.xrayConfiguration.outbounds.settings.TrojanOutboundConfigurationObject
import io.github.tim06.xrayConfiguration.settings.Security
import io.github.tim06.xrayConfiguration.settings.StreamSettings
import io.github.tim06.xrayConfiguration.settings.tcp.Tcp
import io.github.tim06.xrayConfiguration.settings.tcp.header.Header
import io.github.tim06.xrayConfiguration.settings.tcp.header.Request
import io.github.tim06.xrayConfiguration.settings.tls.Tls

fun trojan(uri: String): XrayConfiguration {
    val password = uri.substringAfter("//").substringBeforeLast("@")
    val host = uri.substringAfter("@").substringBeforeLast(":")
    val port = uri.substringAfterLast(":").substringBeforeLast("?")
    val params = uri.substringAfter("?").substringBeforeLast("#").split('&').associate { param ->
        val split = param.split('=')
        split.first() to split.last()
    }
    val type = params["type"]?.let { runCatching { io.github.tim06.xrayConfiguration.settings.Network.find(it) }.getOrNull() }
    val security = params["security"]?.let { runCatching { Security.find(it) }.getOrNull() }
    val fp = params["fp"]
    val sni = params["sni"]
    val alpn = params["alpn"]

    val headerType = params["headerType"]
    val headerHost = params["host"]
    val headerPath = params["path"]
    val headerRequest = headerHost?.let { headerRequestHost ->
        Request(
            headers = mapOf(
                "Connection" to listOf("keep-alive"),
                "Host" to listOf(headerRequestHost),
                "Pragma" to listOf("no-cache"),
                "Accept-Encoding" to listOf("gzip, deflate"),
                "User-Agent" to listOf(USER_AGENT1, USER_AGENT2),
            ),
            method = "GET",
            path = headerPath?.let { listOf(it) },
            version = "1.1"
        )
    }
    return XrayConfiguration(
        outbounds = listOf(
            Outbound(
                protocol = Protocol.TROJAN,
                settings = TrojanOutboundConfigurationObject(
                    servers = listOf(
                        TrojanOutboundConfigurationObject.Server(
                            address = host,
                            port = port.toIntOrNull() ?: FALLBACK_PORT,
                            password = password,
                            level = 8
                        )
                    )
                ),
                streamSettings = StreamSettings(
                    network = type,
                    security = security,
                    tcpSettings = Tcp(
                        header = Header(
                            type = headerType ?: "none",
                            request = headerRequest
                        )
                    ),
                    tlsSettings = Tls(
                        allowInsecure = true,
                        alpn = alpn?.split(',') ?: listOf(),
                        fingerprint = fp,
                        serverName = sni,
                    ).takeIf { security == Security.TLS }
                ),
                tag = "proxy"
            )
        )
    )
}