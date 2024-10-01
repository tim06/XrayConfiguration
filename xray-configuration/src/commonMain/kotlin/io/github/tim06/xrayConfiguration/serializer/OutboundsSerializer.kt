package io.github.tim06.xrayConfiguration.serializer

import io.github.tim06.xrayConfiguration.Protocol
import io.github.tim06.xrayConfiguration.outbounds.Mux
import io.github.tim06.xrayConfiguration.outbounds.Outbound
import io.github.tim06.xrayConfiguration.outbounds.Proxy
import io.github.tim06.xrayConfiguration.outbounds.settings.BlackholeOutboundConfigurationObject
import io.github.tim06.xrayConfiguration.outbounds.settings.DnsOutboundConfigurationObject
import io.github.tim06.xrayConfiguration.outbounds.settings.FreedomOutboundConfigurationObject
import io.github.tim06.xrayConfiguration.outbounds.settings.HttpOutboundConfigurationObject
import io.github.tim06.xrayConfiguration.outbounds.settings.ShadowsocksOutboundConfigurationObject
import io.github.tim06.xrayConfiguration.outbounds.settings.SocksOutboundConfigurationObject
import io.github.tim06.xrayConfiguration.outbounds.settings.TrojanOutboundConfigurationObject
import io.github.tim06.xrayConfiguration.outbounds.settings.VlessOutboundConfigurationObject
import io.github.tim06.xrayConfiguration.outbounds.settings.VmessOutboundConfigurationObject
import io.github.tim06.xrayConfiguration.settings.StreamSettings
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
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

object OutboundObjectSerializer : KSerializer<Outbound> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("InboundObject") {
        element<String>(elementName = "sendThrough", isOptional = true)
        element<String>(elementName = "protocol", isOptional = false)
        element<JsonObject>(elementName = "settings", isOptional = false)
        element<StreamSettings>(elementName = "streamSettings", isOptional = true)
        element<String>(elementName = "tag", isOptional = false)
        element<Proxy>(elementName = "proxySettings", isOptional = true)
        element<Mux>(elementName = "mux", isOptional = true)
    }

    override fun serialize(encoder: Encoder, value: Outbound) {
        val jsonEncoder = encoder as? JsonEncoder ?: throw SerializationException("This class can be saved only by JSON")
        val jsonObject = buildJsonObject {
            value.sendThrough?.let { put("sendThrough", it) }
            put("protocol", jsonEncoder.json.encodeToJsonElement(value.protocol))
            put("settings", jsonEncoder.json.encodeToJsonElement(value.settings))
            value.streamSettings?.let { put("streamSettings", jsonEncoder.json.encodeToJsonElement(it)) }
            put("tag", value.tag)
            value.proxySettings?.let { put("proxySettings", jsonEncoder.json.encodeToJsonElement(it)) }
            value.mux?.let { put("mux", jsonEncoder.json.encodeToJsonElement(value.mux)) }
        }
        jsonEncoder.encodeJsonElement(jsonObject)
    }

    override fun deserialize(decoder: Decoder): Outbound {
        val jsonDecoder = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by JSON")
        val jsonObject = jsonDecoder.decodeJsonElement().jsonObject
        val intermediate = jsonDecoder.json.decodeFromJsonElement<OutboundObjectIntermediate>(jsonObject)

        val settings = intermediate.settings?.let { settings ->
            when (intermediate.protocol) {
                Protocol.SOCKS -> jsonDecoder.json.decodeFromJsonElement<SocksOutboundConfigurationObject>(settings)
                Protocol.HTTP -> jsonDecoder.json.decodeFromJsonElement<HttpOutboundConfigurationObject>(settings)
                Protocol.TROJAN -> jsonDecoder.json.decodeFromJsonElement<TrojanOutboundConfigurationObject>(settings)
                Protocol.FREEDOM -> jsonDecoder.json.decodeFromJsonElement<FreedomOutboundConfigurationObject>(settings)
                Protocol.BLACKHOLE -> jsonDecoder.json.decodeFromJsonElement<BlackholeOutboundConfigurationObject>(settings)
                Protocol.VLESS -> jsonDecoder.json.decodeFromJsonElement<VlessOutboundConfigurationObject>(settings)
                Protocol.SHADOWSOCKS -> jsonDecoder.json.decodeFromJsonElement<ShadowsocksOutboundConfigurationObject>(settings)
                Protocol.DNS -> jsonDecoder.json.decodeFromJsonElement<DnsOutboundConfigurationObject>(settings)
                Protocol.VMESS -> jsonDecoder.json.decodeFromJsonElement<VmessOutboundConfigurationObject>(settings)
                else -> throw SerializationException("Unknown protocol type: ${intermediate.protocol}")
            }
        }

        return Outbound(
            sendThrough = intermediate.sendThrough,
            protocol = intermediate.protocol,
            settings = settings,
            streamSettings = intermediate.streamSettings,
            tag = intermediate.tag,
            proxySettings = intermediate.proxySettings,
            mux = intermediate.mux,
        )
    }
}

@Serializable
private data class OutboundObjectIntermediate(
    @SerialName("sendThrough")
    val sendThrough: String? = null,
    @SerialName("protocol")
    val protocol: Protocol? = null,
    @SerialName("settings")
    val settings: JsonObject? = null,
    @SerialName("streamSettings")
    val streamSettings: StreamSettings? = null,
    @SerialName("tag")
    val tag: String,
    @SerialName("proxySettings")
    val proxySettings: Proxy? = null,
    @SerialName("mux")
    val mux: Mux? = null,
)