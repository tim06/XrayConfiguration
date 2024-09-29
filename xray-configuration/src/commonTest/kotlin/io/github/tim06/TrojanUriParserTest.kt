package io.github.tim06

import io.github.tim06.xrayConfiguration.outbounds.settings.TrojanOutboundConfigurationObject
import io.github.tim06.xrayConfiguration.parser.parse
import io.github.tim06.xrayConfiguration.Protocol
import io.github.tim06.xrayConfiguration.XrayConfiguration.Companion.addMinimalSettings
import io.github.tim06.xrayConfiguration.outbounds.Outbound
import io.github.tim06.xrayConfiguration.outbounds.settings.OutboundConfigurationObject
import io.github.tim06.xrayConfiguration.parser.toConfiguration
import io.github.tim06.xrayConfiguration.settings.Security
import kotlin.test.*

class TrojanUriParserTest {

    @Test
    fun trojanUriToConfigParser() {
        val sample = "trojan://ypDt8RkT7J@38.54.2.32:41118?type=tcp&security=none#trojav2"
        val outbound: Outbound? = parse(sample)
        assertNotNull(outbound)

        val outboundSettings: OutboundConfigurationObject? = outbound.settings
        assertNotNull(outboundSettings)
        assertEquals(outbound.protocol, Protocol.TROJAN)
        assertIs<TrojanOutboundConfigurationObject>(outboundSettings)

        val server = outboundSettings.servers.first()
        assertNotNull(server)
        val host = sample.substringAfter("@").substringBeforeLast(":")
        assertEquals(server.address, host)
        val port = sample.substringAfterLast(":").substringBeforeLast("?")
        assertEquals(server.port.toString(), port)
        val password = sample.substringAfter("://").substringBeforeLast("@")
        assertEquals(server.password, password)

        val streamSettings = outbound.streamSettings
        assertNotNull(streamSettings)
        assertEquals(streamSettings.network, io.github.tim06.xrayConfiguration.settings.Network.TCP)
        assertEquals(streamSettings.security, Security.NONE)

        val streamSettingsTcpHeader = streamSettings.tcpSettings?.header
        assertNotNull(streamSettingsTcpHeader)
        assertEquals(streamSettingsTcpHeader.type, "none")
        assertNull(streamSettingsTcpHeader.request)
        assertNull(streamSettings.tlsSettings)

        val domain = sample.substringAfterLast("@").substringBefore("?")
        val configuration = outbound.toConfiguration()
        assertEquals(configuration.domain(), domain)
        val fullConfiguration = configuration.addMinimalSettings()
        ensureSingleOutbound(fullConfiguration)
    }

    @Test
    fun trojanUriWithTlsToConfigParser() {
        val sample = "trojan://ypDt8RkT7J@121.127.46.131:42118?type=tcp&security=tls&fp=random&sni=wildlydrowse.com&alpn=http%2F1.1#ttls"
        val outbound = parse(sample)
        assertNotNull(outbound)

        val outboundSettings = outbound.settings
        assertNotNull(outboundSettings)
        assertEquals(outbound.protocol, Protocol.TROJAN)
        assertIs<TrojanOutboundConfigurationObject>(outboundSettings)

        val server = outboundSettings.servers.first()
        assertNotNull(server)
        assertEquals(server.address, "121.127.46.131")
        assertEquals(server.port, 42118)
        assertEquals(server.password, "ypDt8RkT7J")

        val streamSettings = outbound.streamSettings
        assertNotNull(streamSettings)
        assertEquals(streamSettings.network, io.github.tim06.xrayConfiguration.settings.Network.TCP)
        assertEquals(streamSettings.security, Security.TLS)

        val tlsSettings = streamSettings.tlsSettings
        assertNotNull(tlsSettings)
        assertEquals(tlsSettings.fingerprint, "random")
        assertEquals(tlsSettings.serverName, "wildlydrowse.com")
        assertEquals(tlsSettings.alpn, listOf("http%2F1.1"))

        val domain = sample.substringAfterLast("@").substringBefore("?")
        val configuration = outbound.toConfiguration()
        assertEquals(configuration.domain(), domain)
        val fullConfiguration = configuration.addMinimalSettings()
        ensureSingleOutbound(fullConfiguration)
    }
}