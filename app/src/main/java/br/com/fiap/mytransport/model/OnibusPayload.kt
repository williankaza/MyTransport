package br.com.fiap.mytransport.model

import com.google.gson.annotations.SerializedName

data class OnibusPayload(
    @SerializedName("numero") val numero: String,
    @SerializedName("ativo") val ativo: Boolean,
    @SerializedName("favorito") val favorito: Boolean,
    val pontos: List<Pontos>
)

data class Pontos(
    @SerializedName("sequencial") val sequencial: Number,
    @SerializedName("latitude") val latitude: Float,
    @SerializedName("longitude") val longitude: Float,
    @SerializedName("ativo") val ativo: Boolean
)

