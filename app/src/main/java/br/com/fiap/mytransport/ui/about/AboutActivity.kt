package br.com.fiap.mytransport.ui.about

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.fiap.mytransport.R
import kotlinx.android.synthetic.main.activity_about.*
import kotlinx.android.synthetic.main.activity_home.*

class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        btAboutReturn.setOnClickListener {
            finish()
        }

    }
}