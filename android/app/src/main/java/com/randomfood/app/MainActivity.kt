package com.randomfood.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

private val Orange = Color(0xFFF36B3F)
private val OrangeDark = Color(0xFFDF542A)
private val Ink = Color(0xFF24201E)
private val Muted = Color(0xFF817873)
private val Line = Color(0xFFEEE6E0)
private val Cream = Color(0xFFFFF8F3)
private val Page = Color(0xFFFFFDFB)

data class Food(
    val id: Int, val name: String, val category: String, val price: Int,
    val emoji: String, val image: String, val desc: String, val tag: String,
    val heat: String = "ไม่เผ็ด", val cuisine: String = ""
)
private val foods = listOf(
    Food(1,"ข้าวกะเพราหมูสับไข่ดาว","อาหารตามสั่ง",60,"🍳","https://images.unsplash.com/photo-1512058564366-18510be2db19?auto=format&fit=crop&w=700&q=80","หอมกะเพรา อิ่มกำลังดี","ขายดี","เผ็ดได้"),
    Food(2,"ข้าวผัดไก่","อาหารตามสั่ง",55,"🍚","https://images.unsplash.com/photo-1603133872878-684f208fb84b?auto=format&fit=crop&w=700&q=80","เมนูคลาสสิก กินง่าย","กินง่าย"),
    Food(3,"ข้าวไข่เจียว","เมนูประหยัด",40,"🍳","https://images.unsplash.com/photo-1525351484163-7529414344d8?auto=format&fit=crop&w=700&q=80","เรียบง่าย สบายกระเป๋า","ประหยัด"),
    Food(4,"ก๋วยเตี๋ยวหมูน้ำใส","เมนูเส้น",55,"🍜","https://images.unsplash.com/photo-1569718212165-3a8278d5f624?auto=format&fit=crop&w=700&q=80","น้ำซุปกลมกล่อม","ซดคล่องคอ"),
    Food(5,"ผัดไทยกุ้งสด","เมนูเส้น",75,"🍤","https://images.unsplash.com/photo-1559314809-0d155014e29e?auto=format&fit=crop&w=700&q=80","เส้นเหนียวนุ่ม เปรี้ยวหวาน","เมนูเส้น","เผ็ดได้"),
    Food(6,"ข้าวหมูทอดกระเทียม","อาหารตามสั่ง",60,"🍖","https://images.unsplash.com/photo-1547592180-85f173990554?auto=format&fit=crop&w=700&q=80","หมูทอดหอมกระเทียม","แนะนำ"),
    Food(7,"ส้มตำไทย","เมนูประหยัด",50,"🥗","https://images.unsplash.com/photo-1559847844-5315695dadae?auto=format&fit=crop&w=700&q=80","เปรี้ยวหวาน เผ็ดปรับได้","แซ่บ","เผ็ดได้"),
    Food(8,"ไก่ทอดกรอบ","ของกินเล่น",45,"🍗","https://images.unsplash.com/photo-1626082927389-6cd097cdc6ec?auto=format&fit=crop&w=700&q=80","กรอบนอกนุ่มใน","กินเล่น"),
    Food(9,"ข้าวแกงเขียวหวานไก่","อาหารตามสั่ง",65,"🍛","https://images.unsplash.com/photo-1603894584373-5ac82b2ae398?auto=format&fit=crop&w=700&q=80","แกงกะทิหอมเครื่องแกง","อาหารไทย","เผ็ดได้"),
    Food(10,"ข้าวเหนียวหมูปิ้ง","เมนูประหยัด",40,"🍢","https://images.unsplash.com/photo-1529042410759-befb1204b468?auto=format&fit=crop&w=700&q=80","มื้อด่วนอิ่มง่าย","ประหยัด"),
    Food(11,"ราดหน้าหมู","เมนูเส้น",60,"🍜","https://images.unsplash.com/photo-1569718212165-3a8278d5f624?auto=format&fit=crop&w=700&q=80","เส้นนุ่ม น้ำราดเข้มข้น","เมนูเส้น"),
    Food(12,"เฟรนช์ฟรายส์","ของกินเล่น",49,"🍟","https://images.unsplash.com/photo-1573080496219-bb080dd4f877?auto=format&fit=crop&w=700&q=80","มันฝรั่งทอดกรอบ เคี้ยวเพลิน","ของกินเล่น"),
    Food(13,"พิซซ่าชีส","อาหารต่างประเทศ",129,"🍕","https://images.unsplash.com/photo-1513104890138-7c749659a591?auto=format&fit=crop&w=700&q=80","พิซซ่าหน้าชีสยืด ๆ","ต่างประเทศ","ไม่เผ็ด","อาหารอิตาเลียน"),
    Food(14,"สปาเกตตีซอสมะเขือเทศ","อาหารต่างประเทศ",99,"🍝","https://images.unsplash.com/photo-1551892374-ecf8754cf8b0?auto=format&fit=crop&w=700&q=80","เส้นสปาเกตตีคลุกซอสมะเขือเทศ","อิตาเลียน","ไม่เผ็ด","อาหารอิตาเลียน"),
    Food(15,"เบอร์เกอร์เนื้อ","อาหารต่างประเทศ",119,"🍔","https://images.unsplash.com/photo-1568901346375-23c9450c58cd?auto=format&fit=crop&w=700&q=80","เบอร์เกอร์เนื้อพร้อมผักและชีส","ยอดนิยม","ไม่เผ็ด","อาหารตะวันตก"),
    Food(16,"ซูชิรวม","อาหารต่างประเทศ",109,"🍣","https://images.unsplash.com/photo-1579871494447-9811cf80d66c?auto=format&fit=crop&w=700&q=80","ซูชิคำพอดีหลากหน้า","ญี่ปุ่น","ไม่เผ็ด","อาหารญี่ปุ่น"),
    Food(17,"ข้าวหน้าไก่เทอริยากิ","อาหารต่างประเทศ",89,"🍱","https://images.unsplash.com/photo-1546069901-ba9599a7e63c?auto=format&fit=crop&w=700&q=80","ไก่ซอสเทอริยากิราดข้าว","ญี่ปุ่น","ไม่เผ็ด","อาหารญี่ปุ่น"),
    Food(18,"แพนเค้กน้ำผึ้ง","ของหวาน",69,"🥞","https://images.unsplash.com/photo-1528207776546-365bb710ee93?auto=format&fit=crop&w=700&q=80","แพนเค้กนุ่มราดน้ำผึ้ง","ของหวาน"),
    Food(19,"ไอศกรีม","ของหวาน",49,"🍨","https://images.unsplash.com/photo-1497034825429-c343d7c6a68f?auto=format&fit=crop&w=700&q=80","ไอศกรีมหวานเย็น เลือกรสได้","เย็นชื่นใจ"),
    Food(20,"ชาไทยเย็น","เครื่องดื่ม",35,"🧋","https://images.unsplash.com/photo-1558857563-b371033873b8?auto=format&fit=crop&w=700&q=80","ชาไทยหอมเข้มข้น","เครื่องดื่ม"),
    Food(21,"กาแฟลาเต้เย็น","เครื่องดื่ม",55,"☕","https://images.unsplash.com/photo-1461023058943-07fcbe16d735?auto=format&fit=crop&w=700&q=80","กาแฟนมรสนุ่ม ดื่มง่าย","กาแฟ"),
    Food(22,"สลัดผัก","อาหารสุขภาพ",79,"🥗","https://images.unsplash.com/photo-1512621776951-a57141f2eefd?auto=format&fit=crop&w=700&q=80","ผักสดหลากชนิดพร้อมน้ำสลัด","สุขภาพ"),
    Food(23,"ข้าวปลาแซลมอนย่าง","อาหารสุขภาพ",129,"🐟","https://images.unsplash.com/photo-1467003909585-2f8a72700288?auto=format&fit=crop&w=700&q=80","ปลาแซลมอนย่างเสิร์ฟพร้อมข้าว","โปรตีน"),
    Food(24,"เกี๊ยวซ่าทอด","ของกินเล่น",59,"🥟","https://images.unsplash.com/photo-1563245372-f21724e3856d?auto=format&fit=crop&w=700&q=80","เกี๊ยวซ่าทอดกรอบไส้แน่น","ของกินเล่น"),
    Food(25,"ข้าวผัดกิมจิ","อาหารต่างประเทศ",89,"🍚","https://images.unsplash.com/photo-1512058564366-18510be2db19?auto=format&fit=crop&w=700&q=80","ข้าวผัดกิมจิสไตล์เกาหลี","เกาหลี","เผ็ดได้","อาหารเกาหลี"),
    Food(26,"ไก่ทอดเกาหลี","อาหารต่างประเทศ",99,"🍗","https://images.unsplash.com/photo-1626082927389-6cd097cdc6ec?auto=format&fit=crop&w=700&q=80","ไก่ทอดเคลือบซอสหวานเผ็ด","เกาหลี","เผ็ดได้","อาหารเกาหลี"),
    Food(27,"ข้าวแกงกะหรี่อินเดีย","อาหารต่างประเทศ",105,"🍛","https://images.unsplash.com/photo-1585937421612-70a008356fbe?auto=format&fit=crop&w=700&q=80","แกงเครื่องเทศหอมเสิร์ฟพร้อมข้าว","อินเดีย","เผ็ดได้","อาหารอินเดีย"),
    Food(28,"ไก่ทิกก้ามาซาลา","อาหารต่างประเทศ",129,"🍲","https://images.unsplash.com/photo-1603894584373-5ac82b2ae398?auto=format&fit=crop&w=700&q=80","ไก่ในซอสเครื่องเทศเข้มข้น","อินเดีย","เผ็ดได้","อาหารอินเดีย"),
    Food(29,"ข้าวผัดหยางโจว","อาหารต่างประเทศ",95,"🥡","https://images.unsplash.com/photo-1603133872878-684f208fb84b?auto=format&fit=crop&w=700&q=80","ข้าวผัดสไตล์จีนใส่ไข่และผัก","จีน","ไม่เผ็ด","อาหารจีน"),
    Food(30,"เกี๊ยวน้ำ","อาหารต่างประเทศ",79,"🥟","https://images.unsplash.com/photo-1563245372-f21724e3856d?auto=format&fit=crop&w=700&q=80","เกี๊ยวไส้หมูในน้ำซุปร้อน","จีน","ไม่เผ็ด","อาหารจีน"),
    Food(31,"ทาโก้ไก่","อาหารต่างประเทศ",99,"🌮","https://images.unsplash.com/photo-1551502955-77540a0c6e36?auto=format&fit=crop&w=700&q=80","แป้งทาโก้ไส้ไก่และผักสด","เม็กซิกัน","เผ็ดได้","อาหารเม็กซิกัน")
)
private val categories = listOf("ทั้งหมด","อาหารตามสั่ง","อาหารไทย","อาหารต่างประเทศ","อาหารญี่ปุ่น","อาหารจีน","อาหารอินเดีย","อาหารเกาหลี","อาหารอิตาเลียน","อาหารเม็กซิกัน","เมนูเส้น","เมนูประหยัด","ของกินเล่น","ของหวาน","เครื่องดื่ม","อาหารสุขภาพ","รายการโปรด")
private data class Restaurant(val name: String, val cuisine: String, val rating: String, val time: String, val fee: String, val image: String, val ids: List<Int>)
private val restaurants = listOf(
    Restaurant("ครัวจานด่วน","อาหารตามสั่ง","4.8","20–30 นาที","ค่าส่ง ฿10","https://images.unsplash.com/photo-1555939594-58d7cb561ad1?auto=format&fit=crop&w=800&q=80", listOf(1,2,6,9)),
    Restaurant("ก๋วยเตี๋ยวเส้นนุ่ม","ก๋วยเตี๋ยว · เมนูเส้น","4.7","15–25 นาที","ค่าส่ง ฿5","https://images.unsplash.com/photo-1569718212165-3a8278d5f624?auto=format&fit=crop&w=800&q=80", listOf(4,5,11)),
    Restaurant("อิ่มคุ้มสตรีทฟู้ด","เมนูประหยัด · ของกินเล่น","4.6","15–25 นาที","ค่าส่ง ฿0","https://images.unsplash.com/photo-1529042410759-befb1204b468?auto=format&fit=crop&w=800&q=80", listOf(3,7,8,10,12))
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { RandomFoodApp() }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RandomFoodApp() {
    val context = LocalContext.current
    val prefs = remember(context) { context.getSharedPreferences("random_food_preferences", android.content.Context.MODE_PRIVATE) }
    var tab by remember { mutableIntStateOf(0) }
    var category by remember { mutableStateOf("ทั้งหมด") }
    var search by remember { mutableStateOf("") }
    var budget by remember { mutableIntStateOf(prefs.getInt("budget", 80).coerceIn(30, 150)) }
    var budgetOption by remember { mutableStateOf("งบเท่าไรก็ได้") }
    var randomCategory by remember { mutableStateOf("อาหารทุกประเภท") }
    var heat by remember { mutableStateOf("ทุกระดับความเผ็ด") }
    var chosen by remember { mutableStateOf<Food?>(null) }
    var favorites by remember { mutableStateOf(prefs.getStringSet("favorites", emptySet())?.toSet() ?: emptySet()) }
    var cart by remember {
        mutableStateOf(prefs.getString("cart", "").orEmpty().split("|").filter { it.contains("=") }.mapNotNull {
            val p = it.split("=", limit = 2); p.getOrNull(1)?.toIntOrNull()?.takeIf { n -> n > 0 }?.let { n -> p[0].toIntOrNull()?.let { id -> id to n } }
        }.toMap())
    }
    var dialog by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var sort by remember { mutableStateOf("แนะนำ") }
    var orderHistory by remember { mutableStateOf(listOf<String>()) }
    var customerName by remember { mutableStateOf("") }
    var customerPhone by remember { mutableStateOf("") }
    var customerAddress by remember { mutableStateOf("") }
    var reportText by remember { mutableStateOf("") }

    LaunchedEffect(favorites) { prefs.edit().putStringSet("favorites", favorites.toSet()).apply() }
    LaunchedEffect(cart) { prefs.edit().putString("cart", cart.entries.joinToString("|") { it.key.toString() + "=" + it.value }).apply() }
    LaunchedEffect(budget) { prefs.edit().putInt("budget", budget).apply() }

    val visibleFoods = foods.filter { food ->
        val categoryMatches = category == "ทั้งหมด" ||
            (category == "รายการโปรด" && food.id in favorites) ||
            (category == "อาหารไทย" && food.category != "อาหารต่างประเทศ" && food.category != "ของหวาน" && food.category != "เครื่องดื่ม") ||
            (category == "อาหารต่างประเทศ" && food.category == "อาหารต่างประเทศ") ||
            (food.category == category || food.cuisine == category)
        categoryMatches && (search.isBlank() || (food.name + food.category + food.cuisine + food.desc).contains(search.trim(), true))
    }.let { list ->
        when (sort) {
            "ราคาต่ำไปสูง" -> list.sortedBy { it.price }
            "ราคาสูงไปต่ำ" -> list.sortedByDescending { it.price }
            "ชื่อเมนู" -> list.sortedBy { it.name }
            else -> list
        }
    }
    val randomFoods = foods.filter { f ->
        val max = when (budgetOption) { "ไม่เกิน 50 บาท" -> 50; "ไม่เกิน 60 บาท" -> 60; "ไม่เกิน 80 บาท" -> 80; else -> 999 }
        f.price <= max &&
            (randomCategory == "อาหารทุกประเภท" || randomCategory == "ทั้งหมด" || f.category == randomCategory || f.cuisine == randomCategory ||
                (randomCategory == "เมนูเส้น" && f.category == "เมนูเส้น") || (randomCategory == "เมนูประหยัด" && f.category == "เมนูประหยัด")) &&
            (heat == "ทุกระดับความเผ็ด" || f.heat == heat)
    }
    val cartCount = cart.values.sum()
    val subtotal = cart.entries.sumOf { (id, qty) -> (foods.find { it.id == id }?.price ?: 0) * qty }
    val delivery = if (cart.isEmpty()) 0 else 10

    MaterialTheme(colorScheme = lightColorScheme(primary = Orange, secondary = OrangeDark, background = Page)) {
        Scaffold(
            containerColor = Page,
            topBar = {
                Column(Modifier.fillMaxWidth().background(Color.White).padding(horizontal = 14.dp, vertical = 10.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Box(Modifier.size(38.dp).clip(RoundedCornerShape(13.dp)).background(Orange), contentAlignment = Alignment.Center) {
                            Text("🍜", fontSize = 22.sp)
                        }
                        Text("Random food", fontSize = 21.sp, fontWeight = FontWeight.Black, color = Orange)
                        Spacer(Modifier.weight(1f))
                        IconButton(onClick = { dialog = "member" }) { Icon(Icons.Default.Person, contentDescription = "สมาชิก", tint = OrangeDark) }
                        IconButton(onClick = { dialog = "orders" }) { Icon(Icons.Default.Inventory2, contentDescription = "ประวัติออเดอร์", tint = Ink) }
                        BadgedBox(badge = { if (cartCount > 0) Badge { Text(cartCount.toString()) } }) {
                            IconButton(onClick = { tab = 4 }) { Icon(Icons.Default.ShoppingCart, contentDescription = "ตะกร้า", tint = Ink) }
                        }
                    }
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(
                        value = search, onValueChange = { search = it; if (tab == 3) tab = 1 },
                        modifier = Modifier.fillMaxWidth(), singleLine = true,
                        placeholder = { Text("ค้นหาเมนูหรือร้านอาหาร...", color = Muted, fontSize = 14.sp) },
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = Muted) },
                        shape = RoundedCornerShape(14.dp),
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFFFFB08D), unfocusedBorderColor = Color.Transparent,
                            focusedContainerColor = Color(0xFFF7F4F1), unfocusedContainerColor = Color(0xFFF7F4F1))
                    )
                }
                HorizontalDivider(color = Line)
            },
            bottomBar = {
                NavigationBar(containerColor = Color.White, tonalElevation = 2.dp) {
                    NavigationBarItem(selected = tab == 0, onClick = { tab = 0 }, icon = { Icon(Icons.Default.Home, null) }, label = { Text("หน้าแรก", fontSize = 10.sp) })
                    NavigationBarItem(selected = tab == 1, onClick = { tab = 1 }, icon = { Icon(Icons.Default.Restaurant, null) }, label = { Text("เมนู", fontSize = 10.sp) })
                    NavigationBarItem(selected = tab == 2, onClick = { tab = 2 }, icon = { Text("🎲", fontSize = 20.sp) }, label = { Text("สุ่มอาหาร", fontSize = 10.sp) })
                    NavigationBarItem(selected = tab == 3, onClick = { tab = 3 }, icon = { Icon(Icons.Default.Home, null) }, label = { Text("ร้านอาหาร", fontSize = 10.sp) })
                    NavigationBarItem(selected = tab == 4, onClick = { tab = 4 }, icon = {
                        BadgedBox(badge = { if (cartCount > 0) Badge { Text(cartCount.toString()) } }) { Icon(Icons.Default.ShoppingCart, null) }
                    }, label = { Text("ตะกร้า", fontSize = 10.sp) })
                }
            }
        ) { padding ->
            when (tab) {
                4 -> CartScreen(cart, onChange = { cart = it }, onBack = { tab = 1 }, modifier = Modifier.padding(padding))
                3 -> RestaurantScreen(search, modifier = Modifier.padding(padding), onSelect = { restaurant ->
                    category = "ทั้งหมด"
                    search = ""
                    tab = 1
                    message = restaurant.name + " · " + restaurant.time + " · " + restaurant.fee
                })
                2 -> LazyColumn(
                    modifier = Modifier.fillMaxSize().padding(padding).background(Page),
                    contentPadding = PaddingValues(16.dp, 16.dp, 16.dp, 28.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    item {
                        RandomPanel(budgetOption, { budgetOption = it }, randomCategory, { randomCategory = it }, heat, { heat = it },
                            onRandom = {
                                chosen = randomFoods.randomOrNull()
                                if (chosen == null) message = "ไม่พบเมนูตามเงื่อนไข ลองปรับตัวกรองดูนะ"
                            })
                    }
                    chosen?.let { food -> item { ChosenFoodCard(food, food.id in favorites, onFavorite = {
                        favorites = if (food.id in favorites) favorites - food.id else favorites + food.id
                    }, onAdd = { cart = cart + (food.id to ((cart[food.id] ?: 0) + 1)); message = "เพิ่มลงตะกร้าแล้ว 🧡" }, onReroll = {
                        chosen = randomFoods.randomOrNull()
                    }) } }
                }
                else -> LazyColumn(
                    modifier = Modifier.fillMaxSize().padding(padding).background(Page),
                    contentPadding = PaddingValues(start = 14.dp, end = 14.dp, top = 15.dp, bottom = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    if (tab == 0) {
                        item {
                            Column(Modifier.fillMaxWidth().clip(RoundedCornerShape(26.dp)).background(Brush.linearGradient(listOf(Color(0xFFFFF0E5), Color(0xFFFFE4D3))))) {
                                Column(Modifier.padding(start = 21.dp, end = 21.dp, top = 22.dp, bottom = 17.dp)) {
                                    Text("มื้อนี้ให้ Random food ช่วยเลือก · 🎲 สุ่มเมนูได้ในคลิกเดียว", fontSize = 11.sp, color = OrangeDark, fontWeight = FontWeight.ExtraBold)
                                    Spacer(Modifier.height(9.dp))
                                    Text("หิวแล้วใช่ไหม?\nหาเมนูที่ใช่ได้เลย", fontSize = 29.sp, lineHeight = 36.sp, fontWeight = FontWeight.Black, color = Ink)
                                    Spacer(Modifier.height(7.dp))
                                    Text("อยากกินอะไรก็ค้นหาได้ หรือถ้ายังคิดไม่ออก ให้เราสุ่มเมนูให้ แล้วเลือกสั่งจากร้านตัวอย่างได้ในที่เดียว", color = Color(0xFF735C50), fontSize = 13.sp, lineHeight = 20.sp)
                                    Spacer(Modifier.height(14.dp))
                                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                        Button(onClick = { tab = 1 }, shape = RoundedCornerShape(12.dp), colors = ButtonDefaults.buttonColors(containerColor = Orange)) { Text("เลือกอาหารเลย →", fontWeight = FontWeight.ExtraBold) }
                                        OutlinedButton(onClick = { tab = 2 }, shape = RoundedCornerShape(12.dp), border = BorderStroke(1.dp, Color.White), colors = ButtonDefaults.outlinedButtonColors(contentColor = Ink)) { Text("🎲 สุ่มให้หน่อย") }
                                    }
                                }
                                AsyncImage(model = "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?auto=format&fit=crop&w=1000&q=85",
                                    contentDescription = "ภาพอาหารหลากหลาย", contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxWidth().height(150.dp))
                            }
                        }
                        item {
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                                FeatureButton("🔎", "ค้นหาเมนู", "ค้นหาอาหารที่อยากกิน", Modifier.weight(1f)) { tab = 1 }
                                FeatureButton("🎲", "สุ่มอาหาร", "ให้เราช่วยเลือก", Modifier.weight(1f)) { tab = 2 }
                                FeatureButton("🛵", "ร้านอาหาร", "ดูร้านตัวอย่าง", Modifier.weight(1f)) { tab = 3 }
                            }
                        }
                    }
                    if (tab == 0 || tab == 1) {
                        item {
                            Column {
                                Text(if (tab == 0) "เมนูแนะนำสำหรับคุณ" else "เลือกเมนูที่ใช่ 🍽️", fontSize = 22.sp, fontWeight = FontWeight.Black, color = Ink)
                                Spacer(Modifier.height(3.dp))
                                Text("รวมเมนูยอดนิยมพร้อมราคา กด + เพื่อใส่ตะกร้า", color = Muted, fontSize = 12.sp)
                            }
                        }
                        item {
                            Row(Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                categories.forEach { cat ->
                                    FilterChip(selected = category == cat, onClick = { category = cat }, label = { Text(cat, fontWeight = FontWeight.Bold, fontSize = 12.sp) },
                                        shape = RoundedCornerShape(50), colors = FilterChipDefaults.filterChipColors(selectedContainerColor = Color(0xFFFFF0E7), selectedLabelColor = OrangeDark))
                                }
                            }
                        }
                        item {
                            var expanded by remember { mutableStateOf(false) }
                            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("พบ " + visibleFoods.size + " เมนู", color = Muted, fontSize = 12.sp)
                                Box {
                                    TextButton(onClick = { expanded = true }) { Text("เรียงตาม: " + sort + " ▾", color = Ink, fontSize = 12.sp) }
                                    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                                        listOf("แนะนำ","ราคาต่ำไปสูง","ราคาสูงไปต่ำ","ชื่อเมนู").forEach { option ->
                                            DropdownMenuItem(text = { Text(option) }, onClick = { sort = option; expanded = false })
                                        }
                                    }
                                }
                            }
                        }
                        if (visibleFoods.isEmpty()) {
                            item { EmptyState(if (category == "รายการโปรด") "ยังไม่มีเมนูโปรด กดหัวใจที่เมนูที่ชอบได้เลย" else "ไม่พบเมนู ลองเปลี่ยนคำค้นหาหรือหมวดหมู่นะ") }
                        } else {
                            items(visibleFoods.chunked(2)) { pair ->
                                Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
                                    pair.forEach { food ->
                                        FoodCard(food, food.id in favorites, onFavorite = {
                                            favorites = if (food.id in favorites) favorites - food.id else favorites + food.id
                                        }, onAdd = {
                                            cart = cart + (food.id to ((cart[food.id] ?: 0) + 1))
                                            message = "เพิ่มลงตะกร้าแล้ว 🧡"
                                        }, modifier = Modifier.weight(1f))
                                    }
                                    if (pair.size == 1) Spacer(Modifier.weight(1f))
                                }
                            }
                        }
                    }
                    if (tab == 0) {
                        item {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                                Column(Modifier.weight(1f)) {
                                    Text("ร้านที่น่าสนใจ", fontSize = 21.sp, fontWeight = FontWeight.Black, color = Ink)
                                    Text("ร้านจำลองสำหรับทดลองหน้าตาแอป", fontSize = 12.sp, color = Muted)
                                }
                                TextButton(onClick = { tab = 3 }) { Text("ดูทั้งหมด →", color = OrangeDark) }
                            }
                        }
                        items(restaurants) { restaurant -> RestaurantCard(restaurant, onClick = { tab = 3 }) }
                        item { TextButton(onClick = { dialog = "report" }, modifier = Modifier.fillMaxWidth()) { Text("⚑ แจ้งปัญหา / ส่งข้อเสนอแนะ", color = Muted) } }
                        item { Text("เว็บไซต์ต้นแบบเพื่อทดลองแนวคิด · ราคาและร้านค้าเป็นข้อมูลสมมติ · ยังไม่มีการชำระเงินจริง", color = Muted, fontSize = 11.sp) }
                    }
                }
            }
        }
        if (dialog.isNotEmpty()) {
            AlertDialog(
                onDismissRequest = { dialog = "" },
                title = { Text(when (dialog) { "member" -> "👤 เข้าสู่ระบบ / สมัครสมาชิก"; "orders" -> "📦 ประวัติคำสั่งซื้อ"; "report" -> "⚑ แจ้งปัญหา / Report"; "checkout" -> "ยืนยันคำสั่งซื้อ"; else -> "Random food" }, fontWeight = FontWeight.Bold) },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        when (dialog) {
                            "member" -> Text("หน้าสมาชิกเป็นต้นแบบ ยังไม่มีระบบบัญชีจริง กรุณาอย่าใช้รหัสผ่านจริง")
                            "orders" -> if (orderHistory.isEmpty()) Text("ยังไม่มีประวัติคำสั่งซื้อทดลอง") else orderHistory.forEach { Text("• " + it) }
                            "report" -> {
                                Text("แจ้งปัญหาหรือข้อเสนอแนะของคุณ")
                                OutlinedTextField(value = reportText, onValueChange = { reportText = it }, label = { Text("รายละเอียด") }, minLines = 3)
                            }
                            "checkout" -> {
                                OutlinedTextField(value = customerName, onValueChange = { customerName = it }, label = { Text("ชื่อผู้รับ") }, singleLine = true)
                                OutlinedTextField(value = customerPhone, onValueChange = { customerPhone = it }, label = { Text("เบอร์โทรศัพท์") }, singleLine = true)
                                OutlinedTextField(value = customerAddress, onValueChange = { customerAddress = it }, label = { Text("ที่อยู่จัดส่ง") }, minLines = 2)
                                Text("ยอดรวม ฿" + (subtotal + delivery) + " (รวมค่าส่งตัวอย่าง ฿" + delivery + ")", fontWeight = FontWeight.Bold, color = OrangeDark)
                                Text("โหมดทดลองเท่านั้น ไม่มีการส่งคำสั่งซื้อหรือชำระเงินจริง", fontSize = 12.sp, color = Muted)
                            }
                        }
                    }
                },
                confirmButton = {
                    TextButton(onClick = {
                        when (dialog) {
                            "checkout" -> if (cart.isNotEmpty() && customerName.isNotBlank() && customerPhone.isNotBlank() && customerAddress.isNotBlank()) {
                                orderHistory = listOf(customerName + " · ฿" + (subtotal + delivery) + " · รับคำสั่งซื้อทดลองแล้ว") + orderHistory
                                cart = emptyMap(); tab = 0; dialog = ""; message = "บันทึกคำสั่งซื้อทดลองแล้ว"
                            } else message = "กรอกชื่อ เบอร์โทร และที่อยู่ให้ครบก่อนนะ"
                            "report" -> { if (reportText.isNotBlank()) message = "รับข้อเสนอแนะตัวอย่างแล้ว ขอบคุณนะ"; reportText = ""; dialog = "" }
                            "member" -> { dialog = ""; message = "ระบบสมาชิกยังเป็นตัวอย่างเท่านั้น" }
                            else -> dialog = ""
                        }
                    }) { Text(if (dialog == "checkout") "ยืนยันคำสั่งซื้อทดลอง" else "ตกลง", color = OrangeDark) }
                },
                dismissButton = { TextButton(onClick = { dialog = "" }) { Text("ปิด") } },
                containerColor = Color.White
            )
        }
        if (message.isNotBlank()) {
            AlertDialog(onDismissRequest = { message = "" }, title = { Text("Random food") }, text = { Text(message) },
                confirmButton = { TextButton(onClick = { message = "" }) { Text("ตกลง", color = OrangeDark) } })
        }
    }
}

