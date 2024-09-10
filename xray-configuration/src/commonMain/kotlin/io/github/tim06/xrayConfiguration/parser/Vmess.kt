package io.github.tim06.xrayConfiguration.parser

import io.github.tim06.xrayConfiguration.Protocol
import io.github.tim06.xrayConfiguration.XrayConfiguration
import io.github.tim06.xrayConfiguration.json
import io.github.tim06.xrayConfiguration.outbounds.Outbound
import io.github.tim06.xrayConfiguration.outbounds.settings.VmessOutboundConfigurationObject
import io.github.tim06.xrayConfiguration.outbounds.settings.VmessOutboundConfigurationObject.Server
import io.github.tim06.xrayConfiguration.settings.Network
import io.github.tim06.xrayConfiguration.settings.Security
import io.github.tim06.xrayConfiguration.settings.StreamSettings
import io.github.tim06.xrayConfiguration.settings.tls.Tls
import io.github.tim06.xrayConfiguration.settings.ws.Ws
import kotlinx.serialization.Serializable
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

@OptIn(ExperimentalEncodingApi::class)
fun vmess(uri: String): XrayConfiguration {
    val encoded = uri.substringAfter("://")
    val decoded = Base64.Default.decode(encoded).decodeToString()
    val model = json.decodeFromString<TempVmess>(decoded)

    return XrayConfiguration(
        outbounds = listOf(
            Outbound(
                protocol = Protocol.VMESS,
                settings = VmessOutboundConfigurationObject(
                    vnext = listOf(
                        Server(
                            address = model.add.orEmpty(),
                            port = model.port ?: FALLBACK_PORT,
                            users = listOf(
                                Server.User(
                                    id = model.id.orEmpty(),
                                    security = (model.scy ?: model.security)?.let { Server.Security.find(it) }
                                        ?: Server.Security.AUTO,
                                    level = 8
                                )
                            )
                        )
                    )
                ),
                streamSettings = StreamSettings(
                    network = Network.WS,
                    security = Security.TLS,
                    wsSettings = Ws(
                        headers = mapOf(
                            "Host" to model.host.orEmpty(),
                        ),
                        path = model.path.orEmpty()
                    ),
                    tlsSettings = Tls(
                        allowInsecure = true
                    ).takeIf { model.tls != null }
                ),
                tag = "proxy"
            )
        )
    )
}

@Serializable
private data class TempVmess(
    val add: String? = null,
    val aid: Int? = null,
    val host: String? = null,
    val port: Int? = null,
    val id: String? = null,
    val net: String? = null,
    val path: String? = null,
    val ps: String? = null,
    val tls: String? = null,
    val sni: String? = null,
    val type: String? = null,
    val security: String? = null,
    val scy: String? = null,
)