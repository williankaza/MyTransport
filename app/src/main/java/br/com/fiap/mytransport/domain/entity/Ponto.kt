package br.com.fiap.mytransport.domain.entity

import com.google.gson.annotations.SerializedName

data class Ponto(
    @SerializedName("sequencial") val sequencial: Number,
    @SerializedName("latitude") val latitude: Float,
    @SerializedName("longitude") val longitude: Float,
    @SerializedName("ativo") val ativo: Boolean
)
