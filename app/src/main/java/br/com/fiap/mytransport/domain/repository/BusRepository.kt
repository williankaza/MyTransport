package br.com.fiap.mytransport.domain.repository

import br.com.fiap.mytransport.domain.entity.Onibus

interface BusRepository {
    fun pesquisarTudo(
        onComplete: (List<Onibus>?) -> Unit,
        onError: (Throwable) -> Unit
    )

    fun pesquisarUmaLinha(
        id: String,
        onComplete: (Onibus?) -> Unit,
        onError: (Throwable) -> Unit
    )

    fun atualizarFavorito(
        id: String,
        onComplete: (Onibus?) -> Unit,
        onError: (Throwable) -> Unit
    )
}