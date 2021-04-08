package br.com.fiap.mytransport.domain.entity

import com.google.gson.annotations.SerializedName

data class Agendamento(
    @SerializedName("dataAgendamentoLDT") val dataAgendamentoLdt: String,
    @SerializedName("usuarioUid") val usuarioUid: String,
    @SerializedName("idAgendamento") val idAgendamento: String,
    val destinoGeo: GeoLocation,
    val origemGeo: GeoLocation,
    val dataAgendamento: DataAgendamento

)

data class GeoLocation(
    @SerializedName("latitude") val latitude: String,
    @SerializedName("longitude") val longitude: String
)

data class DataAgendamento(
    @SerializedName("seconds") val seconds: String,
    @SerializedName("nanos") val nanoseconds: String
)