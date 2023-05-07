import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun navigationPanel() {
    val viewBlocks = ArrayList<@Composable () -> Unit>()
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
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
                    onClick = {}
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
                    viewBlocks.add{textFieldWithMapValue()}
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
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
            )
            {
                for (visual in viewBlocks) {
                    val state = rememberDismissState(
                        confirmStateChange = {
                            if (it == DismissValue.DismissedToStart) {
                                viewBlocks.removeFirst()
                            }
                            true
                        }
                    )
                    SwipeToDismiss(
                        state = state, background = {
                            val color = when (state.dismissDirection) {
                                DismissDirection.StartToEnd -> Color.Transparent
                                DismissDirection.EndToStart -> Color(android.graphics.Color.parseColor("#FF3200"))
                                null -> Color.Transparent
                            }
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(color = color, shape = RoundedCornerShape(8.dp))
                                    .padding(10.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Delete",
                                    tint = Color.White,
                                    modifier = Modifier.align(Alignment.CenterEnd)
                                )
                            }
                        },
                        dismissContent = {
                            Box(
                                modifier = Modifier
                                    .background(
                                        color = (Color(android.graphics.Color.parseColor("#FFFFF"))),
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .fillMaxWidth()
                                    .shadow(2.dp)
                            )
                            {
                                visual()
                                textFieldWithMapValue()
                            }
                        },
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}


