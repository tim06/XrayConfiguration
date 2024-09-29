package io.github.tim06

import io.github.tim06.xrayConfiguration.Protocol
import io.github.tim06.xrayConfiguration.XrayConfiguration.Companion.addMinimalSettings
import io.github.tim06.xrayConfiguration.outbounds.settings.VlessOutboundConfigurationObject
import io.github.tim06.xrayConfiguration.parser.parse
import io.github.tim06.xrayConfiguration.parser.toConfiguration
import io.github.tim06.xrayConfiguration.settings.Security
import kotlin.test.*

class VlessUriParserTest {

    @Test
    fun uriToConfigParser() {
        val source = "vless://fc4f7757-fe52-4e9a-8dc1-91ddfce777c7@89.22.225.235:443?type=tcp&security=none#dgqj0gwx"
        val outbound = parse(source)
        assertNotNull(outbound)

        val outboundSettings = outbound.settings
        assertNotNull(outboundSettings)
        assertEquals(outbound.protocol, Protocol.VLESS)
        assertIs<VlessOutboundConfigurationObject>(outboundSettings)

        assertEquals(outbound.streamSettings?.security, Security.NONE)

        assertNull(outbound.streamSettings?.tlsSettings)
        assertNull(outbound.streamSettings?.wsSettings)
        assertNull(outbound.streamSettings?.realitySettings)

        val configuration = outbound.toConfiguration()
        val fullConfiguration = configuration.addMinimalSettings()
        ensureSingleOutbound(fullConfiguration)
    }

    @Test
    fun uriTlsToConfigParser() {
        val source = "vless://05519058-d2ac-4f28-9e4a-2b2a1386749e@107.23.160.23:22222?security=tls&encryption=none&sni=telegram-channel-vlessconfig.sohala.uk&type=ws&host=telegram-channel-vlessconfig.sohala.uk&path=%2ftelegram-channel-vlessconfig-ws#%f0%9f%87%ba%f0%9f%87%b8%20US%20129"
        val outbound = parse(source)
        assertNotNull(outbound)

        val outboundSettings = outbound.settings
        assertNotNull(outboundSettings)
        assertEquals(outbound.protocol, Protocol.VLESS)
        assertIs<VlessOutboundConfigurationObject>(outboundSettings)

        assertEquals(outbound.streamSettings?.security, Security.TLS)

        assertNull(outbound.streamSettings?.tcpSettings)
        assertNull(outbound.streamSettings?.realitySettings)
        assertNotNull(outbound.streamSettings?.wsSettings)
        assertNotNull(outbound.streamSettings?.tlsSettings)

        val configuration = outbound.toConfiguration()
        val fullConfiguration = configuration.addMinimalSettings()
        ensureSingleOutbound(fullConfiguration)
    }

    @Test
    fun uriRealityToConfigParser() {
        val source = "vless://859b7d71-a9fc-4234-8e03-42eb876a170a@51.89.244.102:26703?type=tcp&security=reality&pbk=jGmjU0vrRXCnIwn0rVatYKf5ihWZhMvqidp2ceY1QAM&fp=chrome&sni=foug.ieb&sid=8c01e365&spx=%2F&flow=xtls-rprx-vision#e3k"
        val outbound = parse(source)
        assertNotNull(outbound)

        val outboundSettings = outbound.settings
        assertNotNull(outboundSettings)
        assertEquals(outbound.protocol, Protocol.VLESS)
        assertIs<VlessOutboundConfigurationObject>(outboundSettings)

        assertEquals(outbound.streamSettings?.security, Security.REALITY)

        assertNull(outbound.streamSettings?.wsSettings)
        assertNull(outbound.streamSettings?.tlsSettings)
        assertNotNull(outbound.streamSettings?.realitySettings)

        val configuration = outbound.toConfiguration()
        val fullConfiguration = configuration.addMinimalSettings()
        ensureSingleOutbound(fullConfiguration)
    }
}