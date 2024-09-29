package io.github.tim06

import io.github.tim06.xrayConfiguration.XrayConfiguration.Companion.addMinimalSettings
import io.github.tim06.xrayConfiguration.parser.parse
import io.github.tim06.xrayConfiguration.parser.toConfiguration
import kotlin.test.Test
import kotlin.test.assertTrue

class MultiConfigTest {

    @Test
    fun multiConfigParser() {
        val config1 = "vmess://eyJhZGQiOiIxODUuMTQ2LjE3My45MiIsImFpZCI6IjAiLCJhbHBuIjoiIiwiZnAiOiIiLCJob3N0IjoiRGUxLnZtZXNzLnNpdGUuIiwiaWQiOiI4NjM5ZTUyYi1hMGNlLTVkNjgtYjQ2NS1iNTk0ODgxZmViNzgiLCJuZXQiOiJ3cyIsInBhdGgiOiIvdm1lc3MiLCJwb3J0IjoiODAiLCJwcyI6Itix2KfbjNqv2KfZhiB8IFZNRVNTIHwgQHByb3h5c3RvcmUxMSB8IERF8J+HqfCfh6ogfCAw77iP4oOjMe+4j+KDoyIsInNjeSI6Im5vbmUiLCJzbmkiOiIiLCJ0bHMiOiIiLCJ0eXBlIjoiIiwidiI6IjIifQ=="
        val config2 = "vmess://eyJhZGQiOiJzYW1oaXRhc2FudGlyc2F0YWkuc2hvcCIsImFpZCI6IjAiLCJhbHBuIjoiIiwiZnAiOiIiLCJob3N0Ijoic2FtaGl0YXNhbnRpcnNhdGFpLnNob3AiLCJpZCI6ImVjNWEzODJlLTMxOGMtZWRiNC1jNDYzLTU5ZDFhYWFjNzNlMiIsIm5ldCI6IndzIiwicGF0aCI6Ii9iaXBqYXhvLyIsInBvcnQiOiI0NDMiLCJwcyI6InZtZXNzLXdzLW0xIiwic2N5IjoiYXV0byIsInNuaSI6InNhbWhpdGFzYW50aXJzYXRhaS5zaG9wIiwidGxzIjoidGxzIiwidHlwZSI6IiIsInYiOiIyIn0="
        val parsedOutbounds = parse(listOf(config1, config2))
        assertTrue(parsedOutbounds.isNotEmpty())

        val configuration = parsedOutbounds.toConfiguration().addMinimalSettings()
        ensureMultipleOutbounds(configuration)
    }
}