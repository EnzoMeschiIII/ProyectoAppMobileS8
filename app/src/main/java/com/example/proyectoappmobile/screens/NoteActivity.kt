package com.example.proyectoappmobile.screens

import android.content.Intent
import android.os.Bundle
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.proyectoappmobile.R
import com.google.gson.Gson

class NoteActivity : AppCompatActivity() {

    private val gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent{
            Note()
        }
    }

    @Preview
    @Composable
    fun Note(){

        val context = LocalContext.current

        fun handleButtonClick() {
            val sharedPreferences = context.getSharedPreferences("user_prefs", MODE_PRIVATE)
            val editor = sharedPreferences.edit()

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
                text = "Notes",
                color = Color(android.graphics.Color.parseColor("#3b608c")),
                modifier = Modifier.padding(top = 16.dp, start = 24.dp),
                fontSize = 40.sp,
                fontWeight = FontWeight.SemiBold
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
                        Image(painter = painterResource(id = R.drawable.ic_3),
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
                            text = "Add Note",
                            color = Color(android.graphics.Color.parseColor("#2f4f86")),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )

                    }
                }
            }


            Text(
                text = "Go Back to main",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .padding(top = 50.dp)
                    .padding(end = 36.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = Color(android.graphics.Color.parseColor("#3b608c"))
            )
            Image(painter = painterResource(id = R.drawable.btn_arrow2down), contentDescription = null,
                modifier = Modifier
                    .width(80.dp)
                    .padding(top = 8.dp)
                    .align(Alignment.CenterHorizontally)
                    .clickable {
                        val intent = Intent(context, MainActivity::class.java)
                        context.startActivity(intent)
                    }
                    .padding(end = 24.dp)
            )

        }
    }
}