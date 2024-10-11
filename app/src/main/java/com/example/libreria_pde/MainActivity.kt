
package com.example.libreria_pde

import android.app.*
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.libreria_pde.FireBase.FireBaseHelper
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Calendar

class MainActivity : ComponentActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var novelAdapter: NovelAdapter
    private lateinit var novelList: MutableList<Novel> // Lista de novelas
    private lateinit var firestore: FirebaseFirestore
    private val CHANNEL_ID = "SYNC_NOTIFICATION"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        // Inicializar Firebase
        FirebaseApp.initializeApp(this)

        // Comenzar a trabajar con Firebase Firestore después de inicializar Firebase
        val firestore = FirebaseFirestore.getInstance()

        // Cargar las novelas desde Firebase
        loadNovelsFromFirebase()

        // Configuración del RecyclerView
        recyclerView = findViewById(R.id.recyclerViewNovels)
        novelAdapter = NovelAdapter(novelList, { novel -> novelDetails(novel) }, { novel -> borrarNovela(novel) })
        recyclerView.adapter = novelAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Botón para agregar una nueva novela
        val addNovelButton: Button = findViewById(R.id.buttonAddNovel)
        addNovelButton.setOnClickListener {
            addNewNovel()
        }
    }

    // Método para agregar una nueva novela
    private fun addNewNovel() {
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setTitle("Agregar Nueva Novela")

        // Crear un layout para el formulario
        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        layout.setPadding(50, 20, 50, 20)

        val titleInput = EditText(this)
        titleInput.hint = "Título de la novela"
        layout.addView(titleInput)

        val authorInput = EditText(this)
        authorInput.hint = "Autor de la novela"
        layout.addView(authorInput)

        val yearInput = EditText(this)
        yearInput.hint = "Año de publicación"
        layout.addView(yearInput)

        dialogBuilder.setView(layout)

        dialogBuilder.setPositiveButton("Guardar") { _, _ ->
            val title = titleInput.text.toString()
            val author = authorInput.text.toString()
            val year = yearInput.text.toString().toIntOrNull() ?: Calendar.getInstance().get(Calendar.YEAR)
            val novelId = getNextNovelId()  // Generar ID local si es necesario

            val novel = Novel(title, author, year, false, novelId)
            novelList.add(novel)
            novelAdapter.notifyDataSetChanged()

            // Reutilizar el método para guardar en Firebase
                FireBaseHelper.FirebaseHelper.saveNovelToFirebase(novel,
                onSuccess = {
                    Toast.makeText(this, "Novela guardada con éxito", Toast.LENGTH_SHORT).show()
                },
                onFailure = { e ->
                    Toast.makeText(this, "Error al guardar la novela: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            )
        }

        dialogBuilder.setNegativeButton("Cancelar", null)
        dialogBuilder.create().show()
    }


    // Cargar novelas desde Firebase
    private fun loadNovelsFromFirebase() {
        firestore.collection("novels").get()
            .addOnSuccessListener { documents ->
                novelList.clear()
                for (document in documents) {
                    val novel = document.toObject(Novel::class.java)
                    novelList.add(novel)
                }
                novelAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Error al cargar novelas: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun getNextNovelId(): Int {
        val sharedPreferences = getSharedPreferences("novelPrefs", MODE_PRIVATE)
        val lastId = sharedPreferences.getInt("lastId", 0) // Obtén el último ID, 0 si no existe
        val nextId = lastId + 1

        // Guardar el próximo ID en las preferencias para mantenerlo actualizado
        with(sharedPreferences.edit()) {
            putInt("lastId", nextId)
            apply()
        }

        return nextId
    }

    fun scheduleJob(context: Context) {
        val componentName = ComponentName(context, SyncJobService::class.java)
        val jobInfo = JobInfo.Builder(123, componentName)
            .setPeriodic(6 * 60 * 60 * 1000)  // Ejecutar cada 6 horas
            .build()

        val jobScheduler = context.getSystemService(JobScheduler::class.java)
        jobScheduler?.schedule(jobInfo)
    }

    private fun novelDetails(novel: Novel) {
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setTitle(novel.title)
        dialogBuilder.setMessage("Autor: ${novel.author}\nAño: ${novel.year}\nFavorita: ${novel.favorite}")

        dialogBuilder.setPositiveButton("Cerrar", null)
        dialogBuilder.create().show()
    }
}

private fun AlertDialog.Builder.setPositiveButton(s: String) {

}

