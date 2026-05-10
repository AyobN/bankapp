package com.ayobn.bankapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun AccountDetailsScreen(
    tokenStore: TokenStore,
    accountNumber: String,
    onBack: () -> Unit,
) {
    var account by remember { mutableStateOf<Account?>(null) }
    var error by remember { mutableStateOf<String?>(null) }
    var nicknameInput by remember { mutableStateOf("") }
    var saving by remember { mutableStateOf(false) }
    var savedMessage by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(accountNumber) {
        try {
            val bearer = tokenStore.bearer() ?: throw Exception("No token")
            val result = Network.api.getAccount(bearer, accountNumber)
            account = result
            nicknameInput = result.nickname.orEmpty()
        } catch (e: Exception) {
            error = "Failed to load account."
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        TextButton(onClick = onBack) {
            Text("← Back")
        }

        when {
            error != null -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(error!!, color = MaterialTheme.colorScheme.error)
                }
            }
            account == null -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator()
                }
            }
            else -> {
                Text("Account Details", style = MaterialTheme.typography.headlineMedium)

                Text(
                    text = "Number: ${account!!.accountNumber}",
                    modifier = Modifier.padding(top = 16.dp),
                )
                Text(text = "Balance: $${"%.2f".format(account!!.balance)}")

                HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

                Text("Nickname", style = MaterialTheme.typography.titleMedium)

                OutlinedTextField(
                    value = nicknameInput,
                    onValueChange = { nicknameInput = it },
                    label = { Text("Nickname") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                )

                if (savedMessage != null) {
                    Text(
                        text = savedMessage!!,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(top = 8.dp),
                    )
                }

                Button(
                    enabled = !saving,
                    onClick = {
                        saving = true
                        savedMessage = null
                        scope.launch {
                            try {
                                val bearer = tokenStore.bearer() ?: throw Exception("No token")
                                val updated = Network.api.updateNickname(
                                    bearer,
                                    accountNumber,
                                    UpdateNicknameRequest(nicknameInput.ifBlank { null })
                                )
                                account = updated
                                savedMessage = "Saved!"
                            } catch (e: Exception) {
                                savedMessage = "Failed to save."
                            } finally {
                                saving = false
                            }
                        }
                    },
                    modifier = Modifier.padding(top = 16.dp),
                ) {
                    Text(if (saving) "Saving..." else "Save")
                }
            }
        }
    }
}