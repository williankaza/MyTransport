package br.com.fiap.mytransport.domain.entity

import br.com.fiap.mytransport.model.Pontos
import com.google.gson.annotations.SerializedName

data class Onibus(
    @SerializedName("numero") val numero: String,
    @SerializedName("ativo") val ativo: Boolean,
    val lsPontos: List<Pontos>
)