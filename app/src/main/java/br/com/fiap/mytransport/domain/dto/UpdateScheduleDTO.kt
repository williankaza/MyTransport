package br.com.fiap.mytransport.domain.dto

import com.google.gson.annotations.SerializedName

data class UpdateScheduleDTO(
        @SerializedName("dataAgendamento") val dataAgendamento: String,
        @SerializedName("destinoGeo") val destinoGeo: UpdateGeoLocation,
        @SerializedName("origemGeo") val origemGeo: UpdateGeoLocation,
        @SerializedName("idAgendamento") val idAgendamento: String
)

data class UpdateGeoLocation(
        @SerializedName("latitude") val latitude: String,
        @SerializedName("longitude") val longitude: String
)