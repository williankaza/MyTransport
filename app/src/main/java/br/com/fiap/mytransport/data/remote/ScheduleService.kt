package br.com.fiap.mytransport.data.remote

import br.com.fiap.mytransport.domain.dto.CreateScheduleDTO
import br.com.fiap.mytransport.domain.dto.UpdateScheduleDTO
import br.com.fiap.mytransport.domain.entity.Agendamento
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ScheduleService {

    @POST("/AgendamentoServices")
    fun createNewSchedule(@Body createScheduleDTO: CreateScheduleDTO): Call<Agendamento>

    //@POST("/AgendamentoServices")
    //fun createNewSchedule(@Body createScheduleDTO: CreateScheduleDTO): Call<ResponseBody>

    @GET("/AgendamentoServices/{uidUserSchedule}")
    fun getSchedulesByUserUid(@Path("uidUserSchedule") userUid: String): Call<List<Agendamento>>

    @PUT("/AgendamentoServices/{uidUserSchedule}")
    fun updateScheduleById(@Path("uidUserSchedule") userUid: String,
        @Query("idAgendamento") idAgendamento: String,
        @Body updateScheduleDTO: UpdateScheduleDTO): Call<Agendamento>

    @DELETE("AgendamentoServices/{uidUserSchedule}/{idSchedule}")
    fun deleteSchedule(@Path("uidUserSchedule") userUid: String,
        @Path("idSchedule") idSchedule: String): Call<ResponseBody>
}