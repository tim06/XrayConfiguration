package io.github.tim06.xrayConfiguration.serializer

import io.github.tim06.xrayConfiguration.Protocol
import io.github.tim06.xrayConfiguration.inbounds.Inbound
import io.github.tim06.xrayConfiguration.inbounds.Sniffing
import io.github.tim06.xrayConfiguration.inbounds.settings.DokoDemoInboundConfigurationObject
import io.github.tim06.xrayConfiguration.inbounds.settings.HttpInboundConfigurationObject
import io.github.tim06.xrayConfiguration.inbounds.settings.ShadowsocksInboundConfigurationObject
import io.github.tim06.xrayConfiguration.inbounds.settings.SocksInboundConfigurationObject
import io.github.tim06.xrayConfiguration.inbounds.settings.TrojanInboundConfigurationObject
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonEncoder
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.encodeToJsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.put

// Кастомный десериализатор для InboundObject
object InboundObjectSerializer : KSerializer<Inbound> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("InboundObject") {
        element<String>("listen")
        element<Int>("port")
        element<String>("protocol")
        element<JsonObject>("settings")
        element<Sniffing>("sniffing")
        element<String>("tag")
    }

    override fun serialize(encoder: Encoder, value: Inbound) {
        val jsonEncoder = encoder as? JsonEncoder ?: throw SerializationException("This class can be saved only by JSON")
        val jsonObject = buildJsonObject {
            put("listen", value.listen)
            put("port", value.port)
            put("protocol", jsonEncoder.json.encodeToJsonElement(value.protocol))
            put("settings", jsonEncoder.json.encodeToJsonElement(value.settings))
            value.sniffing?.let { put("sniffing", jsonEncoder.json.encodeToJsonElement(it)) }
            put("tag", value.tag)
        }
        jsonEncoder.encodeJsonElement(jsonObject)
    }

    override fun deserialize(decoder: Decoder): Inbound {
        val jsonDecoder = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by JSON")
        val jsonObject = jsonDecoder.decodeJsonElement().jsonObject
        val intermediate = jsonDecoder.json.decodeFromJsonElement<InboundObjectIntermediate>(jsonObject)

        val settings = intermediate.settings?.let { settings ->
            when (intermediate.protocol) {
                Protocol.SOCKS -> jsonDecoder.json.decodeFromJsonElement<SocksInboundConfigurationObject>(settings)
                Protocol.HTTP -> jsonDecoder.json.decodeFromJsonElement<HttpInboundConfigurationObject>(settings)
                Protocol.TROJAN -> jsonDecoder.json.decodeFromJsonElement<TrojanInboundConfigurationObject>(settings)
                Protocol.SHADOWSOCKS -> jsonDecoder.json.decodeFromJsonElement<ShadowsocksInboundConfigurationObject>(settings)
                Protocol.DOKODEMO -> jsonDecoder.json.decodeFromJsonElement<DokoDemoInboundConfigurationObject>(settings)
                else -> throw SerializationException("Unknown protocol type: ${intermediate.protocol}")
            }
        }

        return Inbound(
            listen = intermediate.listen,
            port = intermediate.port,
            protocol = intermediate.protocol,
            settings = settings,
            sniffing = intermediate.sniffing,
            tag = intermediate.tag
        )
    }
}

@Serializable
private data class InboundObjectIntermediate(
    val listen: String? = null,
    val port: Int? = null,
    val protocol: Protocol? = null,
    val settings: JsonObject? = null,
    val sniffing: Sniffing? = null,
    val tag: String
)