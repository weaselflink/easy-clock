@file:OptIn(ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class)

package de.stefanbissell.easyclock

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.mouseClickable
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.isSecondaryPressed
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.singleWindowApplication
import kotlin.system.exitProcess
import kotlinx.coroutines.delay
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun main() = singleWindowApplication(
    title = "EasyClock",
    undecorated = true
) {
    ClockView()
}

@Composable
fun ClockView() {
    val settings = Settings.loadSettings()
    val time = remember { mutableStateOf(Clock.System.now()) }
    val fontSize = remember { mutableStateOf(settings.fontSize) }

    Text(
        text = time.format(),
        modifier = Modifier
            .mouseClickable(
                onClick = {
                    if (buttons.isSecondaryPressed) {
                        exitProcess(0)
                    }
                    fontSize.value *= 2
                }
            )
            .background(Color.Black)
            .padding(fontSize.value.dp / 2, fontSize.value.dp / 4),
        color = Color.Red,
        fontSize = fontSize.value.sp
    )

    LaunchedEffect("update") {
        while (true) {
            time.value = Clock.System.now()
            delay(100)
        }
    }
}

private fun MutableState<Instant>.format() =
    value
        .toLocalDateTime(TimeZone.of("Europe/Berlin"))
        .let {
            "${it.hour.pad(" ")}:${it.minute.pad("0")}"
        }

private fun Int.pad(with: String) =
    if (this < 10) {
        "$with$this"
    } else {
        "$this"
    }
