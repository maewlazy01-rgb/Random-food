import SwiftUI

struct Food: Identifiable, Hashable {
    let id = UUID()
    let name: String
    let category: String
    let price: Int
    let emoji: String
}

private let sampleFoods = [
    Food(name: "ข้าวกะเพราไก่", category: "อาหารตามสั่ง", price: 55, emoji: "🍛"),
    Food(name: "ข้าวผัดหมู", category: "อาหารตามสั่ง", price: 50, emoji: "🍚"),
    Food(name: "ก๋วยเตี๋ยวหมู", category: "เส้น", price: 50, emoji: "🍜"),
    Food(name: "ส้มตำไก่ย่าง", category: "อีสาน", price: 70, emoji: "🥗"),
    Food(name: "ข้าวมันไก่", category: "จานเดียว", price: 55, emoji: "🍗"),
    Food(name: "ผัดไทย", category: "เส้น", price: 60, emoji: "🍝"),
    Food(name: "ข้าวไข่เจียว", category: "ประหยัด", price: 35, emoji: "🍳"),
    Food(name: "ข้าวหมูแดง", category: "จานเดียว", price: 60, emoji: "🥘")
]

struct ContentView: View {
    @State private var budget = 80.0
    @State private var selectedFood: Food?
    @State private var favorites = Set<String>()
    private let orange = Color(red: 0.953, green: 0.420, blue: 0.247)
    private var visibleFoods: [Food] { sampleFoods.filter { $0.price <= Int(budget) } }

    var body: some View {
        TabView {
            NavigationStack {
                ScrollView {
                    VStack(alignment: .leading, spacing: 16) {
                        VStack(alignment: .leading, spacing: 12) {
                            Text("หิวแล้วใช่ไหม? 😋").font(.system(size: 28, weight: .heavy))
                                .foregroundStyle(Color(red: 0.21, green: 0.14, blue: 0.11))
                            Text("ให้ Random food ช่วยเลือกเมนูมื้อนี้").foregroundStyle(.secondary)
                            Text("งบต่อมื้อ: ฿\(Int(budget))").fontWeight(.bold)
                            Slider(value: $budget, in: 30...150, step: 5).tint(orange)
                            Button { selectedFood = visibleFoods.randomElement() } label: {
                                Label("สุ่มเมนูให้หน่อย", systemImage: "dice.fill").fontWeight(.bold)
                                    .frame(maxWidth: .infinity).padding(.vertical, 7)
                            }.buttonStyle(.borderedProminent).tint(orange)
                        }
                        .padding(20)
                        .background(Color(red: 1, green: 0.933, blue: 0.898), in: RoundedRectangle(cornerRadius: 24))

                        if let food = selectedFood {
                            HStack(spacing: 14) {
                                Text(food.emoji).font(.system(size: 42))
                                VStack(alignment: .leading, spacing: 4) {
                                    Text("วันนี้กินเมนูนี้!").font(.caption).foregroundStyle(orange)
                                    Text(food.name).font(.title3.bold())
                                    Text("฿\(food.price) · \(food.category)").foregroundStyle(.secondary)
                                }
                                Spacer()
                                Button { favorites.insert(food.name) } label: {
                                    Image(systemName: "heart.fill").foregroundStyle(orange)
                                }.accessibilityLabel("บันทึกรายการโปรด")
                            }.padding(16).background(.white, in: RoundedRectangle(cornerRadius: 18))
                        }

                        Text("เมนูตามงบ").font(.title2.bold())
                        ForEach(visibleFoods) { food in
                            HStack(spacing: 12) {
                                Text(food.emoji).font(.system(size: 30)).frame(width: 56, height: 56)
                                    .background(Color(red: 1, green: 0.933, blue: 0.898), in: RoundedRectangle(cornerRadius: 14))
                                VStack(alignment: .leading, spacing: 4) {
                                    Text(food.name).fontWeight(.bold)
                                    Text(food.category).font(.caption).foregroundStyle(.secondary)
                                    Text("฿\(food.price)").fontWeight(.bold).foregroundStyle(orange)
                                }
                                Spacer()
                                Button("เลือก") { selectedFood = food }.buttonStyle(.bordered).tint(orange)
                                Button {
                                    if favorites.contains(food.name) { favorites.remove(food.name) }
                                    else { favorites.insert(food.name) }
                                } label: {
                                    Image(systemName: favorites.contains(food.name) ? "heart.fill" : "heart")
                                        .foregroundStyle(favorites.contains(food.name) ? orange : .gray)
                                }.buttonStyle(.plain).accessibilityLabel("สลับรายการโปรด")
                            }.padding(12).background(.white, in: RoundedRectangle(cornerRadius: 18))
                        }
                    }.padding(16)
                }
                .background(Color(red: 1, green: 0.992, blue: 0.984))
                .navigationTitle("🍜 Random food").navigationBarTitleDisplayMode(.inline)
            }.tabItem { Label("เมนูอาหาร", systemImage: "fork.knife") }

            NavigationStack {
                List(sampleFoods.filter { favorites.contains($0.name) }) { food in
                    HStack {
                        Text(food.emoji).font(.largeTitle)
                        VStack(alignment: .leading) {
                            Text(food.name).fontWeight(.bold)
                            Text("฿\(food.price) · \(food.category)").foregroundStyle(.secondary)
                        }
                    }
                }.navigationTitle("รายการโปรด")
            }.tabItem { Label("รายการโปรด", systemImage: "heart.fill") }
        }.tint(orange)
    }
}
