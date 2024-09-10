package io.github.tim06.xrayConfiguration.serializer

import io.github.tim06.xrayConfiguration.dns.DnsServer
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PolymorphicKind
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonEncoder
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.encodeToJsonElement

object DnsServerSerializer : KSerializer<DnsServer> {
    @OptIn(InternalSerializationApi::class, ExperimentalSerializationApi::class)
    override val descriptor: SerialDescriptor =
        buildSerialDescriptor("DnsServer", PolymorphicKind.SEALED) {
            element<String>("DirectDnsServer")
            element<DnsServer.CustomDnsServer>("CustomDnsServer")
        }

    override fun serialize(encoder: Encoder, value: DnsServer) {
        val jsonEncoder = encoder as? JsonEncoder ?: error("This class can be saved only by JSON")
        val jsonElement = when (value) {
            is DnsServer.DirectDnsServer -> JsonPrimitive(value.server)
            is DnsServer.CustomDnsServer -> jsonEncoder.json.encodeToJsonElement(value)
        }
        jsonEncoder.encodeJsonElement(jsonElement)
    }

    override fun deserialize(decoder: Decoder): DnsServer {
        val jsonDecoder = decoder as? JsonDecoder ?: error("This class can be loaded only by JSON")
        val jsonElement = jsonDecoder.decodeJsonElement()
        return when (jsonElement) {
            is JsonPrimitive -> DnsServer.DirectDnsServer(jsonElement.content)
            is JsonObject -> jsonDecoder.json.decodeFromJsonElement<DnsServer.CustomDnsServer>(
                jsonElement
            )

            else -> error("Unexpected JSON element")
        }
    }
}