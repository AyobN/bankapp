package com.ayobn.bankapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.ayobn.bankapp.ui.theme.BankAppTheme

// Simple sealed class for screen state — no Navigation library needed
sealed class Screen {
    object Login : Screen()
    object Accounts : Screen()
    data class Details(val accountNumber: String) : Screen()
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BankAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    BankApp()
                }
            }
        }
    }
}

@Composable
fun BankApp() {
    val context = LocalContext.current
    val tokenStore = remember { TokenStore(context) }

    var screen: Screen by remember {
        mutableStateOf(if (tokenStore.token != null) Screen.Accounts else Screen.Login)
    }

    when (val s = screen) {
        is Screen.Login -> LoginScreen(
            tokenStore = tokenStore,
            onLoginSuccess = { screen = Screen.Accounts },
        )
        is Screen.Accounts -> AccountsScreen(
            tokenStore = tokenStore,
            onAccountClick = { number -> screen = Screen.Details(number) },
            onLogout = { screen = Screen.Login },
        )
        is Screen.Details -> AccountDetailsScreen(
            tokenStore = tokenStore,
            accountNumber = s.accountNumber,
            onBack = { screen = Screen.Accounts },
        )
    }
}