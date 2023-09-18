import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.key.*
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import kotlinx.coroutines.launch
import java.io.File

@OptIn(ExperimentalComposeUiApi::class)
@Composable
@Preview
fun app() {
    val path = mutableStateOf("")
    val coroutineScope = rememberCoroutineScope()
    val focusRequester = remember { FocusRequester() }

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
                            searchFiles(path.value)
                            true
                        } else false
                    }
                    .focusable()
                    .focusRequester(focusRequester),
                label = { Text("Path", fontSize = 24.sp) },
                singleLine = true,
                trailingIcon = {
                    Box(modifier = Modifier.padding(end = 10.dp)) {
                        IconButton({}) {
                            Button(
                                onClick = {
                                    searchFiles(path.value)
                                    coroutineScope.launch {
                                        focusRequester.requestFocus()
                                    }
                                },
                                modifier = Modifier.pointerHoverIcon(PointerIcon.Hand)
                            ) {
                                Icon(Icons.Filled.Search, "Search")
                            }
                        }
                    }
                },
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

private fun searchFiles(path: String) {
    val directory = File(path)

    if (!directory.exists() || !directory.isDirectory) {
        println("Invalid directory path")
        return
    }

    val directories = directory.listFiles { file -> file.isDirectory() }

    if (directories.isNullOrEmpty()) {
        println("No directories found")
        return
    }

    directories.forEach { dir ->
        println(dir.name)
    }
}