package com.guidal.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.guidal.data.db.models.UserModel
import com.guidal.data.utils.getUserFromDataStore

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val user: State<UserModel?> = getUserFromDataStore(context).collectAsState(null)

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.surfaceBright,
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(20.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Home Screen",
                    style = MaterialTheme.typography.titleLarge
                )
            }
            if (user.value != null) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "UUID: ${user.value!!.id.toString()}")
                    Text(text = "Name: ${user.value!!.name}")
                    Text(text = "Surname: ${user.value!!.surname ?: ""}")
                    Text(text = "Email: ${user.value!!.email}")
                    Text(text = "Gender: ${user.value!!.gender ?: ""}")
                    Text(text = "Country: ${user.value!!.country ?: ""}")
                    Text(text = "Password: ${user.value!!.password}")
                    Text(text = "Role: ${user.value!!.role ?: ""}")
                }
            }
        }
    }
}