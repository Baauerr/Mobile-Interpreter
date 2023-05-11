import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidtaskcompose.ui.theme.opsExpression
import com.example.scratch.printBlock
import com.example.scratch.variableForView
import kotlinx.coroutines.launch

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

data class Blockes(
    val blockID: Int,
    val color: String,
    val blockFunction: @Composable () -> Unit
)

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun navigationPanel() {
    var iDOfBlock = 0
    var tempVariableForView = ""
    val viewBlocks = remember { mutableStateListOf<Blockes>() }
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val isExpanded = remember { mutableStateOf(false) }
    val drawerState = scaffoldState.drawerState
    Scaffold(
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
                modifier = Modifier
                    .height(50.dp),
                backgroundColor = (Color(android.graphics.Color.parseColor("#0E1621"))),
            ) {
                BottomNavigationItem(
                    icon = { Icon(Icons.Filled.Menu, contentDescription = "OpenMenu") },
                    selected = false,
                    onClick = {
                        scope.launch {
                            scaffoldState.drawerState.open()
                        }
                    }
                )
                BottomNavigationItem(
                    icon = { Icon(Icons.Filled.PlayArrow, contentDescription = "Play") },
                    selected = true,
                    onClick = {tempVariableForView = variableForView}
                )
                BottomNavigationItem(
                    icon = { Icon(Icons.Filled.DateRange, contentDescription = "Console") },
                    selected = false,
                    onClick = { isExpanded.value = !isExpanded.value }
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
                        id = "mathOperation",
                        title = "Math operation",
                        contentDescription = "mathBlocks",
                        icon = Icons.Default.AddCircle,
                        boxColor = "#FF4C64"
                    ),
                    blocks(
                        id = "createPrint",
                        title = "Create print block",
                        contentDescription = "creatingPrint",
                        icon = Icons.Default.AddCircle,
                        boxColor = "#EC2EFF"
                    ),
                ),
                onItemClick = {
                    if (it.id == "createVariable") {
                        viewBlocks.add(
                            Blockes(
                                blockID = iDOfBlock++,
                                color = "#FF7F50",
                                blockFunction = { textFieldWithMapValue() }
                            )
                        )
                    }
                    else if (it.id == "mathOperation"){
                        viewBlocks.add(
                            Blockes(
                                blockID = iDOfBlock++,
                                color = "#FF4C64",
                                blockFunction = { opsExpression() }
                            )
                        )
                    }
                    else if (it.id == "createPrint"){
                        viewBlocks.add(
                            Blockes(
                                blockID = iDOfBlock++,
                                color = "#EC2EFF",
                                blockFunction = { printBlock() }
                            )
                        )
                    }

                }
            )
        },
        contentColor = (Color(android.graphics.Color.parseColor("#FFFFFF"))),
        drawerBackgroundColor = (Color.Black.copy(alpha = 0.4f)),
        backgroundColor = (Color(android.graphics.Color.parseColor("#17212B"))),
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                itemsIndexed(
                    items = viewBlocks,
                    key = { index, item ->
                        item.hashCode()
                    }) { index, item ->
                    val state = rememberDismissState(
                        confirmStateChange = {
                            if (it == DismissValue.DismissedToStart) {
                                viewBlocks.remove(item)
                            }
                            true
                        }
                    )
                    SwipeToDismiss(
                        state = state,
                        background = {
                            val color = when (state.dismissDirection) {
                                DismissDirection.StartToEnd -> Color.Transparent
                                DismissDirection.EndToStart -> Color.Red
                                null -> Color.Transparent
                            }
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(color, shape = RoundedCornerShape(8.dp))
                                    .padding(8.dp)

                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.align(Alignment.CenterEnd)
                                )
                            }
                        },
                        dismissContent = {
                            Box(modifier = Modifier
                                .fillMaxWidth()
                                .shadow(2.dp)
                                .background(
                                    color = (Color(android.graphics.Color.parseColor(item.color))),
                                    shape = RoundedCornerShape(8.dp)
                                )
                            ) {
                                item.blockFunction()
                            }
                        },
                        directions = setOf(DismissDirection.EndToStart)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
        if (isExpanded.value) {
            Column(modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Black.copy(alpha = 0.6f))
                .padding(bottom = 55.dp),
                Arrangement.Bottom,
            )
            {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(16.dp)
                        .shadow(2.dp)
                        .background(
                            Color(android.graphics.Color.parseColor("#0E1621")),
                            shape = RoundedCornerShape(8.dp)
                        ),

                    Alignment.Center,

                    ) {
                    LazyRow()
                    {
                        item{
                            Text(text = tempVariableForView, fontSize = 30.sp)
                        }
                    }
                }
            }
        }
    }
}
