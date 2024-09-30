package com.example.proyectoappmobile.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.proyectoappmobile.R
import com.example.proyectoappmobile.model.User
import com.example.proyectoappmobile.viewModel.UserViewModel
import com.example.proyectoappmobile.viewModel.UserViewModelFactory
import com.example.proyectoappmobile.repository.UserRepository // Asegúrate de importar tu UserRepository
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.TextFieldDefaults
import androidx.compose.ui.text.font.FontWeight

class UserScreen : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Instancia de UserRepository (reemplaza esto con tu implementación real)
            val userRepository = UserRepository() // Cambia según tu implementación
            UserScreenContent(userRepository)
        }
    }

    @Composable
    fun UserScreenContent(userRepository: UserRepository) {
        // Usar el factory para obtener el ViewModel
        val userViewModel: UserViewModel = viewModel(factory = UserViewModelFactory(userRepository))

        val userList by userViewModel.userList.collectAsState(initial = emptyList())
        val statusMessage by userViewModel.statusMessage.collectAsState(initial = "")

        var name by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(android.graphics.Color.parseColor("#f8eeec")))
                .padding(16.dp)
        ) {
            // Header Image
            Image(
                painter = painterResource(id = R.drawable.top_background1),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = "User Management",
                color = Color(android.graphics.Color.parseColor("#Ea6d35")),
                modifier = Modifier.padding(top = 16.dp),
                fontSize = 36.sp,
                fontWeight = FontWeight.SemiBold
            )

            UserForm(
                name = name,
                email = email,
                password = password,
                onNameChange = { name = it },
                onEmailChange = { email = it },
                onPasswordChange = { password = it },
                onSaveClick = {
                    if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                        val user = User(id = email, name = name, email = email, password = password)
                        userViewModel.addUser(user)
                        name = ""
                        email = ""
                        password = ""
                    } else {
                        userViewModel.updateStatusMessage("Por favor completa todos los campos")
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (statusMessage.isNotEmpty()) {
                Text(
                    text = statusMessage,
                    style = MaterialTheme.typography.h6,
                    color = Color(android.graphics.Color.parseColor("#3b608c"))
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn {
                items(userList) { user ->
                    UserItem(
                        user = user,
                        onDeleteClick = { userViewModel.deleteUser(user.id) },
                        onEditClick = {
                            name = user.name
                            email = user.email
                            password = user.password
                        }
                    )
                }
            }
        }
    }

    @Composable
    fun UserForm(
        name: String,
        email: String,
        password: String,
        onNameChange: (String) -> Unit,
        onEmailChange: (String) -> Unit,
        onPasswordChange: (String) -> Unit,
        onSaveClick: () -> Unit
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = onNameChange,
                label = { Text("Nombre") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedLabelColor = Color(android.graphics.Color.parseColor("#5e5e5e")),
                    unfocusedLabelColor = Color(android.graphics.Color.parseColor("#5e5e5e"))
                )
            )

            OutlinedTextField(
                value = email,
                onValueChange = onEmailChange,
                label = { Text("Email") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedLabelColor = Color(android.graphics.Color.parseColor("#5e5e5e")),
                    unfocusedLabelColor = Color(android.graphics.Color.parseColor("#5e5e5e"))
                )
            )

            OutlinedTextField(
                value = password,
                onValueChange = onPasswordChange,
                label = { Text("Contraseña") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                visualTransformation = PasswordVisualTransformation(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedLabelColor = Color(android.graphics.Color.parseColor("#5e5e5e")),
                    unfocusedLabelColor = Color(android.graphics.Color.parseColor("#5e5e5e"))
                )
            )

            Button(
                onClick = onSaveClick,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(top = 16.dp)
                    .border(BorderStroke(1.dp, Color(android.graphics.Color.parseColor("#4d4d4d")))),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(android.graphics.Color.parseColor("#Ea6d35")),
                    contentColor = Color.White
                )
            ) {
                Text("Guardar Usuario")
            }
        }
    }

    @Composable
    fun UserItem(
        user: User,
        onDeleteClick: () -> Unit,
        onEditClick: () -> Unit
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .background(Color.White, RoundedCornerShape(8.dp))
                .clickable { onEditClick() }
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = user.name, style = MaterialTheme.typography.subtitle1)
                Text(text = user.email, style = MaterialTheme.typography.body1)
            }

            Row {
                IconButton(onClick = onEditClick) {
                    Icon(Icons.Default.Edit, contentDescription = "Editar")
                }
                IconButton(onClick = onDeleteClick) {
                    Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                }
            }
        }
    }
}
