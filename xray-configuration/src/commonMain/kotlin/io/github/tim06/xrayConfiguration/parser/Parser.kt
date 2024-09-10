package io.github.tim06.xrayConfiguration.parser

import io.github.tim06.xrayConfiguration.Protocol
import io.github.tim06.xrayConfiguration.XrayConfiguration

fun parse(uri: String): XrayConfiguration? {
    val protocol = uri.takeWhile { protocol -> protocol != ':' }
        .let { runCatching { Protocol.find(it) }.getOrNull() }

    val name = uri.substringAfter("#")

    return protocol?.findOutBound(uri)
}

fun Protocol.findOutBound(uri: String): XrayConfiguration {
    return when (this) {
        Protocol.BLACKHOLE -> TODO()
        Protocol.DNS -> TODO()
        Protocol.DOKODEMO -> TODO()
        Protocol.HTTP -> TODO()
        Protocol.SHADOWSOCKS,
        Protocol.SS -> shadowsocks(uri)

        Protocol.SOCKS -> TODO()
        Protocol.TROJAN -> trojan(uri)
        Protocol.VMESS -> vmess(uri)
        Protocol.VLESS -> vless(uri)
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

fun vmess() {

}

fun ws() {

}

fun freedom() {

}

internal const val FALLBACK_PORT = 443
internal const val USER_AGENT1 =
    "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36"
internal const val USER_AGENT2 =
    "Mozilla/5.0 (iPhone; CPU iPhone OS 10_0_2 like Mac OS X) AppleWebKit/601.1 (KHTML, like Gecko) CriOS/53.0.2785.109 Mobile/14A456 Safari/601.1.46"