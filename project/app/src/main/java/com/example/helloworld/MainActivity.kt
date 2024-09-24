package com.example.helloworld

import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import com.example.helloworld.ui.theme.HelloWorldTheme
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class MainActivity : FragmentActivity() {

    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var executor: Executor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializando o Executor
        executor = Executors.newSingleThreadExecutor()

        // Configurando o BiometricPrompt corretamente para a Activity
        biometricPrompt = BiometricPrompt(this, executor, object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                runOnUiThread {
                    // Autenticação bem-sucedida: Mostrar o conteúdo
                    setContent {
                        HelloWorldTheme {
                            MyAppContent()
                        }
                    }
                }
            }

            override fun onAuthenticationFailed() {
                runOnUiThread {
                    Toast.makeText(this@MainActivity, "Autenticação falhou", Toast.LENGTH_SHORT).show()
                }
            }
        })

        // Criando o prompt de autenticação biométrica
        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Autenticação Biométrica")
            .setSubtitle("Use sua impressão digital para entrar")
            .setNegativeButtonText("Cancelar")
            .build()

        // Iniciando a autenticação biométrica
        biometricPrompt.authenticate(promptInfo)
    }
}

@Composable
fun MyAppContent() {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color.White) // Define o fundo branco para todo o layout
        ) {
            val image: Painter = painterResource(id = R.drawable.wobat) // image here

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = image,
                    contentDescription = "Chave da caverna",
                    modifier = Modifier
                        .size(500.dp)
                        .background(Color.White) // Define o fundo branco para a imagem
                )
                Spacer(modifier = Modifier.height(16.dp))
                Greeting()
            }
        }
    }
}

@Composable
fun Greeting(modifier: Modifier = Modifier) {
    Text(
        text = "Abre-te Sésamo!",
        modifier = modifier,
        color = Color.Black
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HelloWorldTheme {
        MyAppContent()
    }
}
