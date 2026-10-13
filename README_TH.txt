Random food — Mobile App แบบติดตั้งได้ (PWA)

ไฟล์ในชุดนี้
- index.html: ตัวแอป
- manifest.webmanifest: ข้อมูลติดตั้งแอป
- icon.svg: ไอคอนแอป
- sw.js: แคชไฟล์หลักเพื่อช่วยเปิดซ้ำ

วิธีทดลองบนมือถือ
1) อัปโหลดไฟล์ทั้งหมดในโฟลเดอร์นี้ขึ้นเว็บที่รองรับ HTTPS เช่น GitHub Pages
2) เปิด URL ของเว็บไซต์ด้วย Chrome บน Android หรือ Safari บน iPhone
3) Android: เมนู ⋮ > Install app / Add to Home screen
   iPhone: ปุ่ม Share > Add to Home Screen

หมายเหตุ
- ชุดนี้เป็น Progressive Web App (PWA) ที่ติดตั้งบนหน้าจอมือถือได้ ไม่ใช่ไฟล์ APK หรือ IPA โดยตรง
- ต้องเผยแพร่ผ่าน HTTPS ก่อนจึงจะติดตั้งได้อย่างสมบูรณ์
- ระบบเข้าสู่ระบบ รายงานปัญหา ออเดอร์ และการชำระเงินยังเป็นเดโม ไม่มี backend จริง
- รูปจากอินเทอร์เน็ตอาจต้องออนไลน์
