================================================================================
BÁO CÁO TIẾN ĐỘ THỰC HÀNH AN TOÀN HỆ THỐNG THÔNG TIN
LAB 3: THIẾT LẬP MÔ HÌNH TƯỜNG LỬA pfSense (pfSense Firewall Configuration)
================================================================================

THÔNG TIN SINH VIÊN:

- Họ và tên: Lê Tuấn Kiệt
- Mã số sinh viên: 1150080022
- Lớp: 11_ĐH_CNPM1
- Môi trường triển khai: pfSense CE 2.7.2 (amd64) trên VMware Workstation Pro 17

================================================================================

1. # CÁC NỘI DUNG VÀ THAO TÁC ĐÃ HOÀN THÀNH

[x] BƯỚC 1: XÁC THỰC VÀ GIẢI NÉN BỘ CÀI ĐẶT - Tệp gốc: pfSense-CE-2.7.2-RELEASE-amd64.iso.gz. - Giải nén bằng 7-Zip để thu được tệp đĩa quang: pfSense-CE-2.7.2-RELEASE-amd64.iso.

[x] BƯỚC 2: CẤU HÌNH HẠ TẦNG MẠNG TRÊN MÁY THẬT & VMWARE WORKSTATION - Cấu hình card LAN ảo trên VMware Virtual Network Editor: + Sử dụng VMnet1 (Host-only). + Subnet IP: 10.0.0.0, Subnet Mask: 255.0.0.0 (/8). + Tắt hoàn toàn dịch vụ DHCP Server nội bộ của VMnet1. - Cấu hình địa chỉ IP tĩnh cho card mạng ảo máy thật (VMware Network Adapter VMnet1): + IPv4 Address: 10.0.0.100 + Subnet Mask: 255.0.0.0 + Default Gateway & DNS: Để trống hoàn toàn theo đúng quy chuẩn lab để tránh xung đột định tuyến mạng thật. - Tạo phân vùng mạng cô lập DMZ: + Tạo mạng nội bộ LAN Segment với định danh: "dmz-net".

[x] BƯỚC 3: KHỞI TẠO MÁY ẢO pfSense - Hệ điều hành ảo: FreeBSD 13 (64-bit). - Cấu hình tài nguyên: 2 GB RAM, 2 vCPU, 20 GB Virtual Disk (Single file). - Gắn đủ 3 Card mạng ảo theo kiến trúc phân vùng: + Network Adapter 1: Bridged (Automatic) -> Đóng vai trò cổng WAN kết nối Internet. + Network Adapter 2: Custom (VMnet1) -> Đóng vai trò cổng LAN (10.0.0.0/8). + Network Adapter 3: LAN Segment (dmz-net) -> Đóng vai trò cổng DMZ (172.16.0.0/16). - Gắn file ISO vào ổ đĩa ảo CD/DVD IDE.

[x] BƯỚC 4: TIẾN TRÌNH CÀI ĐẶT pfSense CE 2.7.2 - Khởi động máy ảo vào bộ cài đặt pfSense Installer. - Định dạng phân vùng đĩa cứng tự động bằng ZFS (Auto ZFS), chọn ổ cứng ảo da0 (20 GiB) ở chế độ Stripe. - Bung gói hệ điều hành thành công, từ chối mở Manual Shell và tiến hành Reboot hệ thống. - Ngắt kết nối ổ đĩa CD/DVD để hệ thống nạp trực tiếp từ đĩa cứng.

[x] BƯỚC 5: THIẾT LẬP GIAO DIỆN LAN QUA CONSOLE pfSense - Truy cập Console thông qua menu lựa chọn hệ thống: + Chọn Option 2 (Set interface(s) IP address) để cấu hình cổng LAN (em1). + Tắt DHCP trên IPv4 cổng LAN. + Đặt địa chỉ IPv4 LAN mới: 10.0.0.1 + Đặt Subnet bit count: 8 (tương ứng 255.0.0.0). + Bỏ qua cấu hình Gateway và IPv6. + Xác nhận không revert về HTTP (giữ giao thức an toàn HTTPS). - pfSense hoàn tất áp dụng cấu hình và xuất đường dẫn quản trị: https://10.0.0.1/

[x] BƯỚC 6: CẤU HÌNH BAN ĐẦU QUA TRÌNH DUYỆT WEB (SETUP WIZARD & DASHBOARD) - Từ máy thật truy cập https://10.0.0.1, đăng nhập bằng tài khoản mặc định admin / pfsense. - Hoàn thành Setup Wizard 9 bước: + Thiết lập Primary DNS Server: 8.8.8.8, Timezone: Asia/Ho_Chi_Minh. + Bỏ chọn 2 mục chặn trên cổng WAN: "Block RFC1918 Private Networks" và "Block bogon networks" để phục vụ bài lab. + Xác nhận lại thông số LAN 10.0.0.1/8. + Đổi mật khẩu quản trị tài khoản admin mới. - pfSense nạp thành công giao diện Dashboard với trạng thái hoạt động của CPU, RAM và các cổng mạng.

[x] BƯỚC 7: CẤU HÌNH VÙNG DMZ & NAT TỰ ĐỘNG - Vào Interfaces -> Assignments: Thêm card mạng em2 làm cổng OPT1. - Vào Interfaces -> OPT1: Đổi tên thành DMZ, chọn Static IPv4, gán IP: 172.16.0.1/16 (IPv4 Upstream gateway để None). - Vào Firewall -> NAT -> Outbound: Chuyển chế độ sang "Hybrid Outbound NAT rule generation", hệ thống tự động sinh rule NAT cho dải 10.0.0.0/8 và 172.16.0.0/16 ra Internet.

[x] BƯỚC 8: CHUẨN HÓA RULESET LAN & TẠO RULE NỀN TẢNG (BASELINE) - Vào Firewall -> Rules -> LAN: + Vô hiệu hóa (Disable / Toggle) 2 rule mặc định: "Default allow LAN to any rule" (IPv4 và IPv6). + Giữ nguyên "Anti-Lockout Rule" để bảo toàn kết nối WebGUI. - Tạo mới Firewall Rule nền tảng do người quản trị cấu hình: + Action: Pass + Interface: LAN (IPv4) + Protocol: Any + Source: LAN subnets (tương đương 10.0.0.0/8) + Destination: Any + Description: LAN to Internet - Thực hiện quy trình xóa sạch phiên kết nối: + Vào Diagnostics -> States -> Reset States. + Đánh dấu "Reset the firewall state table" và thực thi Reset để pfSense làm mới toàn bộ bảng theo dõi trạng thái.

# ================================================================================ 2. CÁC HẠNG MỤC CẦN TRIỂN KHAI TIẾP THEO

[ ] Dựng máy ảo Windows Server làm Domain Controller (IP: 10.0.0.2/8, Gateway: 10.0.0.1).
[ ] Cài đặt vai trò AD DS, DNS Server và cấu hình DNS Forwarders (8.8.8.8).
[ ] Kiểm thử bật/tắt rule nền tảng và thu thập kết quả ping/curl trên máy DC.
[ ] Dựng máy ảo DMZ-Web (IP: 172.16.0.2/16) cài IIS trên mạng LAN Segment dmz-net.
[ ] Thực hiện 5 tình huống firewall (Chặn ICMP, Chỉ định Host ra mạng, Cô lập DMZ, Port Forward WAN 8080->80, Logging).
================================================================================