@Composable
private fun FeatureButton(emoji: String, title: String, subtitle: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Column(modifier.clip(RoundedCornerShape(16.dp)).background(Color.White).clickable(onClick = onClick).padding(10.dp)) {
        Text(emoji, fontSize = 22.sp)
        Spacer(Modifier.height(5.dp))
        Text(title, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Ink)
        Text(subtitle, fontSize = 10.sp, color = Muted, lineHeight = 14.sp)
    }
}

@Composable
private fun RandomPanel(
    budgetOption: String, onBudget: (String) -> Unit, category: String, onCategory: (String) -> Unit,
    heat: String, onHeat: (String) -> Unit, onRandom: () -> Unit
) {
    Column(Modifier.fillMaxWidth().clip(RoundedCornerShape(22.dp)).background(Cream).padding(17.dp)) {
        Text("ยังไม่รู้จะกินอะไร?", fontSize = 22.sp, fontWeight = FontWeight.Black, color = Ink)
        Text("เลือกงบและความชอบ แล้วให้เราช่วยตัดสินใจ", fontSize = 13.sp, color = Muted)
        Spacer(Modifier.height(12.dp))
        DropdownField("งบประมาณ", budgetOption, listOf("งบเท่าไรก็ได้","ไม่เกิน 50 บาท","ไม่เกิน 60 บาท","ไม่เกิน 80 บาท"), onBudget)
        Spacer(Modifier.height(8.dp))
        DropdownField("ประเภทอาหาร", category, listOf("อาหารทุกประเภท","อาหารตามสั่ง","อาหารไทย","อาหารต่างประเทศ","อาหารญี่ปุ่น","อาหารจีน","อาหารอินเดีย","อาหารเกาหลี","อาหารอิตาเลียน","อาหารเม็กซิกัน","เมนูเส้น","เมนูประหยัด","ของกินเล่น","ของหวาน","เครื่องดื่ม","อาหารสุขภาพ"), onCategory)
        Spacer(Modifier.height(8.dp))
        DropdownField("ความเผ็ด", heat, listOf("ทุกระดับความเผ็ด","ไม่เผ็ด","เผ็ดได้"), onHeat)
        Spacer(Modifier.height(13.dp))
        Button(onClick = onRandom, modifier = Modifier.fillMaxWidth().height(49.dp), shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Orange)) {
            Text("🎲  สุ่มอาหาร", fontWeight = FontWeight.ExtraBold, fontSize = 16.sp)
        }
    }
}

