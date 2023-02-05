package com.example.weather_dvt.core.network.model.weather.util

import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object DayOfWeekSerializer : KSerializer<DayOfWeek> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("DayOfWeek", PrimitiveKind.LONG)

    override fun serialize(encoder: Encoder, value: DayOfWeek) {
        return encoder.encodeLong(value.mill)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun deserialize(decoder: Decoder): DayOfWeek {
        return decoder.decodeLong().asDayOfWeek()
    }
}