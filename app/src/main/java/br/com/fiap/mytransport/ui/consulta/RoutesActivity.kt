package br.com.fiap.mytransport.ui.consulta

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import br.com.fiap.mytransport.R
import br.com.fiap.mytransport.data.remote.APIService
import br.com.fiap.mytransport.data.repository.BusRepositoryImpl
import br.com.fiap.mytransport.domain.entity.Onibus
import br.com.fiap.mytransport.domain.repository.BusRepository
import br.com.fiap.mytransport.model.adapter.BusItemAdapter
import kotlinx.android.synthetic.main.activity_routes.*

class RoutesActivity : AppCompatActivity() {

    private lateinit var busRepository: BusRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_routes)

        btRouterVoltar.setOnClickListener{
            super.finish()
        }

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