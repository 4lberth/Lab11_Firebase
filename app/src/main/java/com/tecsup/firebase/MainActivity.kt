package com.tecsup.firebase

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.tecsup.firebase.ui.theme.Lab11_FirebaseTheme
import com.tecsup.firebase.viewmodel.ContactViewModel
import androidx.lifecycle.viewmodel.compose.viewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab11_FirebaseTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen(viewModel: ContactViewModel = viewModel()) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(4.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(35.dp))
        Text(
            "APLICACIÓN CON FIREBASE",
            fontSize = 23.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Text(
            "Contactos FDS",
            fontSize = 21.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        TextField(
            value = viewModel.idCto,
            onValueChange = { viewModel.idCto = it },
            label = { Text("ID:") },
            readOnly = true
        )
        TextField(
            value = viewModel.nomCto,
            onValueChange = { viewModel.nomCto = it },
            label = { Text("Nombre Contacto:") }
        )
        TextField(
            value = viewModel.celCto,
            onValueChange = { viewModel.celCto = it },
            label = { Text("Nro Celular Contacto:") }
        )
        Row {
            Button(onClick = { viewModel.insertarContacto() }) {
                Text("Insertar")
            }
            Spacer(Modifier.width(12.dp))
            Button(onClick = { viewModel.actualizarContacto() }) {
                Text("Actualizar")
            }
            Spacer(Modifier.width(12.dp))
            Button(onClick = { viewModel.eliminarContacto() }) {
                Text("Eliminar")
            }
        }

        Text(
            "LISTADO DE CONTACTOS",
            fontSize = 23.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        LazyColumn {
            items(viewModel.listCto) { contacto ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(contacto.nomCto, Modifier.weight(0.3f), fontWeight = FontWeight.Bold)
                    Text(contacto.celCto, Modifier.weight(0.2f), fontWeight = FontWeight.Bold)
                    IconButton(onClick = { viewModel.seleccionarContacto(contacto) }) {
                        Icon(imageVector = Icons.Default.Edit, contentDescription = "Editar")
                    }
                }
            }
        }
    }
}
