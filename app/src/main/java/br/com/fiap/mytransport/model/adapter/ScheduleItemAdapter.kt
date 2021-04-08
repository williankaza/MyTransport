package br.com.fiap.mytransport.model.adapter

import android.app.Activity
import br.com.fiap.mytransport.domain.entity.Schedule
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import br.com.fiap.mytransport.R
import br.com.fiap.mytransport.data.remote.ScheduleAPIService
import br.com.fiap.mytransport.data.repository.ScheduleRepositoryImpl
import br.com.fiap.mytransport.domain.repository.ScheduleRepository
import br.com.fiap.mytransport.ui.maps.MapsActivity
import br.com.fiap.mytransport.ui.schedule.ScheduleActivity
import br.com.fiap.mytransport.ui.schedule.ScheduleQueryActivity
import com.google.type.Date
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class ScheduleItemAdapter(
        private val lsSchedules: List<Schedule>,
        private val context: Context
): RecyclerView.Adapter<ScheduleItemAdapter.ScheduleViewHolder>() {

    private lateinit var scheduleRepository: ScheduleRepository

    interface CallbackInterface{
        fun onHandleSelection(position: Int, message: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.schedule_item_container, parent, false)
        scheduleRepository = ScheduleRepositoryImpl(ScheduleAPIService.instance)
        return ScheduleViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        val schedule = lsSchedules[position]
        holder?.bindView(schedule, context)
    }

    override fun getItemCount(): Int {
        return lsSchedules.size
    }

    class ScheduleViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private lateinit var scheduleRepository: ScheduleRepository

        @RequiresApi(Build.VERSION_CODES.O)
        fun bindView(schedule: Schedule, context: Context){
            scheduleRepository = ScheduleRepositoryImpl(ScheduleAPIService.instance)
            val date = LocalDateTime.parse(schedule.dataAgendamento)
            val formatDate = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            val formatHour = DateTimeFormatter.ofPattern("HH:mm")

            itemView.findViewById<TextView>(R.id.tvScheduleDate).text = date.format(formatDate) + " " + date.format(formatHour)
            itemView.findViewById<TextView>(R.id.tvOriginLatitude).text = schedule.latitudeOrigem
            itemView.findViewById<TextView>(R.id.tvOriginLongitude).text = schedule.longitudeOrigem
            itemView.findViewById<TextView>(R.id.tvDestinyLatitude).text = schedule.latitudeDestino
            itemView.findViewById<TextView>(R.id.tvDestinyLongitude).text = schedule.longitudeDestino

            itemView.findViewById<Button>(R.id.btEditSchedule).setOnClickListener{

                val intent = Intent(context, ScheduleActivity::class.java)
                intent.putExtra("userUid", schedule.idUsuario)
                intent.putExtra("scheduleId", schedule.idAgendamento)
                (context as Activity).startActivityForResult(intent, 0)
            }
        }
    }
}