package com.randomfood.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Orange = Color(0xFFF36B3F)
private val OrangeDark = Color(0xFFDF542A)
private val Ink = Color(0xFF24201E)
private val Muted = Color(0xFF817873)
private val Page = Color(0xFFFFFDFB)
private val categories = listOf("ทั้งหมด", "อาหารตามสั่ง", "จานเดียว", "เส้น", "อีสาน", "ประหยัด", "ยำ")
private val foods = listOf(
    Food("ข้าวกะเพราไก่", "อาหารตามสั่ง", 55, "🍛"),
    Food("ข้าวผัดหมู", "อาหารตามสั่ง", 50, "🍚"),
    Food("ก๋วยเตี๋ยวหมู", "เส้น", 50, "🍜"),
    Food("ส้มตำไก่ย่าง", "อีสาน", 70, "🥗"),
    Food("ข้าวมันไก่", "จานเดียว", 55, "🍗"),
    Food("ผัดไทย", "เส้น", 60, "🍝"),
    Food("ข้าวไข่เจียว", "ประหยัด", 35, "🍳"),
    Food("ข้าวหมูแดง", "จานเดียว", 60, "🥘"),
    Food("ข้าวหมูทอด", "จานเดียว", 60, "🍱"),
    Food("ยำวุ้นเส้น", "ยำ", 65, "🥗"),
    Food("ข้าวต้มหมู", "ประหยัด", 45, "🍲"),
    Food("ราดหน้าหมู", "เส้น", 55, "🥘")
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
    var cart by remember { mutableStateOf(mapOf<String, Int>()) }
    var tab by remember { mutableIntStateOf(0) }
    var category by remember { mutableStateOf("ทั้งหมด") }
    var search by remember { mutableStateOf("") }
    var memberMessage by remember { mutableStateOf(false) }
    val matchingFoods = foods.filter {
        it.price <= budget &&
            (category == "ทั้งหมด" || it.category == category) &&
            it.name.contains(search.trim(), ignoreCase = true)
    }
    val visibleFoods = if (tab == 1) foods.filter { it.name in favorites } else matchingFoods
    val cartCount = cart.values.sum()

    MaterialTheme(colorScheme = lightColorScheme(primary = Orange, secondary = OrangeDark)) {
        Scaffold(
            containerColor = Page,
            topBar = {
                Column(Modifier.fillMaxWidth().background(Color.White).padding(horizontal = 16.dp, vertical = 10.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(Modifier.size(39.dp).background(Orange, RoundedCornerShape(13.dp)), contentAlignment = Alignment.Center) {
                            Text("🍜", fontSize = 22.sp)
                        }
                        Spacer(Modifier.width(9.dp))
                        Text("Random food", fontSize = 21.sp, fontWeight = FontWeight.Black, color = Orange)
                        Spacer(Modifier.weight(1f))
                        IconButton(onClick = { tab = 2 }) {
                            BadgedBox(badge = { if (cartCount > 0) Badge { Text(cartCount.toString()) } }) {
                                Icon(Icons.Default.ShoppingCart, contentDescription = "ตะกร้า", tint = Ink)
                            }
                        }
                        IconButton(onClick = { tab = 3 }) {
                            Icon(Icons.Default.Person, contentDescription = "สมาชิก", tint = Ink)
                        }
                    }
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(
                        value = search, onValueChange = { search = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("ค้นหาเมนูอาหารที่อยากกิน…", color = Muted) },
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = Muted) },
                        singleLine = true, shape = RoundedCornerShape(14.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Orange, unfocusedBorderColor = Color(0xFFEEE6E0),
                            focusedContainerColor = Color(0xFFF7F4F1), unfocusedContainerColor = Color(0xFFF7F4F1)
                        )
                    )
                }
            },
            bottomBar = {
                NavigationBar(containerColor = Color.White) {
                    NavigationBarItem(tab == 0, { tab = 0 }, icon = { Icon(Icons.Default.Home, null) }, label = { Text("หน้าหลัก") })
                    NavigationBarItem(tab == 1, { tab = 1 }, icon = { Icon(Icons.Default.Favorite, null) }, label = { Text("รายการโปรด") })
                    NavigationBarItem(tab == 2, { tab = 2 }, icon = {
                        BadgedBox(badge = { if (cartCount > 0) Badge { Text(cartCount.toString()) } }) { Icon(Icons.Default.ShoppingCart, null) }
                    }, label = { Text("ตะกร้า") })
                    NavigationBarItem(tab == 3, { tab = 3 }, icon = { Icon(Icons.Default.Person, null) }, label = { Text("สมาชิก") })
                }
            }
        ) { padding ->
            when (tab) {
                2 -> CartScreen(cart, onChange = { cart = it }, modifier = Modifier.padding(padding))
                3 -> MemberScreen(onSignIn = { memberMessage = true }, modifier = Modifier.padding(padding))
                else -> LazyColumn(
                    modifier = Modifier.fillMaxSize().padding(padding).background(Page),
                    contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    if (tab == 0) {
                        item {
                            Column(
                                Modifier.fillMaxWidth().background(
                                    Brush.linearGradient(listOf(Color(0xFFFFF0E5), Color(0xFFFFE4D3))),
                                    RoundedCornerShape(26.dp)
                                ).padding(22.dp)
                            ) {
                                Text("อร่อยง่าย ๆ เลือกได้ทุกวัน", color = OrangeDark, fontSize = 12.sp, fontWeight = FontWeight.Black)
                                Spacer(Modifier.height(8.dp))
                                Text("หิวแล้วใช่ไหม? 😋", fontSize = 29.sp, lineHeight = 36.sp, fontWeight = FontWeight.Black, color = Color(0xFF35251F))
                                Spacer(Modifier.height(6.dp))
                                Text("ไม่ต้องคิดนาน ให้ Random food ช่วยเลือกเมนูมื้อนี้", color = Color(0xFF735C50), lineHeight = 23.sp)
                                Spacer(Modifier.height(17.dp))
                                Text("งบต่อมื้อ: ฿$budget", fontWeight = FontWeight.Bold, color = Ink)
                                Slider(value = budget.toFloat(), onValueChange = { budget = it.toInt().coerceIn(30, 150) }, valueRange = 30f..150f,
                                    colors = SliderDefaults.colors(thumbColor = Orange, activeTrackColor = Orange))
                                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text("฿30", color = Muted, fontSize = 12.sp)
                                    Text("฿150", color = Muted, fontSize = 12.sp)
                                }
                                Spacer(Modifier.height(12.dp))
                                Button(onClick = { chosen = matchingFoods.randomOrNull() }, modifier = Modifier.fillMaxWidth().height(50.dp),
                                    shape = RoundedCornerShape(13.dp), colors = ButtonDefaults.buttonColors(containerColor = Orange)) {
                                    Text("🎲  สุ่มเมนูให้หน่อย", fontWeight = FontWeight.ExtraBold, fontSize = 16.sp)
                                }
                            }
                        }
                        chosen?.let { food ->
                            item {
                                Card(colors = CardDefaults.cardColors(containerColor = Color.White), shape = RoundedCornerShape(18.dp),
                                    border = BorderStroke(1.dp, Color(0xFFFFD7C5))) {
                                    Row(Modifier.fillMaxWidth().padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                                        FoodEmoji(food.emoji, Modifier.size(70.dp))
                                        Spacer(Modifier.width(12.dp))
                                        Column(Modifier.weight(1f)) {
                                            Text("วันนี้กินเมนูนี้! ✨", color = OrangeDark, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                            Text(food.name, color = Ink, fontSize = 18.sp, fontWeight = FontWeight.ExtraBold)
                                            Text("฿${food.price} · ${food.category}", color = Muted, fontSize = 12.sp)
                                            TextButton(onClick = { cart = cart + (food.name to ((cart[food.name] ?: 0) + 1)) }, contentPadding = PaddingValues(0.dp)) {
                                                Text("＋ เพิ่มลงตะกร้า", color = OrangeDark, fontWeight = FontWeight.Bold)
                                            }
                                        }
                                        IconButton(onClick = { favorites = if (food.name in favorites) favorites - food.name else favorites + food.name }) {
                                            Icon(Icons.Default.Favorite, null, tint = if (food.name in favorites) Orange else Color.LightGray)
                                        }
                                    }
                                }
                            }
                        }
                        item {
                            Column {
                                Text("เลือกเมนูที่ใช่ 🍽️", fontSize = 22.sp, fontWeight = FontWeight.Black, color = Ink)
                                Text("เมนูอร่อย ๆ ที่เหมาะกับงบของคุณ", color = Muted, fontSize = 13.sp)
                            }
                        }
                        item {
                            Row(Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                categories.forEach { label ->
                                    FilterChip(selected = category == label, onClick = { category = label }, label = { Text(label, fontWeight = FontWeight.Bold) },
                                        shape = RoundedCornerShape(50), colors = FilterChipDefaults.filterChipColors(
                                            selectedContainerColor = Color(0xFFFFF0E7), selectedLabelColor = OrangeDark
                                        ))
                                }
                            }
                        }
                    } else {
                        item {
                            Column {
                                Text("รายการโปรด ❤️", fontSize = 23.sp, fontWeight = FontWeight.Black, color = Ink)
                                Text("เมนูที่บันทึกไว้ของคุณ", color = Muted, fontSize = 13.sp)
                            }
                        }
                    }
                    if (visibleFoods.isEmpty()) {
                        item {
                            Box(Modifier.fillMaxWidth().padding(vertical = 32.dp), contentAlignment = Alignment.Center) {
                                Text(if (tab == 1) "ยังไม่มีเมนูโปรด กดหัวใจที่เมนูที่ชอบได้เลย" else "ไม่พบเมนู ลองเปลี่ยนคำค้นหาหรืองบดูนะ", color = Muted)
                            }
                        }
                    } else {
                        items(visibleFoods.chunked(2)) { pair ->
                            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                                pair.forEach { food ->
                                    FoodCard(food, food.name in favorites,
                                        onFavorite = { favorites = if (food.name in favorites) favorites - food.name else favorites + food.name },
                                        onSelect = { chosen = food },
                                        onAdd = { cart = cart + (food.name to ((cart[food.name] ?: 0) + 1)) },
                                        modifier = Modifier.weight(1f))
                                }
                                if (pair.size == 1) Spacer(Modifier.weight(1f))
                            }
                        }
                    }
                    item { TextButton(onClick = { memberMessage = true }) { Text("รายงานปัญหา / ส่งความคิดเห็น", color = Muted) } }
                }
            }
            if (memberMessage) {
                AlertDialog(onDismissRequest = { memberMessage = false }, title = { Text("สมาชิก Random food") },
                    text = { Text("ส่วนสมาชิกเป็นโครงสร้างเบื้องต้น ยังไม่ได้เชื่อมต่อระบบบัญชี") },
                    confirmButton = { TextButton(onClick = { memberMessage = false }) { Text("เข้าใจแล้ว") } })
            }
        }
    }
}

