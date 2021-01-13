package br.com.fiap.mytransport.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.fiap.mytransport.R
import br.com.fiap.mytransport.ui.about.AboutActivity
import br.com.fiap.mytransport.ui.consulta.RoutesActivity
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        btHomeRoutes.setOnClickListener {
            val proximaTela = Intent(this, RoutesActivity::class.java)
            startActivity(proximaTela)
        }

        btHomeAbout.setOnClickListener {
            val proximaTela = Intent(this, AboutActivity::class.java)
            startActivity(proximaTela)
        }
    }
}