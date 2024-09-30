package com.example.proyectoappmobile.screens

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.proyectoappmobile.R
import com.example.proyectoappmobile.model.User
import com.google.gson.Gson

class SignupActivity : AppCompatActivity() {

    private val gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent{
            Signup()
        }
    }

    @Preview
    @Composable
    fun Signup(){

        var idInput by rememberSaveable{ mutableStateOf(0) }
        var nameInput by rememberSaveable { mutableStateOf("") }
        var emailInput by rememberSaveable { mutableStateOf("") }
        var passwordInput by rememberSaveable { mutableStateOf("") }
        val context = LocalContext.current

        fun handleButtonClick() {
            val sharedPreferences = context.getSharedPreferences("user_prefs", MODE_PRIVATE)
            val editor = sharedPreferences.edit()

            if (nameInput.isEmpty() || emailInput.isEmpty() || passwordInput.isEmpty()) {
                Toast.makeText(context, "Los datos no pueden estar vacíos", Toast.LENGTH_SHORT).show()
            } else {
                val newUser = User(idInput.toString(),nameInput, emailInput, passwordInput)

                val userJson = Gson().toJson(newUser)

                editor.putString("user_$emailInput", userJson)
                editor.apply()

                Toast.makeText(context, "Usuario Ingresado: ${newUser.name}", Toast.LENGTH_SHORT).show()

                val intent = Intent(context, LoginActivity::class.java)
                context.startActivity(intent)
            }
        }
        Column(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(color = Color(android.graphics.Color.parseColor("#f8eeec")))
        ){
            Image(painter = painterResource(id = R.drawable.top_background2),
                contentDescription = null
            )
            Text(
                text = "Create\nAccount",
                color = Color(android.graphics.Color.parseColor("#3b608c")),
                modifier = Modifier.padding(top = 16.dp, start = 24.dp),
                fontSize = 40.sp,
                fontWeight = FontWeight.SemiBold
            )


            TextField(value = nameInput, onValueChange = {nameInput=it},
                leadingIcon = {
                    Image(painter = painterResource(id = R.drawable.name), contentDescription = null,
                        modifier = Modifier
                            .size(43.dp)
                            .padding(start = 6.dp)
                            .padding(3.dp)
                    )
                },
                label = { Text(text = "Name") },
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    backgroundColor = Color.White,
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    textColor = Color(android.graphics.Color.parseColor("#5e5e5e")),
                    unfocusedLabelColor = Color(android.graphics.Color.parseColor("#5e5e5e"))
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 24.dp, end = 24.dp)
                    .background(Color.White, CircleShape)
            )

            TextField(value = emailInput, onValueChange = {emailInput=it},
                leadingIcon = {
                    Image(painter = painterResource(id = R.drawable.email), contentDescription = null,
                        modifier = Modifier
                            .size(43.dp)
                            .padding(start = 6.dp)
                            .padding(3.dp)
                    )
                },
                label = { Text(text = "Email") },
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    backgroundColor = Color.White,
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    textColor = Color(android.graphics.Color.parseColor("#5e5e5e")),
                    unfocusedLabelColor = Color(android.graphics.Color.parseColor("#5e5e5e"))
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 24.dp, end = 24.dp)
                    .background(Color.White, CircleShape)
            )

            TextField(value = passwordInput, onValueChange = {passwordInput=it},
                leadingIcon = {
                    Image(painter = painterResource(id = R.drawable.password), contentDescription = null,
                        modifier = Modifier
                            .size(43.dp)
                            .padding(start = 6.dp)
                            .padding(6.dp)
                    )
                },
                label = { Text(text = "Password") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    backgroundColor = Color.White,
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    textColor = Color(android.graphics.Color.parseColor("#5e5e5e")),
                    unfocusedLabelColor = Color(android.graphics.Color.parseColor("#5e5e5e"))
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 24.dp, end = 24.dp)
                    .background(Color.White, CircleShape)
            )

            Image(painter = painterResource(id = R.drawable.btn_arrow2), contentDescription = null,
                modifier = Modifier
                    .width(80.dp)
                    .padding(top = 24.dp)
                    .align(Alignment.End)
                    .clickable {
                        handleButtonClick()
                    }
                    .padding(end = 24.dp)
            )

            Text(
                text = "Forgot you password? Recover it",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth()
                    .clickable {
                        val intent = Intent(context, RecoveryActivity::class.java)
                        context.startActivity(intent)
                    },
                textAlign = TextAlign.Center,
                color = Color(android.graphics.Color.parseColor("#3b608c"))
            )

        }
    }
}