package br.com.fiap.mytransport.data.remote.mapper

import br.com.fiap.mytransport.domain.entity.Agendamento
import br.com.fiap.mytransport.domain.entity.Schedule

object SchedulePayloadMapper {
    fun map(lsSchedule: List<Agendamento>) = lsSchedule.map{
        agendamento ->  Schedule(
            agendamento.usuarioUid,
            agendamento.dataAgendamentoLdt,
            agendamento.idAgendamento,
            agendamento.origemGeo.latitude,
            agendamento.origemGeo.longitude,
            agendamento.destinoGeo.latitude,
            agendamento.destinoGeo.longitude
        )
    }

    fun map(agendamento: Agendamento) = Schedule(
            agendamento.usuarioUid,
            agendamento.dataAgendamentoLdt,
            agendamento.idAgendamento,
            agendamento.origemGeo.latitude,
            agendamento.origemGeo.longitude,
            agendamento.destinoGeo.latitude,
            agendamento.destinoGeo.longitude
    )
}