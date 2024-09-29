package io.github.tim06

import io.github.tim06.xrayConfiguration.Protocol
import io.github.tim06.xrayConfiguration.XrayConfiguration.Companion.addMinimalSettings
import io.github.tim06.xrayConfiguration.outbounds.settings.VmessOutboundConfigurationObject
import io.github.tim06.xrayConfiguration.parser.parse
import io.github.tim06.xrayConfiguration.parser.toConfiguration
import io.github.tim06.xrayConfiguration.settings.Security
import kotlin.test.*

class VmessUriParserTest {

    @Test
    fun uriToConfigParser() {
        val source = "vmess://eyJhZGQiOiIxODUuMTQ2LjE3My45MiIsImFpZCI6IjAiLCJhbHBuIjoiIiwiZnAiOiIiLCJob3N0IjoiRGUxLnZtZXNzLnNpdGUuIiwiaWQiOiI4NjM5ZTUyYi1hMGNlLTVkNjgtYjQ2NS1iNTk0ODgxZmViNzgiLCJuZXQiOiJ3cyIsInBhdGgiOiIvdm1lc3MiLCJwb3J0IjoiODAiLCJwcyI6Itix2KfbjNqv2KfZhiB8IFZNRVNTIHwgQHByb3h5c3RvcmUxMSB8IERF8J+HqfCfh6ogfCAw77iP4oOjMe+4j+KDoyIsInNjeSI6Im5vbmUiLCJzbmkiOiIiLCJ0bHMiOiIiLCJ0eXBlIjoiIiwidiI6IjIifQ=="
        val outbound = parse(source)
        assertNotNull(outbound)

        val outboundSettings = outbound.settings
        assertNotNull(outboundSettings)
        assertEquals(outbound.protocol, Protocol.VMESS)
        assertIs<VmessOutboundConfigurationObject>(outboundSettings)

        assertEquals(outbound.streamSettings?.security, Security.TLS)

        assertNull(outbound.streamSettings?.realitySettings)
        assertNotNull(outbound.streamSettings?.wsSettings)
        assertNotNull(outbound.streamSettings?.tlsSettings)

        val configuration = outbound.toConfiguration()
        val fullConfiguration = configuration.addMinimalSettings()
        ensureSingleOutbound(fullConfiguration)
    }
}