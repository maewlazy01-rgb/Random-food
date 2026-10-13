package com.randomfood.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Orange = Color(0xFFF36B3F)
private val foods = listOf(
    Food("ข้าวกะเพราไก่", "อาหารตามสั่ง", 55, "🍛"),
    Food("ข้าวผัดหมู", "อาหารตามสั่ง", 50, "🍚"),
    Food("ก๋วยเตี๋ยวหมู", "เส้น", 50, "🍜"),
    Food("ส้มตำไก่ย่าง", "อีสาน", 70, "🥗"),
    Food("ข้าวมันไก่", "จานเดียว", 55, "🍗"),
    Food("ผัดไทย", "เส้น", 60, "🍝"),
    Food("ข้าวไข่เจียว", "ประหยัด", 35, "🍳"),
    Food("ข้าวหมูแดง", "จานเดียว", 60, "🥘")
)
data class Food(val name: String, val category: String, val price: Int, val emoji: String)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { RandomFoodApp() }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RandomFoodApp() {
    var chosen by remember { mutableStateOf<Food?>(null) }
    var budget by remember { mutableIntStateOf(80) }
    var favorites by remember { mutableStateOf(setOf<String>()) }
    var tab by remember { mutableIntStateOf(0) }
    val visibleFoods = foods.filter { it.price <= budget }

    MaterialTheme(colorScheme = lightColorScheme(primary = Orange, secondary = Orange)) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("🍜 ", fontSize = 23.sp)
                        Text("Random food", fontWeight = FontWeight.Black, color = Orange)
                    }
                })
            },
            bottomBar = {
                NavigationBar {
                    NavigationBarItem(selected = tab == 0, onClick = { tab = 0 }, icon = { Icon(Icons.Default.Restaurant, null) }, label = { Text("เมนูอาหาร") })
                    NavigationBarItem(selected = tab == 1, onClick = { tab = 1 }, icon = { Icon(Icons.Default.Favorite, null) }, label = { Text("รายการโปรด") })
                }
            }
        ) { padding ->
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(padding).background(Color(0xFFFFFDFC)),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                item {
                    Column(Modifier.fillMaxWidth().background(Color(0xFFFFEEE5), RoundedCornerShape(24.dp)).padding(22.dp)) {
                        Text("หิวแล้วใช่ไหม? 😋", fontSize = 26.sp, fontWeight = FontWeight.Black, color = Color(0xFF35251F))
                        Spacer(Modifier.height(6.dp))
                        Text("ให้ Random food ช่วยเลือกเมนูมื้อนี้", color = Color(0xFF775E52))
                        Spacer(Modifier.height(16.dp))
                        Text("งบต่อมื้อ: ฿$budget", fontWeight = FontWeight.Bold)
                        Slider(value = budget.toFloat(), onValueChange = { budget = it.toInt() }, valueRange = 30f..150f)
                        Button(onClick = { chosen = visibleFoods.randomOrNull() }, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = Orange)) {
                            Text("🎲 สุ่มเมนูให้หน่อย", fontWeight = FontWeight.Bold)
                        }
                    }
                }
                chosen?.let { food ->
                    item {
                        Card(colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF6F0)), shape = RoundedCornerShape(18.dp)) {
                            Row(Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                                Text(food.emoji, fontSize = 42.sp)
                                Spacer(Modifier.width(12.dp))
                                Column(Modifier.weight(1f)) {
                                    Text("วันนี้กินเมนูนี้!", color = Orange, fontSize = 12.sp)
                                    Text(food.name, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                                    Text("฿${food.price} · ${food.category}", color = Color.Gray)
                                }
                                IconButton(onClick = { favorites = favorites + food.name }) {
                                    Icon(Icons.Default.Favorite, contentDescription = "บันทึกรายการโปรด", tint = Orange)
                                }
                            }
                        }
                    }
                }
                item { Text(if (tab == 0) "เมนูตามงบ" else "รายการโปรด", fontSize = 21.sp, fontWeight = FontWeight.Black) }
                items(if (tab == 0) visibleFoods else foods.filter { it.name in favorites }) { food ->
                    Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(18.dp), colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)) {
                        Row(Modifier.fillMaxWidth().padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                            Box(Modifier.size(56.dp).background(Color(0xFFFFEEE5), RoundedCornerShape(14.dp)), contentAlignment = Alignment.Center) { Text(food.emoji, fontSize = 29.sp) }
                            Spacer(Modifier.width(12.dp))
                            Column(Modifier.weight(1f)) {
                                Text(food.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                Text(food.category, color = Color.Gray, fontSize = 12.sp)
                                Text("฿${food.price}", color = Orange, fontWeight = FontWeight.Bold)
                            }
                            OutlinedButton(onClick = { chosen = food }) { Text("เลือก") }
                            IconButton(onClick = { favorites = if (food.name in favorites) favorites - food.name else favorites + food.name }) {
                                Icon(Icons.Default.Favorite, contentDescription = "รายการโปรด", tint = if (food.name in favorites) Orange else Color.LightGray)
                            }
                        }
                    }
                }
            }
        }
    }
}
