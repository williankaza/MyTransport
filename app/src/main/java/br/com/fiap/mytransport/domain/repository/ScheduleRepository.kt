package br.com.fiap.mytransport.domain.repository

import br.com.fiap.mytransport.domain.dto.CreateScheduleDTO
import br.com.fiap.mytransport.domain.dto.UpdateScheduleDTO
import br.com.fiap.mytransport.domain.entity.Agendamento
import okhttp3.ResponseBody

interface ScheduleRepository {
    fun pesquisarTudo(
            idUsuario: String,
            onComplete: (List<Agendamento>?) -> Unit,
            onError: (Throwable) -> Unit
    )

    fun pesquisarUmaAgendamento(
            idUsuario: String,
            idAgendamento: String,
            onComplete: (Agendamento?) -> Unit,
            onError: (Throwable) -> Unit
    )

    fun criarAgendamento(
            dtoCreateAgendamento: CreateScheduleDTO,
            onComplete: (Agendamento?) -> Unit,
            onError: (Throwable) -> Unit
    )

    fun atualizarAgendamento(
            idUsuario: String,
            idAgendamento: String,
            updateScheduleDTO: UpdateScheduleDTO,
            onComplete: (Agendamento?) -> Unit,
            onError: (Throwable) -> Unit
    )

    fun deletarAgendamento(
            idUsuario: String,
            idAgendamento: String,
            onComplete: (Throwable) -> Unit,
            onError: (Throwable) -> Unit
    )
}