package io.github.tim06.xrayConfiguration

import kotlinx.serialization.json.Json

internal val json = Json {
    explicitNulls = false
    encodeDefaults = false
    ignoreUnknownKeys = true
}