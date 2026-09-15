# BÁO CÁO THỰC HÀNH AN TOÀN HỆ THỐNG THÔNG TIN - LAB 1

## Thông tin sinh viên

- **Họ và tên:** Lê Tuấn Kiệt
- **Mã số sinh viên:** 1150080022
- **Lớp:** 11_DH_CNPM1
- **Link video demo (YouTube):** https://youtu.be/19mZKy0q9v4?si=aOayOhDYd6g-Htw8

---

## Tên bài Lab

**LAB 1: Cấu hình, Bắt gói và Phân tích An toàn Giao thức Kết nối Từ xa (Telnet vs SSH)**

---

## Nội dung đã thực hiện

1. **Thiết lập và cô lập môi trường ảo hóa:**
   - Cài đặt hệ điều hành bảo mật Kali Linux trên phần mềm VMware Workstation Pro 17.
   - Sử dụng kỹ thuật nhân bản liên kết (Linked Clone) tạo máy ảo thứ 2 mang tên `Client_10.0.0.2` từ máy nguồn `LAB1`.
   - Kết nối 2 máy ảo vào chung một Switch nội bộ thông qua cơ chế **LAN Segment (`Lab1`)**, đảm bảo cô lập 100% trên máy ảo, không ảnh hưởng hay phụ thuộc vào máy vật lý ngoài đời thực.
2. **Cấu hình địa chỉ mạng và triển khai dịch vụ:**
   - Cấu hình địa chỉ IP tĩnh:
     - Máy Server (`LAB1`): `10.0.0.1/24` (gán trên giao diện `eth0`).
     - Máy Client (`Client_10.0.0.2`): `10.0.0.2/24` (gán trên giao diện `eth0`).
   - Cài đặt và kích hoạt dịch vụ truyền thông từ xa trên Server:
     - Dịch vụ Telnet Server: Chạy qua trình điều phối mạng `openbsd-inetd` (lắng nghe TCP port 23).
     - Dịch vụ SSH Server: `openssh-server` (lắng nghe TCP port 22).
   - Khởi tạo tài khoản thực nghiệm trên Server theo đúng quy ước: `username` = `letuankiet`, `password` = `1150080022`.
3. **Thực nghiệm kết nối và phân tích an toàn lưu lượng:**
   - Sử dụng công cụ Wireshark trên máy Client để bắt toàn bộ lưu lượng trên card mạng `eth0`.
   - Lọc và bắt gói tin Telnet (`tcp.port == 23`): Đăng nhập tài khoản sinh viên, thực hiện các lệnh `whoami`, `ls -la`. Sử dụng tính năng **Follow TCP Stream** để chứng minh dữ liệu bị lộ dưới dạng Cleartext.
   - Lọc và bắt gói tin SSH (`tcp.port == 22`): Đăng nhập tài khoản qua SSH, phân tích dòng gói tin để chứng minh toàn bộ payload và thông tin xác thực đã được mã hóa an toàn (`Encrypted Packet Payload`).

---

## Kết quả thực hiện

1. **Giao thức Telnet (Port 23):**
   - **Kết quả:** Wireshark bắt trọn vẹn tiến trình truyền thông. Khi phân tích bằng Follow TCP Stream, toàn bộ thông tin tài khoản đăng nhập (`letuankiet` / `1150080022`) cùng các câu lệnh thao tác hiển thị hoàn toàn dưới dạng ký tự rõ (Cleartext).
   - **Đánh giá:** Telnet vi phạm nghiêm trọng tính bí mật (**Confidentiality**) và tính toàn vẹn (**Integrity**). Mật khẩu dài hay phức tạp đều vô nghĩa trước tấn công bắt gói Sniffing.
2. **Giao thức SSH (Port 22):**
   - **Kết quả:** Wireshark chỉ nhìn thấy siêu dữ liệu (Metadata) như IP, Port, cờ TCP, kích thước gói. Toàn bộ nội dung phiên truyền được mã hóa hoàn toàn, không thể đọc trộm thông tin hay mật khẩu.
   - **Đánh giá:** SSH bảo vệ toàn diện 3 thuộc tính: Confidentiality (mã hóa đối xứng AES), Integrity (mã kiểm tra HMAC), Authentication (xác thực máy chủ bằng Host key và người dùng bằng SSH key/mật khẩu an toàn).

---

## Các lưu ý cần thiết để kiểm tra hoặc chạy lại bài làm

1. **Cấu hình card mạng máy ảo:**
   - Cả 2 máy ảo `LAB1` và `Client_10.0.0.2` phải được đặt chung ở chế độ mạng: **VM Settings -> Network Adapter -> LAN Segment: Lab1**.
2. **Lệnh khôi phục mạng nhanh (nếu khởi động lại bị mất IP):**
   - **Trên máy Server (`LAB1`):**
     ```bash
     sudo ip link set eth0 up
     sudo ip addr flush dev eth0
     sudo ip addr add 10.0.0.1/24 dev eth0
     sudo systemctl restart openbsd-inetd ssh
     ```
   - **Trên máy Client (`Client_10.0.0.2`):**
     ```bash
     sudo ip link set eth0 up
     sudo ip addr flush dev eth0
     sudo ip addr add 10.0.0.2/24 dev eth0
     ping -c 2 10.0.0.1
     ```
3. **Kiểm tra trạng thái cổng dịch vụ trên Server:**
   - Chạy lệnh `ss -ltn` trên Server: Phải có cả hai dòng `*:22` và `*:23` ở trạng thái `LISTEN`.
4. **Thao tác bắt gói tin trên Client:**
   - Mở Wireshark chọn card `eth0`.
   - Bộ lọc kiểm tra Telnet: `tcp.port == 23` (lệnh kết nối: `telnet 10.0.0.1`).
   - Bộ lọc kiểm tra SSH: `tcp.port == 22` (lệnh kết nối: `ssh letuankiet@10.0.0.1`).
