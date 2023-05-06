import android.annotation.SuppressLint
import android.view.MenuItem
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.material.*
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.scratch.drawerBody
import com.example.scratch.drawerHeader
import kotlinx.coroutines.IO_PARALLELISM_PROPERTY_NAME
import kotlin.math.roundToInt

@Preview(showBackground = true)
@Composable
fun mainDisplay() {
    Column(
        modifier = Modifier
            .background(Color(android.graphics.Color.parseColor("#0E1621")))
            .fillMaxSize()
    ) {
        navigationPanel()
    }
}
fun blockPlus(){
    var OPS = ""
}


@Composable
fun navigationPanel(){
    Scaffold(
        topBar = {
            AppBar {
                onNavigationIconClick = {

                }
            }
          },
        bottomBar = {
            BottomNavigation (
                backgroundColor = (Color(android.graphics.Color.parseColor("#0E1621"))),
                    ) {
                BottomNavigationItem(
                    icon = { Icon(Icons.Filled.PlayArrow, contentDescription = "Home") },
                    selected = true,
                    onClick = { /* Handle home click */ }
                )
                BottomNavigationItem(
                    icon = { Icon(Icons.Filled.Close, contentDescription = "Settings") },
                    selected = false,
                    onClick = { /* Handle settings click */ }
                )
            }
        },
        drawerContent = {
                        drawerHeader()
            drawerBody(
                items = listOf(
                    MenuItem(
                        id = "home",
                        title = "Home",
                        contentDescription = "Go to home screen",
                        icon = Icons.Default.Home
                    )
                ),
            )
        },

        drawerBackgroundColor = (Color(android.graphics.Color.parseColor("#17212B"))),
        backgroundColor = (Color(android.graphics.Color.parseColor("#17212B"))),
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
        ) {
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


@Composable

fun borderMenu(modifier: Modifier

){

}




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