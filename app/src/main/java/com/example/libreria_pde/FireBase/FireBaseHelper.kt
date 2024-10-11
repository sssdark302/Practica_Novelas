package com.example.libreria_pde.FireBase

import android.util.Log
import com.example.libreria_pde.Novel
import com.google.firebase.firestore.FirebaseFirestore

object FireBaseHelper {

    object FirebaseHelper {
        private val db = FirebaseFirestore.getInstance()

        // Método para guardar o actualizar una novela en Firebase
        fun saveNovelToFirebase(
            novel: Novel,
            onSuccess: () -> Unit,
            onFailure: (Exception) -> Unit
        ) {
            val db = FirebaseFirestore.getInstance()

            // Generar un ID único manualmente
            val novelId = novel.id ?: db.collection("novels").document().id

            // Guardar la novela con un ID específico
            db.collection("novels").document(novelId.toString())
                .set(novel)
                .addOnSuccessListener {
                    novel.id = novelId as Int // Guardar el ID manual en el objeto
                    Log.d("FirebaseHelper", "Novela guardada con ID: $novelId")
                    onSuccess()
                }
                .addOnFailureListener { e ->
                    Log.w("FirebaseHelper", "Error al guardar la novela", e)
                    onFailure(e)
                }
        }


        // Método para eliminar una novela de Firebase
        fun deleteNovelFromFirebase(
            novelId: String,
            onSuccess: () -> Unit,
            onFailure: (Exception) -> Unit
        ) {
            val db = FirebaseFirestore.getInstance()

            // Eliminar el documento con el ID específico
            db.collection("novels").document(novelId)
                .delete()
                .addOnSuccessListener {
                    Log.d("FirebaseHelper", "Novela eliminada con éxito.")
                    onSuccess()  // Llamar al callback de éxito
                }
                .addOnFailureListener { e ->
                    Log.w("FirebaseHelper", "Error al eliminar la novela", e)
                    onFailure(e)  // Llamar al callback de error
                }
        }


        // Método para obtener todas las novelas desde Firebase
        fun getAllNovels(onSuccess: (List<Novel>) -> Unit, onFailure: (Exception) -> Unit) {
            val db = FirebaseFirestore.getInstance()

            // Obtener todos los documentos de la colección "novels"
            db.collection("novels")
                .get()
                .addOnSuccessListener { result ->
                    val novels = mutableListOf<Novel>()

                    // Convertir cada documento a un objeto Novel
                    for (document in result) {
                        val novel = document.toObject(Novel::class.java)
                        novel.id
                        novels.add(novel)
                    }

                    Log.d("FirebaseHelper", "Novelas obtenidas: ${novels.size}")
                    onSuccess(novels)  // Llamar al callback de éxito
                }
                .addOnFailureListener { e ->
                    Log.w("FirebaseHelper", "Error al obtener las novelas", e)
                    onFailure(e)  // Llamar al callback de error
                }
        }
    }
}