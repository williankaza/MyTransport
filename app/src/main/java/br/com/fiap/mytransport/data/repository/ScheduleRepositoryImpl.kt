package br.com.fiap.mytransport.data.repository

import android.content.res.Resources
import br.com.fiap.mytransport.R
import br.com.fiap.mytransport.data.remote.ScheduleService
import br.com.fiap.mytransport.domain.dto.CreateScheduleDTO
import br.com.fiap.mytransport.domain.dto.UpdateScheduleDTO
import br.com.fiap.mytransport.domain.entity.Agendamento
import br.com.fiap.mytransport.domain.repository.ScheduleRepository
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ScheduleRepositoryImpl(
        val scheduleService: ScheduleService?
): ScheduleRepository {
    override fun pesquisarTudo(
            idUsuario: String,
            onComplete: (List<Agendamento>?) -> Unit,
            onError: (Throwable) -> Unit) {
        scheduleService
                ?.getSchedulesByUserUid(idUsuario)
                ?.enqueue(object: Callback<List<Agendamento>>{
                    override fun onResponse(
                            call: Call<List<Agendamento>>,
                            response: Response<List<Agendamento>>) {
                        if (response.isSuccessful){
                            val schedulePayload = response.body()

                            if (schedulePayload == null){
                                onError(Throwable(Resources.getSystem().getString(R.string.schRepo_not_found)))
                            } else {
                                onComplete(schedulePayload)
                            }
                        } else {
                            onError(Throwable(Resources.getSystem().getString(R.string.schRepo_not_found)))
                        }
                    }
                    override fun onFailure(
                            call: Call<List<Agendamento>>,
                            t: Throwable) {
                        onError(t)
                    }
                })
    }

    override fun pesquisarUmaAgendamento(
            idUsuario: String,
            idAgendamento: String,
            onComplete: (Agendamento?) -> Unit,
            onError: (Throwable) -> Unit) {
        scheduleService
                ?.getSchedulesByUserUid(idUsuario)
                ?.enqueue(object: Callback<List<Agendamento>>{
                    override fun onResponse(call: Call<List<Agendamento>>, response: Response<List<Agendamento>>) {
                        if (response.isSuccessful){
                            val schedulePayload = response.body()

                            if (schedulePayload == null){
                                onError(Throwable("No schedule found!"))
                            } else {
                                val scheduleEncontrado = schedulePayload.find { agendamento ->  agendamento.idAgendamento == idAgendamento }
                                if (scheduleEncontrado == null){
                                    onError(Throwable("Schedule was not found"))
                                } else {
                                    onComplete(scheduleEncontrado)
                                }
                            }
                        } else {
                            onError(Throwable("No schedule found!"))
                        }
                    }
                    override fun onFailure(call: Call<List<Agendamento>>, t: Throwable) {
                        onError(t)
                    }
                })
    }

    override fun criarAgendamento(
            dtoCreateAgendamento: CreateScheduleDTO,
            onComplete: (Agendamento?) -> Unit,
            onError: (Throwable) -> Unit) {
        scheduleService
                ?.createNewSchedule(dtoCreateAgendamento)
                ?.enqueue(object : Callback<Agendamento>{
                    override fun onResponse(call: Call<Agendamento>, response: Response<Agendamento>) {
                        if (response.isSuccessful){
                            val schedulePayload = response.body()

                            if (schedulePayload == null){
                                onError(Throwable("Error creating schedule"))
                            } else {
                                onComplete(schedulePayload)
                            }
                        } else {
                            var responseError = response.errorBody()?.string()
                            if (responseError != null){
                                var msgError = JSONObject(responseError)
                                onError(Throwable(msgError["message"].toString()))
                            } else {
                                onError(Throwable("Error creating schedule"))
                            }
                        }
                    }
                    override fun onFailure(call: Call<Agendamento>, t: Throwable) {
                        onError(t)
                    }
                })
    }

    override fun atualizarAgendamento(
            idUsuario: String,
            idAgendamento: String,
            updateScheduleDTO: UpdateScheduleDTO,
            onComplete: (Agendamento?) -> Unit,
            onError: (Throwable) -> Unit) {
        scheduleService
                ?.updateScheduleById(idUsuario, idAgendamento, updateScheduleDTO)
                ?.enqueue(object : Callback<Agendamento>{
                    override fun onResponse(call: Call<Agendamento>, response: Response<Agendamento>) {
                        if (response.isSuccessful){
                            val schedulePayload = response.body()

                            if (schedulePayload == null){
                                onError(Throwable("Error updating schedule"))
                            } else {
                                onComplete(schedulePayload)
                            }
                        } else {
                            var responseError = response.errorBody()?.string()
                            if (responseError != null){
                                var msgError = JSONObject(responseError)
                                onError(Throwable(msgError["message"].toString()))
                            } else {
                                onError(Throwable("Error updating schedule"))
                            }
                        }
                    }

                    override fun onFailure(call: Call<Agendamento>, t: Throwable) {
                        onError(t)
                    }

                })
    }

    override fun deletarAgendamento(
            idUsuario: String,
            idAgendamento: String,
            onComplete: (Throwable) -> Unit,
            onError: (Throwable) -> Unit) {
        scheduleService
                ?.deleteSchedule(idUsuario,idAgendamento)
                ?.enqueue(object : Callback<ResponseBody>{
                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        if (response.isSuccessful){
                            onComplete(Throwable("Successfully deleted"))
                        } else {
                            onError(Throwable("Error deleting schedule"))
                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        onError(t)
                    }

                })
    }
}