package br.com.fiap.mytransport.domain.entity

import com.google.gson.annotations.SerializedName

data class Schedule(
        @SerializedName("idUsuario") val idUsuario: String,
        @SerializedName("dataAgendamento") var dataAgendamento: String,
        @SerializedName("idAgendamento") val idAgendamento: String,
        @SerializedName("latitudeOrigem") var latitudeOrigem: String,
        @SerializedName("longitudeOrigem") var longitudeOrigem: String,
        @SerializedName("latitudeDestino") var latitudeDestino: String,
        @SerializedName("longitudeDestino") var longitudeDestino: String
)
