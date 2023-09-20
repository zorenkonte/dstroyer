import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.*
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import kotlinx.coroutines.launch
import java.awt.Window
import java.io.File
import javax.swing.JOptionPane

const val FILE_EXTENSION = "DS_Store"

@OptIn(ExperimentalComposeUiApi::class)
@Composable
@Preview
fun app() {
    val path = mutableStateOf("")
    val coroutineScope = rememberCoroutineScope()
    val focusRequester = remember { FocusRequester() }

    MaterialTheme(colors = MaterialTheme.colors.copy(primary = Color(0xFF8C3041))) {
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
                            searchFiles(path.value, FILE_EXTENSION)
                            true
                        } else false
                    }
                    .focusable()
                    .focusRequester(focusRequester),
                label = {

                    Text("Path", fontSize = 24.sp)
                },
                singleLine = true,
                trailingIcon = {
                    Box(modifier = Modifier.padding(end = 10.dp)) {
                        Button(
                            onClick = {
                                searchFiles(path.value, FILE_EXTENSION)
                                coroutineScope.launch {
                                    focusRequester.requestFocus()
                                }
                            },
                            modifier = Modifier.pointerHoverIcon(PointerIcon.Hand),
                        ) {
                            Icon(Icons.Filled.Search, "Search")
                        }
                    }
                },
            )

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Image(
                    painter = painterResource("Aatrox_0.jpg"),
                    contentDescription = "Background",
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }
    }
}

fun main() = application {
    val windowState = rememberWindowState(position = WindowPosition(Alignment.Center))

    Window(
        onCloseRequest = ::exitApplication,
        title = "DStroyer",
        resizable = false,
        state = windowState
    ) {
        app()
    }
}

private fun showMessageDialog(message: String) {
    JOptionPane.showMessageDialog(
        Window.getWindows().first(),
        message,
        "Warning",
        JOptionPane.WARNING_MESSAGE
    )
}

private fun searchFiles(
    path: String,
    fileExtension: String
) {
    val directory = File(path)

    if (!directory.exists()) {
        showMessageDialog("Directory not found.")
        return
    }

    var deletedFiles = 0

    directory.walk().forEach {
        if (it.extension == fileExtension) {
            it.delete()
            deletedFiles++
        }
    }

    when (deletedFiles) {
        0 -> showMessageDialog("No $fileExtension files found.")
        else -> showMessageDialog("$deletedFiles $fileExtension files deleted.")
    }
}