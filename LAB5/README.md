BÁO CÁO TIẾN ĐỘ THỰC HÀNH AN TOÀN HỆ THỐNG THÔNG TIN
LAB 3: THIẾT LẬP MÔ HÌNH TƯỜNG LỬA pfSense (pfSense Firewall Configuration)

THÔNG TIN SINH VIÊN:

- Họ và tên: Lê Tuấn Kiệt
- Mã số sinh viên: 1150080022
- Lớp: 11_ĐH_CNPM1
- Khoa: Công nghệ Thông tin
- Trường: Đại học Tài nguyên và Môi trường TP. Hồ Chí Minh
- Môi trường triển khai: pfSense CE 2.7.2 (amd64) trên VMware Workstation Pro 17

================================================================================

1. # BẢNG PHÂN BỔ ĐỊA CHỈ IP & THIẾT BỊ MÔ HÌNH
   +--------------------+---------------+-----------------+---------------+---------------+-----------------------+
   | Thiết bị | Interface | IP Address | Subnet Mask | Gateway | DNS |
   +--------------------+---------------+-----------------+---------------+---------------+-----------------------+
   | pfSense | WAN (Adapter1)| DHCP (Bridged) | 255.255.255.0 | Dynamic | Upstream ISP |
   | pfSense | LAN (Adapter2)| 10.0.0.1 | 255.0.0.0 (/8)| --- | --- |
   | pfSense | DMZ (Adapter3)| 172.16.0.1 | 255.255.0.0/16| --- | --- |
   | Domain Controller | LAN (VMnet1) | 10.0.0.2 | 255.0.0.0 (/8)| 10.0.0.1 | 10.0.0.2 (Fwd:8.8.8.8)|
   | Máy thật (Host) | LAN (VMnet1) | 10.0.0.100 | 255.0.0.0 (/8)| (Để trống) | (Để trống) |
   | DMZ-Web Server | DMZ (dmz-net) | 172.16.0.2 | 255.255.0.0/16| 172.16.0.1 | 8.8.8.8 |
   +--------------------+---------------+-----------------+---------------+---------------+-----------------------+

================================================================================ 2. CÁC NỘI DUNG VÀ THAO TÁC ĐÃ HOÀN THÀNH
================================================================================

[x] BƯỚC 1: CẤU HÌNH HẠ TẦNG MẠNG TRÊN MÁY THẬT & VMWARE WORKSTATION - Virtual Network Editor: VMnet1 (Host-only), Subnet 10.0.0.0/8, tắt DHCP. - Cấu hình IP card VMnet1 máy thật: 10.0.0.100, Subnet mask 255.0.0.0 (không gateway/DNS). - Tạo mạng nội bộ LAN Segment: định danh "dmz-net".

[x] BƯỚC 2: KHỞI TẠO MÁY ẢO pfSense - Cấu hình phần cứng: FreeBSD 13 (64-bit), 2 GB RAM, 2 vCPU, 20 GB Virtual Disk. - Gán đủ 3 card mạng: + Adapter 1: Bridged -> Cổng WAN kết nối Internet ngoài. + Adapter 2: Custom (VMnet1) -> Cổng LAN (10.0.0.0/8). + Adapter 3: LAN Segment (dmz-net) -> Cổng DMZ (172.16.0.0/16). - Nạp file ISO: pfSense-CE-2.7.2-RELEASE-amd64.iso.

[x] BƯỚC 3: CÀI ĐẶT HỆ ĐIỀU HÀNH pfSense - Phân vùng tự động bằng Auto ZFS (Stripe trên ổ ảo da0). - Giải nén gói base.txz và cài đặt thành công, reboot hệ thống.

[x] BƯỚC 4: THIẾT LẬP CỔNG LAN QUA MÀN HÌNH CONSOLE - Chọn Option 2 (Set interface(s) IP address) cho cổng LAN (em1). - Đặt IPv4 LAN: 10.0.0.1, Subnet bit count: 8 (255.0.0.0). - Tắt DHCP server trên LAN, giữ giao thức HTTPS (https://10.0.0.1/). - Máy thật ping kiểm tra IP 10.0.0.1 phản hồi thành công (0% loss).

[x] BƯỚC 5: CẤU HÌNH BAN ĐẦU QUA TRÌNH DUYỆT WEB (SETUP WIZARD & DASHBOARD) - Truy cập https://10.0.0.1 bằng tài khoản admin. - Thiết lập DNS 8.8.8.8, Timezone: Asia/Ho_Chi_Minh. - Bỏ chọn chặn RFC1918 và bogon networks trên cổng WAN. - Đổi mật khẩu tài khoản admin và chuyển vào giao diện Dashboard.

[x] BƯỚC 6: CẤU HÌNH VÙNG DMZ & OUTBOUND NAT - Gán card mạng em2 làm cổng OPT1, đổi tên thành DMZ. - Đặt IP tĩnh cho DMZ: 172.16.0.1/16, Gateway để None. - Firewall -> NAT -> Outbound: kích hoạt "Hybrid Outbound NAT rule generation". - Hệ thống tự động sinh rule NAT cho cả 2 dải 10.0.0.0/8 và 172.16.0.0/16.

[x] BƯỚC 7: CHUẨN HÓA RULESET LAN & TẠO RULE NỀN TẢNG (BASELINE) - Firewall -> Rules -> LAN: Vô hiệu hóa (Disable) 2 rule mặc định "Default allow LAN to any rule". - Giữ nguyên Anti-Lockout Rule để bảo toàn quyền truy cập WebGUI. - Tạo rule nền tảng mới: + Action: Pass | Protocol: Any | Interface: LAN (IPv4) + Source: LAN subnets (10.0.0.0/8) | Destination: Any + Description: LAN to Internet - Diagnostics -> States -> Reset States: Tích chọn "Reset the firewall state table" và thực thi Reset.

================================================================================ 3. CÁC HẠNG MỤC CẦN TRIỂN KHAI TIẾP THEO
================================================================================
[ ] Dựng máy ảo Windows Server làm Domain Controller (IP: 10.0.0.2/8, Gateway: 10.0.0.1).
[ ] Cài đặt dịch vụ AD DS (forest vietnam.local) và DNS Forwarders (8.8.8.8).
[ ] Kiểm thử bật/tắt rule nền tảng và kiểm tra ping/curl từ Domain Controller.
[ ] Dựng máy ảo DMZ-Web (IP: 172.16.0.2/16) trên card LAN Segment dmz-net, cài IIS.
[ ] Thực hiện 5 tình huống firewall (Chặn ICMP, Chỉ định Host ra mạng, Cô lập DMZ, Port Forward WAN 8080->80, Logging).
