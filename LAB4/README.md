# BÁO CÁO THỰC HÀNH LAB 4: KHẢO SÁT VÀ ĐÁNH GIÁ BỀ MẶT MẠNG BẰNG NMAP

> **Môn học:** An toàn và Bảo mật Hệ thống Thông tin  
> **Sinh viên thực hiện:** Lê Tuấn Kiệt  
> **Lớp:** 11_DH_CNPM1  
> **MSSV:** 1150080022  
> **Video minh chứng (YouTube):** https://youtu.be/nJN9lQsbGfA  
> **Tuyên bố trách nhiệm:** Bài lab chỉ nhằm mục đích nghiên cứu học tập trong môi trường mạng cô lập (Host-Only), không mang tính chất phá hoại hoặc xâm hại đến bất kỳ cá nhân, tổ chức nào.

---

## 1. MÔI TRƯỜNG THỰC HÀNH

Hệ thống được triển khai trên nền tảng ảo hóa **VMware Workstation** với chế độ mạng **Host-Only (VMnet1)**:

| Thiết bị | Vai trò | Hệ điều hành | Địa chỉ IP thực tế | Subnet Mask | Card mạng kết nối |
| :--- | :--- | :--- | :--- | :--- | :--- |
| **Windows Host** | Máy tính thật | Windows 10/11 64-bit | `192.168.47.1` | `255.255.255.0` | VMware Network Adapter VMnet1 |
| **Kali Linux** | Máy quét chính (Scanner) | Kali Linux 2026.x | `192.168.47.130` | `255.255.255.0` | Host-only (VMnet1) |
| **Metasploitable 2** | Máy mục tiêu (Target VM) | Linux (Ubuntu/Debian) | `192.168.47.129` | `255.255.255.0` | Host-only (VMnet1) |
| **VMware DHCP/Gateway** | Dịch vụ mạng ảo | N/A | `192.168.47.254` | `255.255.255.0` | Host-only (VMnet1) |

---

## 2. TIẾN TRÌNH VÀ CÚ PHÁP CÂU LỆNH THỰC HIỆN

Tất cả các lệnh Nmap được thực thi từ cửa sổ dòng lệnh (Terminal) của máy **Kali Linux** hướng vào dải mạng và máy đích:

### 2.1. Kiểm tra kết nối mạng
```bash
# Kiểm tra thông mạng Kali -> Metasploitable 2
ping -c 4 192.168.47.129