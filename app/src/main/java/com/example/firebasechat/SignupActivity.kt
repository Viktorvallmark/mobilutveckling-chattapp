package com.example.firebasechat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayoutStates.TAG
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SignupActivity : AppCompatActivity() {

    private lateinit var email: EditText
    private lateinit var name: EditText
    private lateinit var password: EditText
    private lateinit var signupBtn: Button
    private lateinit var auth: FirebaseAuth
    private lateinit var db: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        supportActionBar?.hide()

        name = findViewById(R.id.name)
        email = findViewById(R.id.email)
        password = findViewById(R.id.password)
        signupBtn = findViewById(R.id.signupbtn)
        auth = Firebase.auth

        signupBtn.setOnClickListener {
            val emailField = email.text.toString()
            val pwField = password.text.toString()
            val nameField = name.text.toString()

            signUp(emailField, pwField, nameField)
        }
    }
    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun signUp(email: String, pass: String, name: String?) {

        auth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    if (name != null) {
                        auth.currentUser?.uid?.let { addUserToDb(name, email, it) }
                    }

                    val intent = Intent(this@SignupActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(
                        this@SignupActivity,
                        "Authentication failed. Error ${task.exception}",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }

    }
    private fun addUserToDb(name: String, email: String, userid: String) {
        db = Firebase.database.reference
        db.child("User").child(userid).setValue(User(name,email,userid))
    }
}