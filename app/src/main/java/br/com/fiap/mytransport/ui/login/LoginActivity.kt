package br.com.fiap.mytransport.ui.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import br.com.fiap.mytransport.MapsActivity
import br.com.fiap.mytransport.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity : Activity() {

    private lateinit var auth: FirebaseAuth
    val btn_sign_up :Button by lazy { findViewById(R.id.btn_sign_up) }
    val btn_log_in: Button by lazy { findViewById(R.id.btn_log_in) }
    val tv_username: TextView by lazy { findViewById(R.id.tv_username) }
    val tv_password: TextView by lazy { findViewById(R.id.tv_password) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance()


        btn_sign_up.setOnClickListener() {
            startActivity(Intent(this, SignUpActivity::class.java))
            finish()
        }


        btn_log_in.setOnClickListener {
            doLogin()
        }


    }

    private fun doLogin() {


        if (tv_username.text.toString().isEmpty()) {
            tv_username.error = "Please enter email"
            tv_username.requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(tv_username.text.toString()).matches()) {
            tv_username.error = "Please enter valid email"
            tv_username.requestFocus()
            return
        }

        if (tv_password.text.toString().isEmpty()) {
            tv_password.error = "Please enter password"
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
            if(currentUser.isEmailVerified) {
                startActivity(Intent(this, MapsActivity::class.java))
                finish()
            }else{
                Toast.makeText(
                        baseContext, "Please verify your email address.",
                        Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            Toast.makeText(
                    baseContext, "Login failed.",
                    Toast.LENGTH_SHORT
            ).show()
        }
    }


}