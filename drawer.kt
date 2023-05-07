import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.runtime.Composable
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.scratch.R


val GoogleFont = FontFamily(
    Font(R.font.googlesans_regular, FontWeight.Bold)
)

@Composable
fun drawerHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 64.dp, bottom = 20.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Blocks",
            fontSize = 60.sp,
            color = Color.White,
            fontFamily = GoogleFont
        )
    }
}

@Composable
fun drawerBody(
    items: List<blocks>,
    modifier: Modifier = Modifier,
    itemTextStyle: TextStyle = TextStyle(fontSize = 18.sp, fontFamily = FontFamily.SansSerif),
    onItemClick: (blocks) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(items) { item ->
            Box(
                modifier = Modifier
                    .clickable {
                        onItemClick(item)
                    }
                    .background(
                        color = (Color(android.graphics.Color.parseColor(item.boxColor))),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .width(250.dp)
                    .height(60.dp)
                    .shadow(2.dp),
                contentAlignment = Alignment.CenterStart,
            )
            {
                Icon(
                    imageVector = item.icon,
                    contentDescription = item.contentDescription,
                    Modifier.padding(start = 5.dp),
                    Color.White
                )
                Text(
                    modifier = Modifier.padding(start = 35.dp),
                    text = item.title,
                    color = Color.White
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

data class blocks(
    val id: String,
    val title: String,
    val contentDescription: String,
    val icon: ImageVector,
    val boxColor: String,
)

@Composable
fun AppBar(
    onNavigationIconClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(text = stringResource(id = R.string.app_name))
        },
        backgroundColor = (Color(android.graphics.Color.parseColor("#0E1621"))),
        contentColor = (Color(android.graphics.Color.parseColor("#FFFFFF"))),
    )
}
