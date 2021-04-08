package br.com.fiap.mytransport.ui.schedule

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import br.com.fiap.mytransport.R
import br.com.fiap.mytransport.data.remote.ScheduleAPIService
import br.com.fiap.mytransport.data.remote.mapper.SchedulePayloadMapper
import br.com.fiap.mytransport.data.repository.ScheduleRepositoryImpl
import br.com.fiap.mytransport.domain.entity.Schedule
import br.com.fiap.mytransport.domain.repository.ScheduleRepository
import br.com.fiap.mytransport.model.adapter.ScheduleItemAdapter
import kotlinx.android.synthetic.main.activity_routes.*
import kotlinx.android.synthetic.main.activity_routes.btRouterVoltar
import kotlinx.android.synthetic.main.activity_schedule_query.*

class ScheduleQueryActivity : AppCompatActivity() {
    var userUid: String = ""
    val CODE_RESPONSE = 0
    private lateinit var scheduleRepository: ScheduleRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule_query)
        setupValues(savedInstanceState)

        btRouterVoltar.setOnClickListener{
            super.finish()
        }

        btnCreateSchedule.setOnClickListener{
            val proximaTela = Intent(this, ScheduleActivity::class.java)
            proximaTela.putExtra("userUid", userUid)
            startActivityForResult(proximaTela, CODE_RESPONSE)
        }

        scheduleRepository = ScheduleRepositoryImpl(ScheduleAPIService.instance)
        getListSchedule()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            CODE_RESPONSE->{
                getListSchedule()
            }
        }
    }

    fun setupValues(savedInstanceState: Bundle?){
        userUid = intent.getStringExtra("userUid").toString()
    }

    fun getListSchedule(){
        val recyclerView = findViewById<RecyclerView>(R.id.schedule_recyclerview)

        scheduleRepository.pesquisarTudo(
                userUid,
                { lsAllAgendamento ->
                    val lsSchedules: MutableList<Schedule> = mutableListOf<Schedule>()

                    if (lsAllAgendamento != null){
                        for ( agendamentos in lsAllAgendamento ){
                            lsSchedules.add(SchedulePayloadMapper.map(agendamentos))
                        }
                    }
                    lsSchedules.sortByDescending { schedule -> schedule.dataAgendamento }
                    recyclerView.adapter = ScheduleItemAdapter( lsSchedules, this )
                },
                {
                    throwable ->
                        throwable.message?.let { Log.d("wyk", it) }
                }
        )
    }

}