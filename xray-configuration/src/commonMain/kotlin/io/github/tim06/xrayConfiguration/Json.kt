package io.github.tim06.xrayConfiguration

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.ClassDiscriminatorMode
import kotlinx.serialization.json.Json

@OptIn(ExperimentalSerializationApi::class)
internal val json = Json {
    explicitNulls = false
    encodeDefaults = true
    ignoreUnknownKeys = true
    prettyPrint = true
    classDiscriminatorMode = ClassDiscriminatorMode.NONE
    coerceInputValues = true
}