@Composable
private fun FoodEmoji(emoji: String, modifier: Modifier = Modifier) {
    Box(modifier.background(Color(0xFFFFEEE5), RoundedCornerShape(14.dp)), contentAlignment = Alignment.Center) {
        Text(emoji, fontSize = 34.sp)
    }
}

@Composable
private fun FoodCard(food: Food, favorite: Boolean, onFavorite: () -> Unit, onSelect: () -> Unit, onAdd: () -> Unit, modifier: Modifier = Modifier) {
    Card(modifier = modifier, shape = RoundedCornerShape(17.dp), colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Color(0xFFEEE6E0))) {
        Column {
            Box(Modifier.fillMaxWidth().height(112.dp).background(Brush.linearGradient(listOf(Color(0xFFFFF0E5), Color(0xFFF8D7C3))))) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text(food.emoji, fontSize = 55.sp) }
                Surface(Modifier.align(Alignment.TopStart).padding(8.dp), shape = RoundedCornerShape(7.dp), color = Color.White.copy(alpha = .95f)) {
                    Text(food.category, Modifier.padding(horizontal = 7.dp, vertical = 4.dp), fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Ink)
                }
                IconButton(onClick = onFavorite, modifier = Modifier.align(Alignment.TopEnd).padding(4.dp).size(32.dp).background(Color.White.copy(alpha = .95f), RoundedCornerShape(50))) {
                    Icon(Icons.Default.Favorite, "รายการโปรด", tint = if (favorite) Color(0xFFE44E67) else Muted, modifier = Modifier.size(18.dp))
                }
            }
            Column(Modifier.fillMaxWidth().padding(10.dp)) {
                Text(food.name, fontWeight = FontWeight.ExtraBold, fontSize = 14.sp, color = Ink, maxLines = 2, minLines = 2)
                Text(food.category, fontSize = 11.sp, color = Muted)
                Spacer(Modifier.height(6.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("฿${food.price}", color = OrangeDark, fontWeight = FontWeight.Black, fontSize = 16.sp)
                    Spacer(Modifier.weight(1f))
                    IconButton(onClick = onAdd, modifier = Modifier.size(32.dp).background(Color(0xFFFFF0E7), RoundedCornerShape(10.dp))) {
                        Text("+", color = OrangeDark, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                    }
                }
                OutlinedButton(onClick = onSelect, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp),
                    contentPadding = PaddingValues(vertical = 5.dp), colors = ButtonDefaults.outlinedButtonColors(contentColor = OrangeDark)) {
                    Text("เลือกเมนู", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }
            }
        }
    }
}

