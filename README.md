# 🍜 Random food — Native Android App

โปรเจกต์ Random-food ทำเป็นแอป Android แบบ Native ด้วย **Kotlin + Jetpack Compose** แยกจาก MealMatch โดยสิ้นเชิง

## ฟีเจอร์ปัจจุบัน
- แสดงรายการเมนูอาหารตัวอย่าง
- ปรับงบประมาณต่อมื้อและสุ่มเมนูที่อยู่ในงบ
- เลือกเมนูจากรายการ
- เพิ่ม/นำเมนูออกจากรายการโปรด
- หน้าจอ Native พร้อมแท็บนำทาง

## เปิดโปรเจกต์บน Android
1. ติดตั้ง Android Studio และ JDK 17
2. เปิดโฟลเดอร์ `android/` ใน Android Studio
3. ติดตั้ง Android SDK ที่ Android Studio แนะนำ และรอ Gradle Sync
4. เลือก Android Emulator หรือเชื่อมต่อโทรศัพท์ Android แล้วกด **Run**

## สร้าง APK ผ่าน GitHub Actions
มี workflow สำหรับ build APK ที่ไฟล์ `.github/workflows/android.yml`
1. เปิดแท็บ **Actions** ของ repository
2. เลือก **Android APK** แล้วกด **Run workflow** หรือ push การเปลี่ยนแปลงในโฟลเดอร์ `android/`
3. เมื่อ workflow สำเร็จ เปิดรายการรันล่าสุด แล้วดาวน์โหลด artifact ชื่อ `Random-food-Android-debug`
4. แตกไฟล์ ZIP เพื่อรับ `app-debug.apk`

> APK จะดาวน์โหลดได้ก็ต่อเมื่อ workflow รันสำเร็จแล้วเท่านั้น ปัจจุบันยังไม่ได้ยืนยันผล build จากเครื่องมือในบทสนทนานี้

## ขอบเขตตอนนี้
นี่คือเวอร์ชัน Android ระยะแรก เมนูและราคาเป็นข้อมูลตัวอย่าง รายการโปรดยังไม่เชื่อมฐานข้อมูลหรือบัญชีสมาชิก และยังไม่มีระบบค้นหาร้านจริง แผนที่ การสั่งซื้อ หรือชำระเงิน

ไฟล์เว็บเดิม เช่น `index.html` ยังคงอยู่เป็นต้นแบบอ้างอิง แต่ไม่ใช่ตัวแอป Native ส่วนการทำ iPhone พักไว้ก่อนตามแผน และโปรเจกต์นี้ไม่แก้ไขหรือทับ MealMatch

## โครงสร้าง
- `android/` — แอป Android (Kotlin / Jetpack Compose)
- `ios/RandomFood/` — ไฟล์ต้นแบบ iOS ที่เก็บไว้ แต่ยังไม่อยู่ในขอบเขตงานรอบนี้
- `index.html` — เว็บต้นแบบเดิม
