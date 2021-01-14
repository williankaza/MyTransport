package br.com.fiap.mytransport.model.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.fiap.mytransport.R
import br.com.fiap.mytransport.data.remote.APIService
import br.com.fiap.mytransport.data.repository.BusRepositoryImpl
import br.com.fiap.mytransport.domain.entity.Onibus
import br.com.fiap.mytransport.domain.repository.BusRepository
import br.com.fiap.mytransport.ui.maps.MapsActivity


class BusItemAdapter(private val lsOnibus: List<Onibus>,
                     private val context: Context
): RecyclerView.Adapter<BusItemAdapter.BusViewHolder>(){

    private lateinit var busRepository: BusRepository

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.route_item_container, parent, false)
        busRepository = BusRepositoryImpl(APIService.instance)
        return BusViewHolder(view)
    }

    override fun onBindViewHolder(holder: BusViewHolder, position: Int) {
        val onibus = lsOnibus[position]
        holder?.bindView(onibus, context)

        var img = holder?.itemView.findViewById<ImageView>(R.id.ivFavorite)
        if (onibus.favorito){
            img.setImageResource(R.drawable.fav_heart)
        } else {
            img.setImageResource(R.drawable.unfav_heart)
        }
    }

    override fun getItemCount(): Int {
        return lsOnibus.size
    }

    class BusViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private lateinit var busRepository: BusRepository

        fun bindView(onibus: Onibus, context: Context){
            busRepository = BusRepositoryImpl(APIService.instance)
            itemView.findViewById<TextView>(R.id.numero_onibus).text = onibus.numero

            itemView.findViewById<Button>(R.id.consultarOnibus).setOnClickListener{
                Log.d("wyk","Chamou a ação do Botão")
                val intent = Intent(context, MapsActivity::class.java)
                intent.putExtra("NUMERO_ONIBUS", onibus.numero)
                context.startActivity(intent)
            }

            itemView.findViewById<ImageView>(R.id.ivFavorite).setOnClickListener{
                busRepository.atualizarFavorito(
                    onibus.numero, {
                    onibus ->
                        var img = itemView.findViewById<ImageView>(R.id.ivFavorite)
                        if (onibus != null){
                            if (onibus.favorito){
                                img.setImageResource(R.drawable.fav_heart)
                            } else {
                                img.setImageResource(R.drawable.unfav_heart)
                            }
                        }
                    },{

                    })
            }
        }

    }

}
