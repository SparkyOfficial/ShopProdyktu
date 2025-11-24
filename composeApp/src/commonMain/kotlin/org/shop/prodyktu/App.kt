package org.shop.prodyktu

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import org.shop.prodyktu.model.User
import org.shop.prodyktu.ui.BottomNavigationBar
import org.shop.prodyktu.ui.CartScreen
import org.shop.prodyktu.ui.MainScreen
import org.shop.prodyktu.ui.OrderScreen
import org.shop.prodyktu.ui.ReservationScreen
import org.shop.prodyktu.viewmodel.ShopViewModel

@Composable
fun App() {
    MaterialTheme {
        val viewModel: ShopViewModel = viewModel()
        var currentScreen by remember { mutableStateOf<Screen>(Screen.Products) }
        
        // Set a sample user for demonstration
        LaunchedEffect(Unit) {
            viewModel.setCurrentUser(
                User(
                    id = "user1",
                    name = "John Doe",
                    email = "john@example.com",
                    phone = "+1234567890",
                    address = "123 Main St"
                )
            )
        }
        
        Column(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.weight(1f)) {
                when (currentScreen) {
                    is Screen.Products -> {
                        val products by viewModel.products.collectAsState()
                        MainScreen(
                            viewModel = viewModel,
                            products = products,
                            onProductClick = { /* Handle product click */ }
                        )
                    }
                    is Screen.Cart -> {
                        val cartItems by viewModel.cartItems.collectAsState()
                        val totalAmount by viewModel.cartTotal.collectAsState()
                        CartScreen(
                            viewModel = viewModel,
                            onPlaceOrder = { 
                                viewModel.placeOrder()
                                currentScreen = Screen.Orders
                            },
                            cartItems = cartItems,
                            totalAmount = totalAmount
                        )
                    }
                    is Screen.Orders -> {
                        val orders by viewModel.userOrders.collectAsState()
                        OrderScreen(orders = orders)
                    }
                    is Screen.Reservations -> {
                        val reservations by viewModel.userReservations.collectAsState()
                        ReservationScreen(reservations = reservations)
                    }
                }
            }
            
            BottomNavigationBar(
                currentScreen = currentScreen,
                onScreenSelected = { screen -> currentScreen = screen }
            )
        }
    }
}

sealed class Screen {
    object Products : Screen()
    object Cart : Screen()
    object Orders : Screen()
    object Reservations : Screen()
}