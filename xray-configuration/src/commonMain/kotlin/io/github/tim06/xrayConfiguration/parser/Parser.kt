package io.github.tim06.xrayConfiguration.parser

import io.github.tim06.xrayConfiguration.Protocol
import io.github.tim06.xrayConfiguration.XrayConfiguration
import io.github.tim06.xrayConfiguration.outbounds.Outbound

fun parse(uris: List<String>): List<Outbound> {
    val outbounds: List<Outbound> = uris.mapIndexedNotNull { index, uri ->
        runCatching { parse(uri = uri, tag = "outbound-$index") }.getOrNull()
    }
    return outbounds
}

fun parseToConfiguration(uris: List<String>): XrayConfiguration {
    val outbounds: List<Outbound> = uris.mapIndexedNotNull { index, uri ->
        runCatching { parse(uri = uri, tag = "outbound-$index") }.getOrNull()
    }
    return outbounds.toConfiguration()
}

fun parse(uri: String, tag: String = "proxy"): Outbound? {
    val protocol: Protocol? = uri.takeWhile { protocol -> protocol != ':' }
        .let { runCatching { Protocol.find(it) }.getOrNull() }

    return protocol?.findOutbound(uri = uri, tag = tag)
}

fun Protocol.findOutbound(uri: String, tag: String = "proxy"): Outbound {
    return when (this) {
        Protocol.BLACKHOLE -> TODO()
        Protocol.DNS -> TODO()
        Protocol.DOKODEMO -> TODO()
        Protocol.HTTP -> TODO()
        Protocol.SHADOWSOCKS,
        Protocol.SS, -> shadowsocks(uri = uri, tag = tag)

        Protocol.SOCKS -> TODO()
        Protocol.TROJAN -> trojan(uri = uri, tag = tag)
        Protocol.VMESS -> vmess(uri = uri, tag = tag)
        Protocol.VLESS -> vless(uri = uri, tag = tag)
        Protocol.WS -> TODO()
        Protocol.FREEDOM -> TODO()
    }
}

private fun blackhole() {

}

private fun dns() {

}

private fun dokodemo() {

}

fun http() {

}

fun socks() {

}

fun ws() {

}

fun freedom() {

}

fun Outbound.toConfiguration(): XrayConfiguration {
    return listOf(this).toConfiguration()
}

fun List<Outbound>.toConfiguration(): XrayConfiguration {
    return XrayConfiguration(
        outbounds = this,
    )
}

internal const val FALLBACK_PORT: Int = 443
internal const val USER_AGENT1: String =
    "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36"
internal const val USER_AGENT2: String =
    "Mozilla/5.0 (iPhone; CPU iPhone OS 10_0_2 like Mac OS X) AppleWebKit/601.1 (KHTML, like Gecko) CriOS/53.0.2785.109 Mobile/14A456 Safari/601.1.46"