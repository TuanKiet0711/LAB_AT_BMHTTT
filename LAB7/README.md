BÁO CÁO TIẾN ĐỘ THỰC HÀNH AN TOÀN HỆ THỐNG THÔNG TIN
LAB 7: WEB PROXY VÀ KIỂM SOÁT NỘI DUNG (SQUID + E2GUARDIAN + CLAMAV)

THÔNG TIN SINH VIÊN:

- Họ và tên: Lê Tuấn Kiệt
- Mã số sinh viên: 1150080022
- Lớp: 11_ĐH_CNPM1
- Trường: Đại học Tài nguyên và Môi trường TP. Hồ Chí Minh
- Môi trường triển khai: pfSense 2.7.2, Windows Server 2025, Ubuntu Server 24.04 trên VMware Workstation

================================================================================

1. # BẢNG PHÂN BỔ ĐỊA CHỈ IP & THIẾT BỊ MÔ HÌNH
   +--------------------+---------------+-----------------+---------------+---------------+-----------------------+
   | Thiết bị | Interface | IP Address | Subnet Mask | Gateway | DNS |
   +--------------------+---------------+-----------------+---------------+---------------+-----------------------+
   | pfSense Firewall | WAN | DHCP (Bridged) | 255.255.255.0 | Dynamic | Upstream ISP |
   | pfSense Firewall | LAN | 10.6.0.1 | 255.255.255.0 | --- | --- |
   | pfSense Firewall | DMZ | 172.16.6.1 | 255.255.255.0 | --- | --- |
   | DC01 | LAN (VMnet2) | 10.6.0.10 | 255.255.255.0 | 10.6.0.1 | 127.0.0.1 (Fwd:8.8.8.8)|
   | WEB-PROXY | LAN (VMnet2) | 10.6.0.254 | 255.255.255.0 | 10.6.0.1 | 10.6.0.10, 8.8.8.8 |
   | DMZ-WEB | DMZ | 172.16.6.10 | 255.255.255.0 | 172.16.6.1 | --- |
   | Client-A / Client-B| LAN (VMnet2) | 10.6.0.100 - 200| 255.255.255.0 | 10.6.0.1 | 10.6.0.10 |
   +--------------------+---------------+-----------------+---------------+---------------+-----------------------+

================================================================================ 2. CÁC NỘI DUNG VÀ THAO TÁC ĐÃ HOÀN THÀNH
================================================================================

[x] BƯỚC 1: KHỞI TẠO CÁC ALIAS TRÊN FIREWALL pfSense

- Tạo IP Alias:
  - LAB7_PROXY: 10.6.0.254 (Địa chỉ máy chủ Web Proxy).
  - LAB7_CLIENTS: 10.6.0.100 - 10.6.0.200 (Dải IP cấp cho các máy trạm kiểm thử).
- Tạo Port Alias:
  - WEB_PORTS: 80 (HTTP), 443 (HTTPS).
  - MAIL_PORTS: 25, 465, 587 (SMTP), 110, 995 (POP3), 143, 993 (IMAP).

[x] BƯỚC 2: THIẾT LẬP VÀ ÁP DỤNG BỘ 7 LUẬT LỌC TRÊN CỔNG LAN (pfSense)

- Triển khai và sắp xếp chính xác thứ tự luật (First-Match) để ép duyệt web qua proxy và chống bypass:
  - Rule 1 (Pass): LAN subnets -> 10.6.0.10 Port 53 (TCP/UDP) | DNS to DC01.
  - Rule 2 (Pass): LAB7_CLIENTS -> LAB7_PROXY Port 8080 (TCP) | Client dùng E2Guardian.
  - Rule 3 (Pass): LAN subnets -> Any Port MAIL_PORTS (TCP) | Cho email theo yêu cầu gốc.
  - Rule 4 (Block): LAB7_CLIENTS -> Any Port WEB_PORTS (TCP) | Chặn web trực tiếp chống bypass (bật Log).
  - Rule 5 (Pass): LAB7_PROXY -> Any Port WEB_PORTS (TCP) | Cho proxy đi HTTP/HTTPS ra ngoài.
  - Rule 6 (Pass): LAB7_PROXY -> 172.16.6.10 Port 80 (TCP) | Cho proxy vào site test DMZ.
  - Rule 7 (Block): LAN subnets -> Any (IPv4 \*) | Explicit deny cho bài thực hành.
- Áp dụng thay đổi (Apply Changes) thành công trên hệ thống pfSense.

[x] BƯỚC 3: DỰNG MÁY ẢO WINDOWS SERVER (DC01)

- Cài đặt hoàn tất hệ điều hành Windows Server 2025 Standard (Desktop Experience).
- Cài đặt VMware Tools và thiết lập kết nối mạng trên card VMnet2.

================================================================================ 3. CÁC HẠNG MỤC CẦN TRIỂN KHAI TIẾP THEO
================================================================================

[ ] Cấu hình IP tĩnh cho DC01 (10.6.0.10/24, Gateway: 10.6.0.1).
[ ] Cài đặt dịch vụ DNS Server trên DC01.
[ ] Tạo Forward Lookup Zone "lab7.test" và bản ghi Wildcard (\* A 172.16.6.10).
[ ] Kiểm tra phân giải tên miền từ PowerShell (Resolve-DnsName normal.lab7.test).
[ ] Hoàn tất cài đặt máy ảo Ubuntu Server 24.04 (WEB-PROXY) trên card VMnet2.
[ ] Cấu hình Netplan đặt IP tĩnh 10.6.0.254/24 cho WEB-PROXY.
[ ] Cài đặt và tích hợp chuỗi dịch vụ: Squid (port 3128), E2Guardian (port 8080) và ClamAV.
[ ] Cấu hình Explicit Proxy trên Client và kiểm thử các kịch bản lọc web, chặn virus EICAR, kiểm tra bypass.