@Composable
private fun CartScreen(cart: Map<String, Int>, onChange: (Map<String, Int>) -> Unit, modifier: Modifier = Modifier) {
    val total = cart.entries.sumOf { entry -> (foods.find { it.name == entry.key }?.price ?: 0) * entry.value }
    LazyColumn(modifier = modifier.fillMaxSize().background(Page), contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        item {
            Text("ตะกร้าของคุณ 🛒", fontSize = 24.sp, fontWeight = FontWeight.Black, color = Ink)
            Text("รายการอาหารที่เลือกไว้", color = Muted, fontSize = 13.sp)
        }
        if (cart.isEmpty()) {
            item {
                Box(Modifier.fillMaxWidth().padding(vertical = 50.dp), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("🛍️", fontSize = 48.sp)
                        Text("ตะกร้ายังว่างอยู่", fontWeight = FontWeight.Bold, color = Ink)
                        Text("กลับไปเลือกเมนูที่ชอบกันเถอะ", color = Muted)
                    }
                }
            }
        } else {
            items(cart.entries.toList(), key = { it.key }) { entry ->
                val food = foods.first { it.name == entry.key }
                Card(shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
                    Row(Modifier.fillMaxWidth().padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                        FoodEmoji(food.emoji, Modifier.size(54.dp))
                        Spacer(Modifier.width(10.dp))
                        Column(Modifier.weight(1f)) {
                            Text(food.name, fontWeight = FontWeight.Bold, color = Ink)
                            Text("฿${food.price} / จาน", color = OrangeDark, fontSize = 12.sp)
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            TextButton(onClick = {
                                val next = entry.value - 1
                                onChange(if (next <= 0) cart - entry.key else cart + (entry.key to next))
                            }) { Text("−", fontSize = 20.sp, color = OrangeDark) }
                            Text(entry.value.toString(), fontWeight = FontWeight.Bold)
                            TextButton(onClick = { onChange(cart + (entry.key to (entry.value + 1))) }) { Text("+", fontSize = 20.sp, color = OrangeDark) }
                        }
                    }
                }
            }
            item {
                HorizontalDivider(color = Color(0xFFEEE6E0))
                Row(Modifier.fillMaxWidth().padding(vertical = 8.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("รวมทั้งหมด", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Ink)
                    Text("฿$total", fontSize = 20.sp, fontWeight = FontWeight.Black, color = OrangeDark)
                }
                Button(onClick = { onChange(emptyMap()) }, modifier = Modifier.fillMaxWidth().height(50.dp),
                    shape = RoundedCornerShape(13.dp), colors = ButtonDefaults.buttonColors(containerColor = Orange)) {
                    Text("ยืนยันรายการ (ตัวอย่าง)", fontWeight = FontWeight.Bold)
                }
                Text("ตะกร้านี้เป็นตัวอย่าง ยังไม่ได้เชื่อมระบบสั่งซื้อหรือชำระเงินจริง", color = Muted, fontSize = 12.sp)
            }
        }
    }
}

