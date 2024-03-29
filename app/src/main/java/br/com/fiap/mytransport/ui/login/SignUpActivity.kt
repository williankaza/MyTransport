package br.com.fiap.mytransport.ui.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import br.com.fiap.mytransport.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : Activity() {

    private lateinit var auth: FirebaseAuth
    val btn_sign_up :Button by lazy { findViewById(R.id.btn_sign_up_finish) }
    val tv_username: TextView by lazy { findViewById(R.id.tv_username_signup) }
    val tv_password: TextView by lazy { findViewById(R.id.tv_password_signup) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        auth = FirebaseAuth.getInstance()

        btn_sign_up.setOnClickListener {
            signUpUser()
        }

        btCancelar.setOnClickListener{
            super.finish()
        }

    }

    private fun signUpUser() {
        if (tv_username.text.toString().isEmpty()) {
            tv_username.error = getString(R.string.login_please_type_email)
            tv_username.requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(tv_username.text.toString()).matches()) {
            tv_username.error = getString(R.string.login_please_type_valid_email)
            tv_username.requestFocus()
            return
        }

        if (tv_password.text.toString().isEmpty() || tv_password.text.length < 6) {
            tv_password.error = getString(R.string.login_please_type_password)
            tv_password.requestFocus()
            return
        }

        auth.createUserWithEmailAndPassword(tv_username.text.toString(), tv_password.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    user?.sendEmailVerification()
                        ?.addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                startActivity(Intent(this, LoginActivity::class.java))
                                finish()
                            }
                        }
                } else {
                    val taskresult = task.exception?.message
                    Toast.makeText(
                        baseContext, taskresult,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}
