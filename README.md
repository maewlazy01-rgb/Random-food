# 🍜 Random food — Native Mobile App

โปรเจกต์ Random-food แยกจาก MealMatch และมีซอร์สโค้ด Native แยกตามแพลตฟอร์ม Android และ iPhone ไม่ใช่ PWA

## แพลตฟอร์ม
- **Android:** Kotlin + Jetpack Compose — โฟลเดอร์ `android/`
- **iPhone / iOS:** Swift + SwiftUI — โฟลเดอร์ `ios/RandomFood/`

## ฟีเจอร์ใน Native starter
- แสดงรายการเมนูอาหารตัวอย่าง
- เลือกงบประมาณต่อมื้อและสุ่มเมนูที่อยู่ในงบ
- เลือกเมนูจากรายการ
- เพิ่ม/นำเมนูออกจากรายการโปรด
- หน้าจอ Native พร้อมแท็บนำทาง

## เปิดบน Android
1. เปิด Android Studio แล้วเลือกโฟลเดอร์ `android/`
2. รอ Gradle Sync (ใช้ JDK 17 และ Android SDK ที่ Android Studio แนะนำ)
3. เลือก Android Emulator หรือโทรศัพท์ Android แล้วกด **Run**

หมายเหตุ: ยังไม่ได้แนบ Gradle Wrapper; Android Studio อาจขอให้ตั้งค่า Gradle ก่อน

## เปิดบน iPhone
ต้องใช้ Mac ที่ติดตั้ง Xcode:
1. สร้างโปรเจกต์ใหม่ชนิด **iOS App** ชื่อ `RandomFood` และเลือก SwiftUI
2. นำ `ios/RandomFood/RandomFoodApp.swift` และ `ios/RandomFood/ContentView.swift` ไปใส่ในโปรเจกต์
3. เลือก iPhone Simulator แล้วกด **Run**

## หมายเหตุ
โค้ดนี้เป็น **Native app starter** มีระบบสุ่มเมนูและรายการโปรดพื้นฐาน ยังไม่ใช่ไฟล์ติดตั้งสำเร็จ (.apk/.aab/.ipa) และยังไม่มีระบบหลังบ้าน บัญชีสมาชิกจริง การสั่งซื้อจริง แผนที่/ตำแหน่งร้าน หรือชำระเงินจริง เมนูและราคาเป็นข้อมูลตัวอย่าง

ไฟล์ `index.html` และไฟล์เว็บเดิมยังคงเก็บไว้เป็นต้นแบบอ้างอิง ไม่ได้เป็นตัวแอป Native โปรเจกต์นี้แยกจาก MealMatch โดยสิ้นเชิง

## โครงสร้าง
- `android/` — แอป Android (Kotlin / Jetpack Compose)
- `ios/RandomFood/` — ซอร์สโค้ดแอป iOS (Swift / SwiftUI)
- `index.html` — เว็บต้นแบบเดิม
