package com.example.lab13

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.lab13.ui.theme.Lab13Theme
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab13Theme {
                CambioContenido()
            }
        }
    }
}

@Composable
fun VisibilidadConAnimatedVisivility() {
    var isVisible by remember { mutableStateOf(false) }
    var boxColor by remember { mutableStateOf(Color.Blue) }

    // Cambia el color cada segundo
    LaunchedEffect(Unit) {
        while (true) {
            boxColor = Color(
                red = Random.nextFloat(),
                green = Random.nextFloat(),
                blue = Random.nextFloat()
            )
            delay(1000L) // Cambia cada 1000 ms (1 segundo)
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 200.dp)
    ) {
        Button(onClick = { isVisible = !isVisible }) {
            Text("Box multicolor")
        }

        AnimatedVisibility(
            visible = isVisible,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(boxColor)
            )
        }
    }
}

@Composable
fun CambioColorAnimateAsState() {
    var isBlue by remember { mutableStateOf(true) }

    val backgroundColor by animateColorAsState(
        targetValue = if (isBlue) Color.Blue else Color.Green,
        animationSpec = tween(durationMillis = 1000)
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Button(onClick = { isBlue = !isBlue }) {
            Text("Cambio de color ")
        }

        Box(
            modifier = Modifier
                .size(100.dp)
                .background(backgroundColor)
        )
    }
}

@Composable
fun AnimacionTamañoanimateDpAsState() {
    var isExpanded by remember { mutableStateOf(false) }
    var offset by remember { mutableStateOf(0.dp) }

    val size by animateDpAsState(targetValue = if (isExpanded) 150.dp else 100.dp)

    offset = if (isExpanded) 100.dp else 0.dp

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Button(onClick = {
            isExpanded = !isExpanded
        }) {
            Text("Mover y Crecer")
        }

        Box(
            modifier = Modifier
                .size(size)
                .offset(offset)
                .background(Color.Blue)
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun CambioContenido() {
    var currentState by remember { mutableStateOf(State.Loading) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Button(onClick = {
            currentState = when (currentState) {
                State.Loading -> State.Content
                State.Content -> State.Error
                State.Error -> State.Loading
            }
        }) {
            Text("Change State")
        }

        AnimatedContent(
            targetState = currentState,
            transitionSpec = {
                fadeIn(animationSpec = tween(durationMillis = 500)) with fadeOut(animationSpec = tween(durationMillis = 500))
            }
        ) { state ->
            when (state) {
                State.Loading -> {
                    Text("Cargando...", style = MaterialTheme.typography.bodyLarge)
                }
                State.Content -> {
                    Text("Contenido cargado!", style = MaterialTheme.typography.bodyLarge)
                }
                State.Error -> {
                    Text("Ocurrió un error.", style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
    }
}

enum class State {
    Loading,
    Content,
    Error
}