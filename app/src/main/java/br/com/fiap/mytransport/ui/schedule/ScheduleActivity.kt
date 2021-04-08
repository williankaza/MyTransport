package br.com.fiap.mytransport.ui.schedule

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import br.com.fiap.mytransport.R
import br.com.fiap.mytransport.data.remote.ScheduleAPIService
import br.com.fiap.mytransport.data.remote.mapper.SchedulePayloadMapper
import br.com.fiap.mytransport.data.repository.ScheduleRepositoryImpl
import br.com.fiap.mytransport.domain.dto.CreateGeoLocation
import br.com.fiap.mytransport.domain.dto.CreateScheduleDTO
import br.com.fiap.mytransport.domain.dto.UpdateGeoLocation
import br.com.fiap.mytransport.domain.dto.UpdateScheduleDTO
import br.com.fiap.mytransport.domain.entity.Agendamento
import br.com.fiap.mytransport.domain.entity.GeoLocation
import br.com.fiap.mytransport.domain.entity.Schedule
import br.com.fiap.mytransport.domain.repository.ScheduleRepository
import kotlinx.android.synthetic.main.activity_schedule.*
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

class ScheduleActivity : AppCompatActivity() {
    var userUid: String = ""
    var scheduleId: String = ""

    private lateinit var scheduleRepository: ScheduleRepository

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule)
        scheduleRepository = ScheduleRepositoryImpl(ScheduleAPIService.instance)

        setupValues()

        if (scheduleId != null){
            getDadosSchedule()
        } else {
            btCadSchDelete.isClickable = false
        }

        btCadSchSave.setOnClickListener{
            if (scheduleId.isEmpty()){
                saveNewSchedule()
            } else {
                updateSchedule()
            }

        }

        btCadSchDelete.setOnClickListener{
            deleteSchedule()
        }

        btCadSchReturn.setOnClickListener{
            setResult(Activity.RESULT_OK)
            super.finish()
        }
    }

    fun setupValues(){
        userUid = intent.getStringExtra("userUid").orEmpty()
        scheduleId = intent.getStringExtra("scheduleId").orEmpty()
    }

    fun deleteSchedule(){
        scheduleRepository?.deletarAgendamento(
                userUid,
                scheduleId,
                {  throwable ->
                    Toast.makeText(
                            baseContext, getString(R.string.schedule_deleted_successful),
                            Toast.LENGTH_SHORT
                    ).show()
                    setResult(Activity.RESULT_OK)
                    super.finish()
                },
                { throwable ->
                    Toast.makeText(baseContext, throwable.message, Toast.LENGTH_SHORT).show()
                }
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun saveNewSchedule(){
        val schedule: Schedule? = validInfo()

        if (schedule != null){
            var createScheduleDTO: CreateScheduleDTO = CreateScheduleDTO(
                    schedule.dataAgendamento,
                    CreateGeoLocation(schedule.latitudeOrigem, schedule.longitudeOrigem),
                    CreateGeoLocation(schedule.latitudeDestino, schedule.longitudeDestino),
                    schedule.idUsuario
            )

            scheduleRepository?.criarAgendamento(
                    createScheduleDTO,
                    { agendamento: Agendamento? ->
                        if (agendamento != null){
                            Toast.makeText(
                                    baseContext, getString(R.string.schedule_create_successful),
                                    Toast.LENGTH_SHORT
                            ).show()
                            setResult(Activity.RESULT_OK)
                            super.finish()
                        }
                    },
                    { throwable ->
                        Toast.makeText(baseContext, throwable.message, Toast.LENGTH_SHORT).show()
                    }
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateSchedule(){
        val schedule: Schedule? = validInfo()

        if (schedule != null){
            var updateScheduleDTO: UpdateScheduleDTO = UpdateScheduleDTO(
                schedule.dataAgendamento,
                UpdateGeoLocation(schedule.latitudeOrigem, schedule.longitudeOrigem),
                UpdateGeoLocation(schedule.latitudeDestino, schedule.longitudeDestino),
                scheduleId
            )

            scheduleRepository?.atualizarAgendamento(
                    userUid, scheduleId, updateScheduleDTO,
                    { agendamento: Agendamento? ->
                        if (agendamento != null){
                            Toast.makeText(
                                    baseContext, getString(R.string.schedule_update_successful),
                                    Toast.LENGTH_SHORT
                            ).show()
                            setResult(Activity.RESULT_OK)
                            super.finish()
                        }
                    },
                    { throwable ->
                        Toast.makeText(baseContext, throwable.message, Toast.LENGTH_SHORT).show()
                    }
            )
        }
    }

    @SuppressLint("SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.O)
    fun validInfo(): Schedule? {
        var isOk: Boolean = true
        var msgErro: String = ""
        var schedule: Schedule = Schedule(userUid, "", scheduleId, "", "", "", "")

        if (edCadSchDate.text.isEmpty() || edCadSchHour.text.isEmpty()){
            isOk = false
            msgErro = getString(R.string.schedule_type_date_hour)
        } else {

            try {
                //"2021-04-05T22:52:06.095Z"
                val format: DateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm")
                val data: Date = format.parse(edCadSchDate.text.toString() + " " + edCadSchHour.text.toString())
                schedule.dataAgendamento = data.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().toString()

            } catch (error: ParseException){
                isOk = false
                msgErro = getString(R.string.schedule_date_hour_correct_format)
            }
        }

        if (isOk && edCadSchOrgLatitude.text.isEmpty()){
            isOk = false
            msgErro = getString(R.string.schedule_valid_latitude_origin)
        } else {
            schedule.latitudeOrigem = edCadSchOrgLatitude.text.toString()
        }

        if (isOk && edCadSchOrgLongitude.text.isEmpty()){
            isOk = false
            msgErro = getString(R.string.schedule_valid_longitude_origin)
        } else {
            schedule.longitudeOrigem = edCadSchOrgLongitude.text.toString()
        }

        if (isOk && edCadSchDstLatitude.text.isEmpty()){
            isOk = false
            msgErro = getString(R.string.schedule_valid_latitude_destiny)
        } else {
            schedule.latitudeDestino = edCadSchDstLatitude.text.toString()
        }

        if (isOk && edCadSchDstLongitude.text.isEmpty()){
            isOk = false
            msgErro = getString(R.string.schedule_valid_longitude_destiny)
        } else {
            schedule.longitudeDestino = edCadSchDstLongitude.text.toString()
        }

        return if (isOk){
            schedule
        } else {
            Toast.makeText(
                    baseContext, msgErro,
                    Toast.LENGTH_SHORT
            ).show()
            null
        }
    }



    @RequiresApi(Build.VERSION_CODES.O)
    fun getDadosSchedule(){
         scheduleRepository?.pesquisarUmaAgendamento(
                 userUid,
                 scheduleId,
                 { agendamento: Agendamento? ->

                     if (agendamento != null) {
                         var schedule = SchedulePayloadMapper.map(agendamento)

                         val date = LocalDateTime.parse(schedule.dataAgendamento)

                         val formatDate = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                         val formatHour = DateTimeFormatter.ofPattern("HH:mm")

                         edCadSchDate.setText(date.format(formatDate))
                         edCadSchHour.setText(date.format(formatHour))

                         edCadSchOrgLatitude.setText(schedule.latitudeOrigem)
                         edCadSchOrgLongitude.setText(schedule.longitudeOrigem)

                         edCadSchDstLatitude.setText(schedule.latitudeDestino)
                         edCadSchDstLongitude.setText(schedule.longitudeDestino)
                     }
                 },
                 {

                 })
    }
}