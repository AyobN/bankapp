package com.ayobn.bankapp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AccountsScreen(
    tokenStore: TokenStore,
    onAccountClick: (String) -> Unit,
    onLogout: () -> Unit,
) {
    var accounts by remember { mutableStateOf<List<Account>?>(null) }
    var error by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        try {
            val bearer = tokenStore.bearer() ?: throw Exception("No token")
            accounts = Network.api.listAccounts(bearer)
        } catch (e: Exception) {
            error = "Failed to load accounts."
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text("My Accounts", style = MaterialTheme.typography.headlineMedium)
            Button(onClick = {
                tokenStore.clear()
                onLogout()
            }) {
                Text("Logout")
            }
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
            accounts == null -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator()
                }
            }
            else -> {
                LazyColumn(
                    modifier = Modifier.padding(top = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    items(accounts!!) { account ->
                        AccountCard(account, onClick = { onAccountClick(account.accountNumber) })
                    }
                }
            }
        }
    }
}

@Composable
private fun AccountCard(account: Account, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = account.nickname ?: account.accountNumber,
                style = MaterialTheme.typography.titleMedium,
            )
            Text(
                text = "Balance: $${"%.2f".format(account.balance)}",
                style = MaterialTheme.typography.bodyLarge,
            )
            if (account.nickname != null) {
                Text(
                    text = "Account: ${account.accountNumber}",
                    style = MaterialTheme.typography.bodySmall,
                )
            }
        }
    }
}