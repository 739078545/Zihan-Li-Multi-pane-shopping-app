package com.example.multi_pane_shopping_app

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextAlign
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import com.example.multi_pane_shopping_app.ui.theme.MultipaneshoppingappTheme

// Data class for com.example.multi_pane_shopping_app.Product
data class Product(val name: String, val price: String, val description: String)

// Hardcoded list of com.example.multi_pane_shopping_app.getProducts
val products = listOf(
    Product("Product A", "$100", "This is a great product A."),
    Product("Product B", "$150", "This is product B with more features."),
    Product("Product C", "$200", "Premium product C.")
)

// Main Composable Function
@Composable
fun ShoppingApp() {
    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT
    var selectedProduct by remember { mutableStateOf<Product?>(null) }

    if (isPortrait) {
        if (selectedProduct == null) {
            // Show product list
            ProductList(
                products = products,
                onProductClick = { product ->
                    selectedProduct = product
                }
            )
        } else {
            // Show product details with a back button
            ProductDetails(
                product = selectedProduct,
                onBack = {
                    selectedProduct = null
                }
            )
        }
    } else {
        // Landscape mode: Show both the product list and details side by side
        Row(Modifier.fillMaxSize()) {
            ProductList(
                products = products,
                modifier = Modifier.weight(1f),
                onProductClick = { product ->
                    selectedProduct = product
                }
            )
            ProductDetails(
                product = selectedProduct,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

// Composable to display the list of com.example.multi_pane_shopping_app.getProducts
@Composable
fun ProductList(
    products: List<Product>,
    modifier: Modifier = Modifier,
    onProductClick: (Product) -> Unit
) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(products) { product ->
            Text(
                text = product.name,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onProductClick(product) }
                    .padding(16.dp)
            )
            HorizontalDivider()
        }
    }
}

// Composable to display product details
@Composable
fun ProductDetails(
    product: Product?,
    modifier: Modifier = Modifier,
    onBack: (() -> Unit)? = null
) {
    if (product == null) {
        // Placeholder message when no product is selected
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier.fillMaxSize()
        ) {
            Text(
                text = "Select a product to view details.",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
        }
    } else {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            if (onBack != null) {
                // Back button in portrait mode
                Button(
                    onClick = { onBack() },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text("Back", color = MaterialTheme.colorScheme.onPrimary)
                }
            }
            Text(
                text = product.name,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 8.dp)
            )
            Text(
                text = product.price,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(top = 4.dp)
            )
            Text(
                text = product.description,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MultipaneshoppingappTheme {
                ShoppingApp()
            }
        }
    }
}
