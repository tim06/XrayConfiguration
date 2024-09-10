package io.github.tim06.xrayConfiguration.serializer

import io.github.tim06.xrayConfiguration.dns.DnsHosts
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PolymorphicKind
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonEncoder
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.jsonPrimitive

object DnsHostsSerializer : KSerializer<DnsHosts> {
    @OptIn(InternalSerializationApi::class, ExperimentalSerializationApi::class)
    override val descriptor: SerialDescriptor =
        buildSerialDescriptor("DnsHosts", PolymorphicKind.SEALED) {
            element<String>("DnsHostsServer")
            element<List<String>>("DnsHostsServers")
        }

    override fun serialize(encoder: Encoder, value: DnsHosts) {
        val jsonEncoder = encoder as? JsonEncoder ?: error("This class can be saved only by JSON")
        val jsonElement = when (value) {
            is DnsHosts.DnsHostsServer -> JsonPrimitive(value.server)
            is DnsHosts.DnsHostsServers -> JsonArray(value.servers.map { JsonPrimitive(it) })
        }
        jsonEncoder.encodeJsonElement(jsonElement)
    }

    override fun deserialize(decoder: Decoder): DnsHosts {
        val jsonDecoder = decoder as? JsonDecoder ?: error("This class can be loaded only by JSON")
        val jsonElement = jsonDecoder.decodeJsonElement()
        return when (jsonElement) {
            is JsonPrimitive -> DnsHosts.DnsHostsServer(jsonElement.content)
            is JsonArray -> DnsHosts.DnsHostsServers(jsonElement.map { it.jsonPrimitive.content })
            else -> error("Unexpected JSON element")
        }
    }
}