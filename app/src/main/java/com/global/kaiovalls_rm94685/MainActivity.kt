package com.global.kaiovalls_rm94685

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.global.kaiovalls_rm94685.model.Dica
import com.global.kaiovalls_rm94685.repository.DicaDao
import com.global.kaiovalls_rm94685.ui.theme.Kaiovalls_rm94685Theme

class MainActivity : ComponentActivity() {

    private lateinit var dicaDao : DicaDao; // aqui deve instanciar o banco de dados

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = Room.databaseBuilder(
            applicationContext,
            DicaDatabase::class.java,
            "dica_database"
        ).build()
        dicaDao = database.dicaDao()


        setContent {
            Kaiovalls_rm94685Theme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    // Kaio Valls e Luana Aquino
                    DicasList()
                    AddDicaButton {
                        // Adicionar uma dica de exemplo
                        addDica(Dica(title = "Nova Dica", descricao = "Descrição da Dica"))
                    }
                }
            }
        }
    }


    @Composable
    fun DicasList() {
        val dicaList by dicaDao.getAll().observeAsState(initial = emptyList())

        LazyColumn {
            items(dicaList) { dica ->
                DicaCard(dica = dica)
            }
        }
    }

    fun addDica(dica: Dica) {
        dicaDao.insert(dica)
    }
}

@Composable
fun AddDicaButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.padding(16.dp).height(10.dp)

    ) {
        Text("+")
    }
}

@Composable
fun DicaCard(dica: Dica) {
    Card(modifier = Modifier.padding(bottom = 8.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .weight(3f)) {
                Text(
                    text = dica.title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = dica.descricao,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal
                )
            }
        }
    }
}