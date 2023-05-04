import android.annotation.SuppressLint
import android.view.MenuItem
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.scratch.ui.theme.TextFieldWithMapValue
import kotlinx.coroutines.IO_PARALLELISM_PROPERTY_NAME
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Preview(showBackground = true)
@Composable
fun mainDisplay() {
    Box(
        modifier = Modifier
            .background(Color(android.graphics.Color.parseColor("#0E1621")))
            .fillMaxSize()
    ) {
        navigationPanel()
    }
}

@Composable
fun navigationPanel() {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    Scaffold(modifier = Modifier
        .zIndex(1f),
        scaffoldState = scaffoldState,
        topBar = {
            AppBar(
                onNavigationIconClick = {
                    scope.launch {
                        scaffoldState.drawerState.open()
                    }
                }
            )
        },
        bottomBar = {
            BottomNavigation(
                backgroundColor = (Color(android.graphics.Color.parseColor("#0E1621"))),
            ) {
                BottomNavigationItem(
                    icon = { Icon(Icons.Filled.PlayArrow, contentDescription = "Play") },
                    selected = true,
                    onClick = { /* Handle home click */ }
                )
                BottomNavigationItem(
                    icon = { Icon(Icons.Filled.Close, contentDescription = "Stop") },
                    selected = false,
                    onClick = { /* Handle settings click */ }
                )
            }
        },
        drawerContent = {
                drawerHeader()
                drawerBody(
                    items = listOf(
                        blocks(
                            id = "createVariable",
                            title = "Create new variable",
                            contentDescription = "CreatingVariable",
                            icon = Icons.Default.AddCircle,
                            boxColor = "#FF7F50"
                        ),
                        blocks(
                            id = "sumOperation",
                            title = "Sum operation",
                            contentDescription = "sumBlocks",
                            icon = Icons.Default.AddCircle,
                            boxColor = "#FF4C64"
                        ),
                    ),
                    onItemClick = {
                        println("Click")
                    }
                )
        },
        contentColor = (Color(android.graphics.Color.parseColor("#FFFFFF"))),
        drawerBackgroundColor = (Color.Transparent),
        backgroundColor = (Color(android.graphics.Color.parseColor("#17212B"))),
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            Column(modifier = Modifier
                .background(
                    color = (Color(android.graphics.Color.parseColor("#FF7F50"))),
                    shape = RoundedCornerShape(8.dp)
                )
                .fillMaxWidth()
                .shadow(2.dp))
            {
                TextFieldWithMapValue() }
        }
    }
}




//drawerContent = {
//
//    Column(modifier = Modifier
//        .fillMaxHeight(),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally) {}
//},
//
//drawerBackgroundColor = (Color(android.graphics.Color.parseColor("#17212B"))),
//backgroundColor = (Color(android.graphics.Color.parseColor("#17212B"))),
//) { innerPadding ->
//    Column(
//        modifier = Modifier
//            .padding(innerPadding)
//    ) {
//    }
//}


//fun ScreenWithButton() {
//    val boxes = remember { mutableStateListOf<Color>() }
//
//    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
//        Button(
//            onClick = { boxes.add(Color.Red) },
//            modifier = Modifier
//                .align(Alignment.CenterHorizontally)
//                .background(Color(android.graphics.Color.parseColor("#FFFFFF")))
//        ) {
//            Text("Добавить Box")
//        }
//
//        boxes.forEach {
//            Box(
//                modifier = Modifier
//                    .width(200.dp)
//                    .height(200.dp)
//                    .background(Color(android.graphics.Color.parseColor("#FFFFFF")))
//            ) {
//                // Содержимое вашего Box
//            }
//        }
//    }
//}

//При нажатии на кнопку, элемент будет добавляться в массив, а уже этот массив через цикл отображаться на экране. Элементы можно сделать в виде классов.