package io.github.tim06.xrayConfiguration.parser

import io.github.tim06.xrayConfiguration.Protocol
import io.github.tim06.xrayConfiguration.inbounds.settings.Method
import io.github.tim06.xrayConfiguration.outbounds.Outbound
import io.github.tim06.xrayConfiguration.outbounds.settings.ShadowsocksOutboundConfigurationObject
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

@OptIn(ExperimentalEncodingApi::class)
fun shadowsocks(uri: String, tag: String = "proxy"): Outbound {
    val paramsBase64 = uri.substringAfter("//").substringBeforeLast("@")
    val host = uri.substringAfter("@").substringBeforeLast(":")
    val port = uri.substringAfterLast(":").substringBeforeLast("#")

    val decoded = Base64.Default.decode(paramsBase64).decodeToString()
    val params = decoded.split(":")
    val method = params.getOrNull(0)?.let { runCatching { Method.find(it) }.getOrNull() }
    val password = params.getOrNull(1).orEmpty()

    return Outbound(
        protocol = Protocol.SHADOWSOCKS,
        settings = ShadowsocksOutboundConfigurationObject(
            servers = listOf(
                ShadowsocksOutboundConfigurationObject.Server(
                    address = host,
                    method = requireNotNull(method),
                    password = password,
                    port = port.toIntOrNull() ?: 443
                )
            )
        ),
        tag = tag
    )
}