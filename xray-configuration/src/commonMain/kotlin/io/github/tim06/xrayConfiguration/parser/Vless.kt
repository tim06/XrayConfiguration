package io.github.tim06.xrayConfiguration.parser

import io.github.tim06.xrayConfiguration.Protocol
import io.github.tim06.xrayConfiguration.XrayConfiguration
import io.github.tim06.xrayConfiguration.outbounds.Outbound
import io.github.tim06.xrayConfiguration.outbounds.settings.VlessOutboundConfigurationObject
import io.github.tim06.xrayConfiguration.outbounds.settings.VlessOutboundConfigurationObject.Server
import io.github.tim06.xrayConfiguration.outbounds.settings.VlessOutboundConfigurationObject.Server.User
import io.github.tim06.xrayConfiguration.outbounds.settings.VlessOutboundConfigurationObject.Server.User.Flow
import io.github.tim06.xrayConfiguration.settings.Network
import io.github.tim06.xrayConfiguration.settings.Security
import io.github.tim06.xrayConfiguration.settings.StreamSettings
import io.github.tim06.xrayConfiguration.settings.reality.Reality
import io.github.tim06.xrayConfiguration.settings.tcp.Tcp
import io.github.tim06.xrayConfiguration.settings.tcp.header.Header
import io.github.tim06.xrayConfiguration.settings.tls.Tls
import io.github.tim06.xrayConfiguration.settings.ws.Ws
import net.thauvin.erik.urlencoder.UrlEncoderUtil

fun vless(uri: String): XrayConfiguration {
    val id = uri.substringAfter("://").substringBefore("@")
    val host = uri.substringAfter("@").substringBeforeLast(":")
    val port = uri.substringAfterLast(":").substringBefore("?")

    val params = uri.substringAfter("?").substringBeforeLast("#").split('&').associate { param ->
        val split = param.split('=')
        split.first() to split.last()
    }
    val type = params["type"]?.let { runCatching { Network.find(it) }.getOrNull() }
    val security = params["security"]?.let { runCatching { Security.find(it) }.getOrNull() } ?: Security.NONE
    val flow = params["flow"]?.let { runCatching { Flow.find(it) }.getOrNull() } ?: Flow.EMPTY

    val pbk = params["pbk"]
    val fp = params["fp"]
    val sid = params["sid"]
    val spx = params["spx"]?.let { UrlEncoderUtil.decode(it) }

    val sni = params["sni"]
    val alpn = params["alpn"]

    val tlsHost = params["host"]
    val tlsPath = params["path"]

    return XrayConfiguration(
        outbounds = listOf(
            Outbound(
                protocol = Protocol.VLESS,
                settings = VlessOutboundConfigurationObject(
                    vnext = listOf(
                        Server(
                            address = host,
                            port = port.toIntOrNull() ?: FALLBACK_PORT,
                            users = listOf(
                                User(
                                    id = id,
                                    flow = flow,
                                    encryption = Security.NONE,
                                    level = 8,
                                )
                            ),
                        )
                    )
                ),
                streamSettings = StreamSettings(
                    network = type,
                    security = security,
                    realitySettings = Reality(
                        show = false,
                        serverName = sni.orEmpty(),
                        fingerprint = fp.orEmpty(),
                        publicKey = pbk.orEmpty(),
                        shortId = sid.orEmpty(),
                        spiderX = spx.orEmpty(),
                    ).takeIf { security == Security.REALITY },
                    tlsSettings = Tls(
                        allowInsecure = true,
                        serverName = sni,
                    ).takeIf { security == Security.TLS },
                    tcpSettings = Tcp(
                        header = Header(type = "none")
                    ).takeIf { type == Network.TCP },
                    wsSettings = Ws(
                        headers = if (tlsHost.isNullOrEmpty()) {
                            emptyMap()
                        } else {
                            mapOf(
                                "Host" to tlsHost
                            )
                        },
                        path = tlsPath.orEmpty()
                    ).takeIf { type == Network.WS }
                ),
                tag = "proxy"
            )
        )
    )
}
