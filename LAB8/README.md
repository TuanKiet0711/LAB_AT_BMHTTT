# BÁO CÁO TIẾN ĐỘ THỰC HÀNH AN TOÀN HỆ THỐNG THÔNG TIN

## BÀI THỰC HÀNH: LOCAL DNS ATTACK (DNS SPOOFING & CACHE POISONING)

### THÔNG TIN SINH VIÊN

- **Họ và tên:** Lê Tuấn Kiệt
- **Mã số sinh viên:** 1150080022
- **Lớp:** 11_ĐH_CNPM1
- **Năm thực hiện:** 2026
- **Môi trường triển khai:** pfSense 2.7.2, Ubuntu Server 24.04 (BIND9), Ubuntu Client, Kali Linux trên nền tảng VMware Workstation / VirtualBox.

---

### 1. BẢNG PHÂN BỔ THÔNG SỐ ĐỊA CHỈ IP & THIẾT BỊ MÔ HÌNH

| Thiết bị       | Interface / Vị trí      | IP Address / CIDR  | Gateway    | DNS Phân giải | Vai trò trong bài Lab                    |
| :------------- | :---------------------- | :----------------- | :--------- | :------------ | :--------------------------------------- |
| **pfSense**    | WAN (`em0`)             | DHCP (Bridged/NAT) | Dynamic    | ISP DNS       | Tường lửa kết nối Internet ngoài         |
| **pfSense**    | MGMT (`em1`)            | 192.168.56.254/24  | ---        | ---           | Quản trị pfSense WebGUI                  |
| **pfSense**    | LAN (`em2`)             | 10.6.0.1/24        | ---        | ---           | Phân đoạn mạng LAN-DOMAIN cũ (Lab 7/8)   |
| **pfSense**    | DMZ (`em3`)             | 172.16.6.1/24      | ---        | ---           | Vùng chứa máy chủ dịch vụ nội bộ         |
| **pfSense**    | DNSLAB (`em4`)          | 10.9.0.1/24        | ---        | ---           | Gateway phân đoạn mạng tấn công cô lập   |
| **DNS-SERVER** | `dns-lab` (LAN Segment) | 10.9.0.10/24       | 10.9.0.1   | 127.0.0.1     | Recursive & Caching Resolver (BIND9)     |
| **VICTIM**     | `dns-lab` (LAN Segment) | 10.9.0.100/24      | 10.9.0.1   | 10.9.0.10     | Máy trạm client mục tiêu bị tấn công     |
| **ATTACKER**   | `dns-lab` (Promiscuous) | 10.9.0.200/24      | 10.9.0.1   | 10.9.0.10     | Kali Linux chạy Scapy & Fake Web         |
| **APP-TEST**   | DMZ (`dmz-net`)         | 172.16.6.20/24     | 172.16.6.1 | ---           | Authoritative DNS trễ (`delay 1.5s`)     |
| **DMZ-WEB**    | DMZ (`dmz-net`)         | 172.16.6.10/24     | 172.16.6.1 | ---           | Web Server hợp lệ (`external.lab9.test`) |

---

### 2. MA TRẬN LUẬT TƯỜNG LỬA TRÊN CỔNG DNSLAB (PFSENSE FIREWALL RULES)

_Quy tắc duyệt luật: First-Match (từ trên xuống dưới)._

|  STT  |  Action   |   Log   |   Protocol   |      Source      |    Destination    |   Port    | Mục đích kỹ thuật                                      |
| :---: | :-------: | :-----: | :----------: | :--------------: | :---------------: | :-------: | :----------------------------------------------------- |
| **1** | **Pass**  |   No    | IPv4 TCP/UDP |   `10.9.0.10`    |   `172.16.6.20`   | 53 (DNS)  | Cho phép DNS Resolver chuyển tiếp query tới Auth-DNS   |
| **2** | **Pass**  |   No    |   IPv4 TCP   | `DNSLAB subnets` |   `172.16.6.10`   | 80 (HTTP) | Cho phép Client truy cập website hợp lệ trong DMZ      |
| **3** | **Block** | **Yes** |   IPv4 \*    | `DNSLAB subnets` |   `10.6.0.0/24`   |    Any    | Bảo vệ vùng mạng LAN cũ (LAN-DOMAIN)                   |
| **4** | **Block** | **Yes** |   IPv4 \*    | `DNSLAB subnets` | `192.168.56.0/24` |    Any    | Bảo vệ vùng mạng quản trị MGMT                         |
| **5** | **Block** | **Yes** |   IPv4 \*    | `DNSLAB subnets` |        Any        |    Any    | Cách ly mặc định, chặn toàn bộ kết nối ra WAN/Internet |

