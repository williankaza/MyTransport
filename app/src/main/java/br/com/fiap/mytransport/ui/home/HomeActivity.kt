package br.com.fiap.mytransport.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import br.com.fiap.mytransport.R
import br.com.fiap.mytransport.ui.about.AboutActivity
import br.com.fiap.mytransport.ui.consulta.RoutesActivity
import br.com.fiap.mytransport.ui.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        auth = FirebaseAuth.getInstance()

        btHomeRoutes.setOnClickListener {
            val proximaTela = Intent(this, RoutesActivity::class.java)
            startActivity(proximaTela)
        }

        btHomeAbout.setOnClickListener {
            val proximaTela = Intent(this, AboutActivity::class.java)
            startActivity(proximaTela)
        }

        btHomeLogout.setOnClickListener{
            auth.signOut()
            Log.d("wyk","SignOut")
            val proximaTela = Intent(this, LoginActivity::class.java)
            startActivity(proximaTela)
            finish()

        }
    }
}