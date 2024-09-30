package com.example.proyectoappmobile.screens

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.google.gson.reflect.TypeToken

class LoginActivity : AppCompatActivity() {

    private val gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Login()
        }
    }

    @Preview
    @Composable
    fun Login(){

        var emailInput by rememberSaveable { mutableStateOf("") }
        var passwordInput by rememberSaveable { mutableStateOf("") }
        val context = LocalContext.current

        fun handleLoginClick() {
            val sharedPreferences = context.getSharedPreferences("user_prefs", MODE_PRIVATE)

            // Retrieve JSON data
            val userJson = sharedPreferences.getString("user_$emailInput", null)
            val userType = object : TypeToken<User>() {}.type
            val storedUser: User? = userJson?.let { gson.fromJson(it, userType) }

            if (storedUser != null && storedUser.password == passwordInput) {
                Toast.makeText(context, "Welcome back, ${storedUser.name}!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                context.startActivity(intent)
            } else {
                Toast.makeText(context, "Datos inválidos", Toast.LENGTH_SHORT).show()
            }
        }

        Column(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(color = Color(android.graphics.Color.parseColor("#f8eeec")))
        ){
            Image(painter = painterResource(id = R.drawable.top_background1),
                contentDescription = null
            )
            Text(
                text = "Welcome\nBack",
                color = Color(android.graphics.Color.parseColor("#Ea6d35")),
                modifier = Modifier.padding(top = 16.dp, start = 24.dp),
                fontSize = 40.sp,
                fontWeight = FontWeight.SemiBold
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
                label = { Text(text = "Email")},
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
                label = { Text(text = "Password")},
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

            Text(
                text = "Are you a new User? Register",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth()
                    .clickable {
                        // Redirige a Signup
                        val intent = Intent(context, SignupActivity::class.java)
                        context.startActivity(intent)
                    },
                textAlign = TextAlign.Center,
                color = Color(android.graphics.Color.parseColor("#3b608c"))
            )

            Image(painter = painterResource(id = R.drawable.btn_arraw1), contentDescription = null,
                modifier = Modifier
                    .width(80.dp)
                    .padding(top = 24.dp)
                    .align(Alignment.End)
                    .clickable {
                        handleLoginClick()
                    }
                    .padding(end = 24.dp)
            )

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp, start = 24.dp, end = 24.dp)
            ) {
                Button(onClick = { /*TODO*/ }, Modifier
                    .padding(top = 10.dp, bottom = 10.dp, end = 8.dp)
                    .weight(0.5f)
                    .height(55.dp),
                    border = BorderStroke(
                        1.dp,
                        Color(android.graphics.Color.parseColor("#4d4d4d"))
                    ),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Transparent,
                    ), shape = RoundedCornerShape(10.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.3f), verticalArrangement = Arrangement.Center
                    ) {
                        Image(painter = painterResource(id = R.drawable.google),
                            contentDescription = null,
                            modifier = Modifier
                                .width(25.dp)
                                .clickable { }
                        )

                    }
                    Column(
                        modifier = Modifier
                            .padding(start = 14.dp)
                            .weight(0.7f),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = "Google",
                            color = Color(android.graphics.Color.parseColor("#2f4f86")),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )

                    }
                }

                Button(onClick = { /*TODO*/ }, Modifier
                    .padding(top = 10.dp, bottom = 10.dp, end = 8.dp)
                    .weight(0.5f)
                    .height(55.dp),
                    border = BorderStroke(
                        1.dp,
                        Color(android.graphics.Color.parseColor("#4d4d4d"))
                    ),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Transparent,
                    ), shape = RoundedCornerShape(10.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.3f), verticalArrangement = Arrangement.Center
                    ) {
                        Image(painter = painterResource(id = R.drawable.facebook),
                            contentDescription = null,
                            modifier = Modifier
                                .width(25.dp)
                                .clickable { }
                        )

                    }
                    Column(
                        modifier = Modifier
                            .padding(start = 12.dp)
                            .weight(0.7f),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = "Facebook",
                            color = Color(android.graphics.Color.parseColor("#2f4f86")),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}