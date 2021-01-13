package br.com.fiap.mytransport.data.remote.mapper

import br.com.fiap.mytransport.domain.entity.Onibus
import br.com.fiap.mytransport.model.OnibusPayload

object BusPayloadMapper {
    fun map(lsOnibus: List<OnibusPayload>) = lsOnibus.map { map(it) }
    fun map(onibus: OnibusPayload) = Onibus(
        onibus.numero,
        onibus.ativo,
        onibus.favorito,
        onibus.pontos,
    )
}