package com.tecsup.firebase.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.database.*
import com.tecsup.firebase.model.Contacto

class ContactViewModel : ViewModel() {
    var idCto by mutableStateOf("")
    var nomCto by mutableStateOf("")
    var celCto by mutableStateOf("")
    val listCto = mutableStateListOf<Contacto>()

    private val db = FirebaseDatabase.getInstance()
    private val dbref = db.getReference("Contacto")

    init {
        cargarContactos()
    }

    private fun cargarContactos() {
        dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                listCto.clear()
                for (contacto in snapshot.children) {
                    val objCto = contacto.getValue(Contacto::class.java)
                    objCto?.let { listCto.add(it) }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase", "Error al leer los contactos: ${error.message}")
            }
        })
    }

    fun insertarContacto() {
        val objCto = Contacto(idCto, nomCto, celCto)
        val idcontacto = dbref.push().key

        idcontacto?.let {
            objCto.idCto = it
            dbref.child(it).setValue(objCto)
                .addOnSuccessListener {
                    Log.d("FIREBASE", "Contacto grabado")
                    limpiarCampos()
                }
                .addOnFailureListener { error ->
                    Log.d("FIREBASE", "ERROR al guardar contacto")
                }
        }
    }

    fun actualizarContacto() {
        val objCto = Contacto(idCto, nomCto, celCto)
        val contactoRef = dbref.child(objCto.idCto)
        contactoRef.setValue(objCto)
            .addOnSuccessListener {
                Log.d("FIREBASE", "Contacto modificado")
                limpiarCampos()
            }
            .addOnFailureListener { error ->
                Log.e("FIREBASE", "ERROR al modificar")
            }
    }

    fun eliminarContacto() {
        val idcontacto = dbref.child(idCto)
        idcontacto.removeValue()
            .addOnSuccessListener {
                Log.d("FIREBASE", "Contacto eliminado")
                limpiarCampos()
            }
            .addOnFailureListener { error ->
                Log.e("FIREBASE", "ERROR al eliminar")
            }
    }

    fun seleccionarContacto(contacto: Contacto) {
        idCto = contacto.idCto
        nomCto = contacto.nomCto
        celCto = contacto.celCto
    }

    private fun limpiarCampos() {
        idCto = ""
        nomCto = ""
        celCto = ""
    }
}

