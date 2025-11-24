package org.shop.prodyktu.ui

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.CalendarToday

@Composable
fun BottomNavigationBar(
    currentScreen: Screen,
    onScreenSelected: (Screen) -> Unit
) {
    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = null) },
            label = { Text("Products") },
            selected = currentScreen is Screen.Products,
            onClick = { onScreenSelected(Screen.Products) }
        )
        
        NavigationBarItem(
            icon = { Icon(Icons.Default.ShoppingCart, contentDescription = null) },
            label = { Text("Cart") },
            selected = currentScreen is Screen.Cart,
            onClick = { onScreenSelected(Screen.Cart) }
        )
        
        NavigationBarItem(
            icon = { Icon(Icons.Default.List, contentDescription = null) },
            label = { Text("Orders") },
            selected = currentScreen is Screen.Orders,
            onClick = { onScreenSelected(Screen.Orders) }
        )
        
        NavigationBarItem(
            icon = { Icon(Icons.Default.CalendarToday, contentDescription = null) },
            label = { Text("Reservations") },
            selected = currentScreen is Screen.Reservations,
            onClick = { onScreenSelected(Screen.Reservations) }
        )
    }
}