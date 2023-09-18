import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.*
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

@OptIn(ExperimentalComposeUiApi::class)
@Composable
@Preview
fun app() {
    val path = mutableStateOf("")

    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = path.value,
                onValueChange = { path.value = it },
                textStyle = TextStyle(fontSize = 24.sp),
                modifier = Modifier.fillMaxWidth()
                    .onPreviewKeyEvent {
                        if ((it.key == Key.NumPadEnter || it.key == Key.Enter) && it.type == KeyEventType.KeyUp) {
                            println("Path: ${path.value}")
                            true
                        } else false
                    },
                label = { Text("Path", fontSize = 24.sp) },
                singleLine = true,
            )

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.TopCenter
            ) {

            }
        }
    }
}

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "DStroyer"
    ) {
        app()
    }
}
