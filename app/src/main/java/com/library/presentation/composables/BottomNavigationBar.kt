import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview

data class TabBarItem(
    val title: String = "Home",
    val onIconClick: () -> Unit = {},
    val selectedIcon: ImageVector = Icons.Filled.Home,
    val unselectedIcon: ImageVector = Icons.Outlined.Home,
    val badgeAmount: Int? = null
)


@Composable
fun TabView(tabBarItems: List<TabBarItem>, selectedIndex: Int) {

    NavigationBar {

        tabBarItems.forEachIndexed { index, tabBarItem ->
            NavigationBarItem(
                selected = selectedIndex == index,
                onClick = {
                    tabBarItem.onIconClick()
                },
                icon = {
                    TabBarIconView(
                        isSelected = selectedIndex == index,
                        badgeAmount = tabBarItem.badgeAmount,
                        tabBarItem = tabBarItem,
                    )
                },
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun TabBarIconView(
    isSelected: Boolean = false,
    tabBarItem: TabBarItem = TabBarItem(),
    badgeAmount: Int? = null,
) {
    //TODO is badgeBox really needed?
    BadgedBox(badge = {
        if (badgeAmount != null) {
            Badge {
                Text(badgeAmount.toString())
            }
        }
    }) {
        Icon(
            imageVector = if (isSelected) {
                tabBarItem.selectedIcon
            } else {
                tabBarItem.unselectedIcon
            },
            contentDescription = tabBarItem.title
        )
    }
}