@Composable
private fun DropdownField(label: String, selected: String, options: List<String>, onSelect: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    Column {
        Text(label, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Muted)
        Box {
            Surface(modifier = Modifier.fillMaxWidth().clickable { expanded = true }, shape = RoundedCornerShape(11.dp),
                border = BorderStroke(1.dp, Line), color = Color.White) {
                Row(Modifier.padding(horizontal = 12.dp, vertical = 12.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text(selected, color = Ink, fontSize = 13.sp, modifier = Modifier.weight(1f))
                    Text("⌄", color = Muted)
                }
            }
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }, modifier = Modifier.heightIn(max = 340.dp)) {
                options.forEach { option -> DropdownMenuItem(text = { Text(option, fontSize = 13.sp) }, onClick = { onSelect(option); expanded = false }) }
            }
        }
    }
}

@Composable
private fun ChosenFoodCard(food: Food, favorite: Boolean, onFavorite: () -> Unit, onAdd: () -> Unit, onReroll: () -> Unit) {
    Card(shape = RoundedCornerShape(18.dp), colors = CardDefaults.cardColors(containerColor = Color.White), border = BorderStroke(1.dp, Color(0xFFFFD7C5))) {
        Column(Modifier.padding(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                AsyncImage(model = food.image, contentDescription = food.name, contentScale = ContentScale.Crop,
                    modifier = Modifier.size(90.dp).clip(RoundedCornerShape(12.dp)))
                Spacer(Modifier.width(12.dp))
                Column(Modifier.weight(1f)) {
                    Text("วันนี้กินเมนูนี้! ✨", color = OrangeDark, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    Text(food.name, color = Ink, fontSize = 16.sp, fontWeight = FontWeight.ExtraBold)
                    Text(food.desc + " · " + food.heat, color = Muted, fontSize = 11.sp)
                    Text("฿" + food.price, color = OrangeDark, fontWeight = FontWeight.Black, fontSize = 17.sp)
                }
                IconButton(onClick = onFavorite) { Icon(Icons.Default.Favorite, null, tint = if (favorite) Color(0xFFE44E67) else Muted) }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                OutlinedButton(onClick = onReroll, modifier = Modifier.weight(1f), shape = RoundedCornerShape(11.dp)) { Text("สุ่มใหม่") }
                Button(onClick = onAdd, modifier = Modifier.weight(1f), shape = RoundedCornerShape(11.dp), colors = ButtonDefaults.buttonColors(containerColor = Orange)) { Text("เพิ่มลงตะกร้า") }
            }
        }
    }
}

@Composable
private fun FoodCard(food: Food, favorite: Boolean, onFavorite: () -> Unit, onAdd: () -> Unit, modifier: Modifier = Modifier) {
    Card(modifier = modifier, shape = RoundedCornerShape(17.dp), colors = CardDefaults.cardColors(containerColor = Color.White), border = BorderStroke(1.dp, Line)) {
        Column {
            Box(Modifier.fillMaxWidth().height(132.dp).background(Brush.linearGradient(listOf(Color(0xFFFFF0E5), Color(0xFFF8D7C3))))) {
                AsyncImage(model = food.image, contentDescription = food.name, contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize(), onError = {})
                Surface(Modifier.align(Alignment.TopStart).padding(7.dp), shape = RoundedCornerShape(7.dp), color = Color.White.copy(alpha = .96f)) {
                    Text(food.tag, Modifier.padding(horizontal = 7.dp, vertical = 4.dp), fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Ink)
                }
                IconButton(onClick = onFavorite, modifier = Modifier.align(Alignment.TopEnd).padding(4.dp).size(32.dp).background(Color.White.copy(alpha = .95f), RoundedCornerShape(50))) {
                    Icon(Icons.Default.Favorite, "รายการโปรด", tint = if (favorite) Color(0xFFE44E67) else Muted, modifier = Modifier.size(18.dp))
                }
            }
            Column(Modifier.fillMaxWidth().padding(10.dp)) {
                Text(food.name, fontWeight = FontWeight.ExtraBold, fontSize = 13.sp, color = Ink, maxLines = 2, minLines = 2, lineHeight = 18.sp)
                Text(food.desc, fontSize = 10.sp, color = Muted, maxLines = 2, minLines = 2, lineHeight = 14.sp)
                Text(food.heat + " · " + if (food.cuisine.isNotBlank()) food.cuisine else food.category, fontSize = 9.sp, color = Muted, maxLines = 1)
                Spacer(Modifier.height(5.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("฿" + food.price, color = OrangeDark, fontWeight = FontWeight.Black, fontSize = 16.sp)
                    Spacer(Modifier.weight(1f))
                    IconButton(onClick = onAdd, modifier = Modifier.size(32.dp).background(Color(0xFFFFF0E7), RoundedCornerShape(10.dp))) {
                        Text("+", color = OrangeDark, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
private fun RestaurantScreen(search: String, modifier: Modifier = Modifier, onSelect: (Restaurant) -> Unit) {
    LazyColumn(modifier.fillMaxSize().background(Page), contentPadding = PaddingValues(14.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        item {
            Text("ร้านที่น่าสนใจ", fontSize = 23.sp, fontWeight = FontWeight.Black, color = Ink)
            Text("ร้านจำลองสำหรับทดลองหน้าตาแอป ยังไม่ได้เชื่อมต่อร้านค้าจริง", color = Muted, fontSize = 12.sp)
        }
        items(restaurants.filter { search.isBlank() || (it.name + it.cuisine).contains(search, true) }) { restaurant ->
            RestaurantCard(restaurant, onClick = { onSelect(restaurant) })
        }
    }
}

@Composable
private fun RestaurantCard(restaurant: Restaurant, onClick: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth().clickable(onClick = onClick), shape = RoundedCornerShape(17.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White), border = BorderStroke(1.dp, Line)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(model = restaurant.image, contentDescription = restaurant.name, contentScale = ContentScale.Crop,
                modifier = Modifier.width(112.dp).height(122.dp))
            Column(Modifier.weight(1f).padding(12.dp)) {
                Text(restaurant.name, fontSize = 15.sp, fontWeight = FontWeight.ExtraBold, color = Ink)
                Text("★ " + restaurant.rating + "  ·  " + restaurant.time, fontSize = 12.sp, color = Color(0xFF22845C), fontWeight = FontWeight.Bold)
                Text(restaurant.cuisine, fontSize = 11.sp, color = Muted)
                Text(restaurant.fee, fontSize = 11.sp, color = Muted)
                Text("ดูเมนูร้าน →", fontSize = 12.sp, color = OrangeDark, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun CartScreen(cart: Map<Int, Int>, onChange: (Map<Int, Int>) -> Unit, onBack: () -> Unit, modifier: Modifier = Modifier) {
    val subtotal = cart.entries.sumOf { entry -> (foods.find { it.id == entry.key }?.price ?: 0) * entry.value }
    val delivery = if (cart.isEmpty()) 0 else 10
    LazyColumn(modifier = modifier.fillMaxSize().background(Page), contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        item {
            Text("🛒 ตะกร้าของคุณ", fontSize = 24.sp, fontWeight = FontWeight.Black, color = Ink)
            Text("รายการอาหารที่เลือกไว้", color = Muted, fontSize = 13.sp)
        }
        if (cart.isEmpty()) {
            item {
                EmptyState("ตะกร้ายังว่างอยู่ · กลับไปเลือกเมนูที่ชอบกันเถอะ")
                Button(onClick = onBack, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = Orange)) { Text("เลือกอาหารเลย") }
            }
        } else {
            items(cart.entries.toList(), key = { it.key }) { entry ->
                val food = foods.first { it.id == entry.key }
                Card(shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Color.White), border = BorderStroke(1.dp, Line)) {
                    Row(Modifier.fillMaxWidth().padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
                        AsyncImage(model = food.image, contentDescription = food.name, contentScale = ContentScale.Crop,
                            modifier = Modifier.size(62.dp).clip(RoundedCornerShape(11.dp)))
                        Spacer(Modifier.width(10.dp))
                        Column(Modifier.weight(1f)) {
                            Text(food.name, fontWeight = FontWeight.Bold, color = Ink, fontSize = 13.sp)
                            Text("฿" + food.price + " / รายการ", color = OrangeDark, fontSize = 12.sp)
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            TextButton(onClick = { val n = entry.value - 1; onChange(if (n <= 0) cart - entry.key else cart + (entry.key to n)) }) { Text("−", fontSize = 20.sp, color = OrangeDark) }
                            Text(entry.value.toString(), fontWeight = FontWeight.Bold)
                            TextButton(onClick = { onChange(cart + (entry.key to (entry.value + 1))) }) { Text("+", fontSize = 20.sp, color = OrangeDark) }
                        }
                    }
                }
            }
            item {
                HorizontalDivider(color = Line)
                SummaryLine("ค่าอาหาร", subtotal)
                SummaryLine("ค่าส่ง (ตัวอย่าง)", delivery)
                SummaryLine("ยอดรวม", subtotal + delivery, true)
                Button(onClick = { onChange(cart); /* checkout dialog opened by parent action below is intentionally handled via message */ },
                    modifier = Modifier.fillMaxWidth().height(49.dp), shape = RoundedCornerShape(12.dp), colors = ButtonDefaults.buttonColors(containerColor = Orange)) {
                    Text("ตรวจสอบรายการ", fontWeight = FontWeight.Bold)
                }
                Text("นี่คือการสาธิตขั้นตอนสั่งอาหารเท่านั้น ไม่มีการส่งคำสั่งซื้อหรือชำระเงินจริง", color = Muted, fontSize = 11.sp)
            }
        }
    }
}

@Composable
private fun SummaryLine(label: String, amount: Int, strong: Boolean = false) {
    Row(Modifier.fillMaxWidth().padding(vertical = 6.dp), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label, color = Ink, fontWeight = if (strong) FontWeight.ExtraBold else FontWeight.Normal, fontSize = if (strong) 17.sp else 14.sp)
        Text("฿" + amount, color = if (strong) OrangeDark else Ink, fontWeight = if (strong) FontWeight.Black else FontWeight.Medium, fontSize = if (strong) 19.sp else 14.sp)
    }
}

@Composable
private fun EmptyState(text: String) {
    Column(Modifier.fillMaxWidth().clip(RoundedCornerShape(16.dp)).background(Color(0xFFFAF7F4)).padding(28.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text("🍽️", fontSize = 38.sp)
        Spacer(Modifier.height(8.dp))
        Text(text, color = Muted, fontSize = 13.sp)
    }
}
