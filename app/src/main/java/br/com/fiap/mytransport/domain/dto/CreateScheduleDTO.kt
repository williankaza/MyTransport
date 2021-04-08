package br.com.fiap.mytransport.domain.dto

import com.google.gson.annotations.SerializedName

data class CreateScheduleDTO(
    @SerializedName("dataAgendamento") var dataAgendamento: String,
    @SerializedName("destinoGeo") var destinoGeo: CreateGeoLocation,
    @SerializedName("origemGeo") var origemGeo: CreateGeoLocation,
    @SerializedName("usuarioUid") var usuarioUid: String
)

data class CreateGeoLocation(
    @SerializedName("latitude") var latitude: String,
    @SerializedName("longitude") var longitude: String
)