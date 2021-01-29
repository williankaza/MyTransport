package br.com.fiap.mytransport.ui.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import br.com.fiap.mytransport.ui.maps.MapsActivity
import br.com.fiap.mytransport.R
import br.com.fiap.mytransport.ui.consulta.RoutesActivity
import br.com.fiap.mytransport.ui.home.HomeActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity : Activity() {

    private lateinit var auth: FirebaseAuth
    val btn_sign_up :Button by lazy { findViewById(R.id.btn_sign_up) }
    val btn_log_in: Button by lazy { findViewById(R.id.btn_log_in) }
    val tv_username: TextView by lazy { findViewById(R.id.tv_username) }
    val tv_password: TextView by lazy { findViewById(R.id.tv_password) }
    var flagLoginInicial = true



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance()
        Log.d("wyk","Voltei para o Login")

        btn_sign_up.setOnClickListener() {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        btn_log_in.setOnClickListener {
            doLogin()
        }


    }

    private fun doLogin() {


        if (tv_username.text.toString().isEmpty()) {
            tv_username.error = "Por favor, entre com e-mail"
            tv_username.requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(tv_username.text.toString()).matches()) {
            tv_username.error = "Por favor, entre com um e-mail válido"
            tv_username.requestFocus()
            return
        }

        if (tv_password.text.toString().isEmpty()) {
            tv_password.error = "Por favor, entre com a senha"
            tv_password.requestFocus()
            return
        }

        auth.signInWithEmailAndPassword(tv_username.text.toString(), tv_password.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        updateUI(user)
                    } else {

                        updateUI(null)
                    }
                }
    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(currentUser: FirebaseUser?) {

        if (currentUser != null) {
            if (currentUser.isEmailVerified) {
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            } else {
                Toast.makeText(
                    baseContext, "Por favor, verifique seu email para finalizar o cadastro",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }else if(flagLoginInicial){
            flagLoginInicial = false
            return
        } else {
            Toast.makeText(
                    baseContext, "Não foi possível se autenticar",
                    Toast.LENGTH_SHORT
            ).show()
        }
    }


}