package org.shop.prodyktu.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.shop.prodyktu.model.Product
import org.shop.prodyktu.viewmodel.ShopViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: ShopViewModel,
    products: List<Product>,
    onProductClick: (Product) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var isSearchActive by remember { mutableStateOf(false) }
    
    Column(modifier = Modifier.fillMaxSize()) {
        // Header
        Text(
            text = "Village Store",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
        
        // Search bar
        SearchBar(
            query = searchQuery,
            onQueryChange = { query ->
                searchQuery = query
                viewModel.searchProducts(query)
            },
            onSearch = { viewModel.searchProducts(it) },
            active = isSearchActive,
            onActiveChange = { isSearchActive = it },
            placeholder = { Text("Search products...") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            // Search suggestions could go here
        }
        
        // Product list
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            items(products) { product ->
                ProductCard(
                    product = product,
                    onAddToCart = { viewModel.addToCart(product) },
                    onReserve = { 
                        // For demo purposes, reserve 1 item
                        viewModel.createReservation(product.id, 1)
                    },
                    onClick = { onProductClick(product) }
                )
            }
        }
    }
}