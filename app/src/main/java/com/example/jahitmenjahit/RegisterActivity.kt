package com.example.jahitmenjahit

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.jahitmenjahit.databinding.ActivityLoginMain2Binding
import com.example.jahitmenjahit.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private  lateinit var binding : ActivityRegisterBinding
    private lateinit var edt_fullname: EditText
    private lateinit var edt_email: EditText
    private lateinit var edt_password: EditText
    private lateinit var edt_confirm_password: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding: ActivityRegisterBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnRegister1.setOnClickListener {
            edt_fullname = binding.edtFullname;
            edt_email = binding.edtEmail;
            edt_password = binding.edtPassword;
            edt_confirm_password = binding.edtConfirmPassword;

            val sharedPreferences = this.getSharedPreferences("user", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("fullname", edt_fullname.text.toString())
            editor.putString("email", edt_email.text.toString())
            editor.putString("password", edt_password.text.toString())
            editor.putString("confirmpassword", edt_confirm_password.text.toString())
            editor.apply()

            Toast.makeText(this, "Registrasi Sukses! Silahkan Login", Toast.LENGTH_LONG).show()
            //  reset
            edt_fullname.setText("")
            edt_email.setText("")
            edt_password.setText("")
            edt_confirm_password.setText("")
        }


        binding.txtlogin.setOnClickListener{
            startActivity(Intent(this,LoginMainActivity2::class.java))
        }
    }
}