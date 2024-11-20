package com.global.kaiovalls_rm94685

import android.os.Bundle
import android.provider.CalendarContract.Colors
import android.widget.SearchView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.colorspace.Rgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import com.global.kaiovalls_rm94685.model.Dica
import com.global.kaiovalls_rm94685.repository.DicaRepository
import com.global.kaiovalls_rm94685.ui.theme.Kaiovalls_rm94685Theme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private lateinit var dicaRepository: DicaRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dicaRepository = DicaRepository(applicationContext)

        setContent {
            Kaiovalls_rm94685Theme {
                Surface(modifier = Modifier.fillMaxSize(), color = Color.Green) {
                    // Kaio Valls e Luana Aquino

                    DicasList(dicaRepository = dicaRepository, lifecycleScope)
                }
            }
        }
    }
}

@Composable
fun DicasList(dicaRepository: DicaRepository, lifecycleScope: LifecycleCoroutineScope) {
    var title by remember { mutableStateOf("") }
    var descricao by remember { mutableStateOf("") }
    var searchQuery by remember { mutableStateOf("") }

    val dicaList = if (searchQuery.isEmpty()) {
        dicaRepository.getAllDicas().observeAsState(initial = emptyList())
    } else {
        dicaRepository.searchDica(searchQuery).observeAsState(initial = emptyList())
    }

    Column(modifier = Modifier.padding(all = 10.dp)) {
        Text(text = "Kaio Valls RM94685 \n Luana Aquino RM93074 \n Luana foi a aluna que teve que ir para o hospital")
        TextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text(text = "Pesquisa") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Rounded.Search,
                    contentDescription = "Search"
                )
            }
        )

        TextField(
            value = title,
            onValueChange = { title = it },
            label = { Text(text = "Título") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 2.dp)
        )

        TextField(
            value = descricao,
            onValueChange = { descricao = it },
            label = { Text(text = "Descrição") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 2.dp)
        )
        AddDicaButton {
            lifecycleScope.launch {
                dicaRepository.addDica(Dica(title = title, descricao = descricao))
            }
        }

        LazyColumn {
            items(dicaList.value) { dica ->
                DicaCard(dica = dica, dicaRepository, lifecycleScope)
            }
        }
    }
}


@Composable
fun SearchView(searchQuery: String, onQueryChanged: (String) -> Unit){
    TextField(value = searchQuery,
        onValueChange = onQueryChanged,
        placeholder = { Text(text = "Pesquisa")},
        leadingIcon = {
            Icon(
                imageVector = Icons.Rounded.Search,
                contentDescription = ""
            )
        },

    )
}

@Composable
fun AddDicaButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF006400) // Verde escuro (Dark Green em hexadecimal)
        )
    ) {
        Text("Adicionar Dica")
    }
}

@Composable
fun DicaCard(dica: Dica, dicaRepository: DicaRepository, lifecycleScope: LifecycleCoroutineScope) {
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
            Button(onClick = {
                lifecycleScope.launch {
                    dicaRepository.removeDica(dica = dica)
                }
            },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF006400) // Verde escuro (Dark Green em hexadecimal)
                )
            ) {
                Text(text = "Remover")
            }
        }
    }
}