---

### 3. TIẾN ĐỘ VÀ QUY TRÌNH THỰC HIỆN CHI TIẾT

#### Giai đoạn 1: Chuẩn bị hạ tầng mạng & Cấu hình pfSense

- [x] Tạo snapshot an toàn `LAB9_START_FROM_LAB8` trước khi can thiệp cấu hình.
- [x] Thêm Network Adapter 5 trên VMware Workstation gán vào LAN Segment `dns-lab`.
- [x] Reset mật khẩu `admin` pfSense console về mặc định `pfsense`.
- [x] Gán Interface mới `em4`, định danh tên `DNSLAB`, cấu hình IP `10.9.0.1/24` (Gateway: None).
- [x] Thiết lập 5 luật Firewall Rules trên tab DNSLAB và thực thi Reset State Table.

#### Giai đoạn 2: Triển khai máy chủ DNS-SERVER BIND9

- [x] Đặt IP tĩnh Ubuntu Server `10.9.0.10/24`, Gateway `10.9.0.1`.
- [x] Cài đặt `bind9` và `dnsutils`.
- [x] Cấu hình `/etc/bind/named.conf.options`: thiết lập cho phép đệ quy dải `10.9.0.0/24` và thêm khối `server 172.16.6.20 { send-cookie no; };`.
- [x] Cấu hình `/etc/bind/named.conf.local`: thiết lập forward zone `external.lab9.test` trỏ tới `172.16.6.20`.
- [x] Kiểm tra zone bằng `named-checkconf`, `named-checkzone` và xác nhận dịch vụ chạy cổng 53.

#### Giai đoạn 3: Cấu hình Client Victim & Attacker Kali

- [x] Cấu hình Victim: IP `10.9.0.100/24`, Gateway `10.9.0.1`, DNS Server trỏ duy nhất `10.9.0.10`.
- [x] Cấu hình Kali: IP `10.9.0.200/24`, bật card mạng chế độ `Promiscuous Mode = Allow VMs`.
- [x] Dựng máy chủ Fake HTTP Server tại thư mục `~/LAB9/fake_site/` lắng nghe cổng 80 trên Kali.

#### Giai đoạn 4: Xác thực Baseline trước tấn công

- [x] Khởi chạy authoritative server mô phỏng trên APP-TEST: `sudo python3 auth_dns_delayed.py --bind 172.16.6.20 --delay 1.5`.
- [x] Thực thi `sudo rndc flush` trên DNS-SERVER.
- [x] Trên Victim chạy `dig @10.9.0.10 www.external.lab9.test A +noall +answer` và `curl http://www.external.lab9.test`.
- [x] **Xác nhận Baseline:** Kết quả phân giải trả về chính xác IP `172.16.6.10` (DMZ-WEB).

#### Giai đoạn 5: Thực hiện các kịch bản tấn công

- [x] **Task 1 - Local Hosts File Modification:**
  - Sửa `/etc/hosts` trên Victim thêm dòng `10.9.0.200 www.lab9.test`.
  - `curl` trỏ về trang web giả `10.9.0.200`; `dig` vẫn trả kết quả gốc từ DNS-SERVER.
  - Khôi phục file backup `/etc/hosts.lab9.bak`.
- [x] **Task 2 - Direct DNS Spoofing:**
  - Chạy `sudo python3 dns_spoof_lab9.py --mode victim --iface eth1` trên Kali.
  - Bắt gói Wireshark: Fake response gửi đến Victim trước với đúng Transaction ID và UDP Port.
  - Victim nhận địa chỉ `10.9.0.200`.
- [x] **Task 3 - DNS Cache Poisoning:**
  - Chạy `sudo python3 dns_spoof_lab9.py --mode resolver --iface eth1` trên Kali.
  - Victim gửi truy vấn; Kali gửi gói tin giả mạo tới `10.9.0.10` (DNS-SERVER).
  - Dump bộ nhớ cache BIND9: `sudo rndc dumpdb -cache`. Bản ghi `www.external.lab9.test` bị nhiễm độc trỏ về `10.9.0.200`.
  - Tắt script tấn công, kiểm tra các client khác vẫn bị chuyển hướng về web giả.

#### Giai đoạn 6: Khôi phục và Hardening hệ thống

- [x] Dừng script Scapy và Fake HTTP Server trên Kali.
- [x] Chuyển Promiscuous Mode của Kali về lại `Deny`.
- [x] Xóa sạch cache (`rndc flush`, `resolvectl flush-caches`).
- [x] Xác nhận hệ thống phân giải trở lại bình thường về `172.16.6.10`.
