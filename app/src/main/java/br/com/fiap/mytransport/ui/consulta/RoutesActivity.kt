package br.com.fiap.mytransport.ui.consulta

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import br.com.fiap.mytransport.R
import br.com.fiap.mytransport.data.remote.APIService
import br.com.fiap.mytransport.data.repository.BusRepositoryImpl
import br.com.fiap.mytransport.domain.entity.Onibus
import br.com.fiap.mytransport.domain.repository.BusRepository
import br.com.fiap.mytransport.model.adapter.BusItemAdapter
import br.com.fiap.mytransport.ui.maps.MapsActivity

class RoutesActivity : AppCompatActivity() {

    private lateinit var busRepository: BusRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_routes)

        busRepository = BusRepositoryImpl(APIService.instance)
        getListOnibus()
    }

    fun getListOnibus() {
        val recyclerView = findViewById<RecyclerView>(R.id.routes_recyclerview)

        busRepository.pesquisarTudo(
            { lsAllOnibus ->
                val lsOnibus: MutableList<Onibus> = mutableListOf<Onibus>()
                if (lsAllOnibus != null) {
                    for ( onibus in lsAllOnibus ){
                        lsOnibus.add(onibus)
                    }
                }
                recyclerView.adapter = BusItemAdapter(lsOnibus, this)
            },
            {
            }
        )
    }
}