@Composable
private fun MemberScreen(onSignIn: () -> Unit, modifier: Modifier = Modifier) {
    Column(modifier.fillMaxSize().background(Page).padding(22.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(Modifier.height(35.dp))
        Box(Modifier.size(86.dp).background(Color(0xFFFFEEE5), RoundedCornerShape(26.dp)), contentAlignment = Alignment.Center) {
            Icon(Icons.Default.Person, null, tint = Orange, modifier = Modifier.size(48.dp))
        }
        Spacer(Modifier.height(18.dp))
        Text("ยินดีต้อนรับสู่ Random food", fontSize = 22.sp, fontWeight = FontWeight.Black, color = Ink)
        Spacer(Modifier.height(8.dp))
        Text("เข้าสู่ระบบสมาชิกเพื่อจัดการประสบการณ์ของคุณ", color = Muted)
        Spacer(Modifier.height(20.dp))
        Button(onClick = onSignIn, modifier = Modifier.fillMaxWidth().height(50.dp), shape = RoundedCornerShape(13.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Orange)) { Text("สมาชิก", fontWeight = FontWeight.Bold) }
        Spacer(Modifier.height(12.dp))
        Text("หน้าสมาชิกเป็นโครงสร้างเบื้องต้น ยังไม่ได้เชื่อมระบบบัญชี", color = Muted, fontSize = 12.sp)
    }
}
