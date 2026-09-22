# BÁO CÁO THỰC HÀNH LAB 3

**Môn học:** An toàn Hệ thống Thông tin / An toàn Thông tin  
**Bài thực hành:** Lab 3: Nhận diện và ứng phó các mối đe dọa đến an toàn thông tin[cite: 1]

---

## 1. THÔNG TIN SINH VIÊN

- **Họ và tên:** Lê Tuấn Kiệt[cite: 17, 18, 19]
- **Lớp:** 11_DH_CNPM1[cite: 17, 18, 19]
- **Mã số sinh viên (MSSV):** 1150080022[cite: 17, 18, 19]
- **Link Video minh chứng:** _(Dán link YouTube của bạn vào đây nếu lớp yêu cầu)_[cite: 1]

---

## 2. PHIÊN BẢN MÔI TRƯỜNG THỰC HÀNH

- **Phần mềm ảo hóa:** VMware Workstation Pro 17 (hoặc 26H1 theo chuẩn phòng máy)[cite: 1, 9]
- **Hệ điều hành máy ảo (Guest OS):** Windows 11 x64 (OS Build 26200.x / 25H2)[cite: 1]
- **Cấu hình mạng:** Host-only[cite: 1]
- **Endpoint Protection:** Microsoft Defender Antivirus (Real-time protection & Tamper protection: Enabled)[cite: 1]
- **Các công cụ phân tích & giám sát:**
  - Windows PowerShell 5.1 (Run as Administrator)[cite: 1]
  - Microsoft Sysmon v15.22 (Schema 4.90)[cite: 1]
  - Microsoft Autoruns v14.3[cite: 1]
  - Microsoft Process Explorer v17.14[cite: 1]
  - Wireshark 4.6.8 + Npcap[cite: 1]
  - Python 3.14.7[cite: 1]

---

## 3. CÁCH DỰNG MÔI TRƯỜNG & BASELINE

1. **Khởi tạo máy ảo:**
   - Tạo máy ảo Windows 11 trên VMware Workstation với cấu hình: 2 vCPU, 4–6 GB RAM, 64 GB Disk[cite: 1].
   - Thiết lập card mạng máy ảo ở chế độ **Host-only**[cite: 1].
   - Tạo snapshot ban đầu có tên: `LAB3_CLEAN_20260914`[cite: 1].
2. **Cấu trúc thư mục làm việc:**
   - Tạo thư mục gốc `C:\LAB3` và các thư mục con: `Evidence`, `Tools`, `Downloads`, `Assets`[cite: 1].
   - Đặt file `LAB3_Threats_Assets.zip` vào `C:\LAB3\Downloads`, đối chiếu mã SHA-256 (`96236f95ce59d0cc37f9b7ab8fbb04522f21870cd0b53aad6e52da5a23655439`) và giải nén[cite: 1].
3. **Cài đặt công cụ:**
   - Tải và giải nén Sysmon, Autoruns, Process Explorer vào `C:\LAB3\Tools`[cite: 1].
   - Cài đặt Python 3.14.7 và Wireshark 4.6.8 có Npcap[cite: 1].
4. **Thu thập Baseline ban đầu:**
   - Chạy script PowerShell lưu thông tin hệ điều hành, Defender (`RealTimeProtectionEnabled = True`), Firewall profiles, Network IP và danh sách tiến trình đang chạy vào `C:\LAB3\Evidence`[cite: 1].

---

## 4. CÁC TÌNH HUỐNG ĐÃ THỰC HIỆN VÀ KẾT QUẢ ĐÁNH GIÁ

| Tình huống (Scenario)                  | Mục tiêu & Kỹ thuật chính                                                                                                                                                                               | Kết quả (PASS/FAIL) |
| :------------------------------------- | :------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ | :-----------------: |
| **TH1: Risk Register & Nguồn đe dọa**  | Xây dựng Risk Register (5 tài sản/rủi ro); Phân loại 5 nhóm nguồn đe dọa: Hành động vô ý, cố ý, thảm họa tự nhiên, lỗi kỹ thuật, lỗi quản lý[cite: 1].                                                  |  **PASS**[cite: 1]  |
| **TH2: Mã độc (EICAR Verification)**   | Tạo chuỗi kiểm thử EICAR an toàn; Microsoft Defender phát hiện theo thời gian thực và cách ly (Protection history / Event log)[cite: 1].                                                                |  **PASS**[cite: 1]  |
| **TH3: Tấn công mật khẩu & Keylogger** | Bật Audit Logon; mô phỏng đăng nhập đúng/sai bằng tài khoản `lab3user`; thu thập Event ID 4624, 4625, 4648; thực hiện xoay vòng mật khẩu (credential rotation)[cite: 1].                                |  **PASS**[cite: 1]  |
| **TH4: Backdoor & Persistence**        | Cài Sysmon schema 4.90; tạo Run key (`LAB3_Run_Demo`) và Scheduled Task (`LAB3_Persistence_Demo`); chạy HTTP listener loopback `127.0.0.1:8080`; kiểm chứng bằng Autoruns và Process Explorer[cite: 1]. |  **PASS**[cite: 1]  |
| **TH5: Sniffing: HTTP vs HTTPS**       | Bắt gói tin trên loopback bằng Wireshark; đọc được payload `TRAINING_ONLY` của HTTP; so sánh với TLS/HTTPS (chỉ xem được metadata, payload được mã hóa an toàn)[cite: 1].                               |  **PASS**[cite: 1]  |
| **TH6: DoS, DDoS & Mail Bombing**      | Chạy `local_load_test.py` kiểm thử tải cục bộ giới hạn; phân tích tập dữ liệu `ddos_sample.csv` (nhiều IP nguồn TEST-NET) và `mailbomb_sample.csv` (volume, tần suất gửi)[cite: 1].                     |  **PASS**[cite: 1]  |
| **TH7: Social Engineering & Phishing** | Phân tích 5 chỉ dấu lừa đảo trong file offline `phishing_email.txt`; phân loại 6 kịch bản tấn công trong `social_engineering_cases.csv`[cite: 1].                                                       |  **PASS**[cite: 1]  |
| **Phục hồi & Dọn dẹp (Cleanup)**       | Xóa Run key, gỡ Scheduled Task, dừng tiến trình port 8080, xóa tài khoản `lab3user`; kiểm tra lại trạng thái sạch; xuất file băm `evidence_sha256.csv`[cite: 1].                                        |  **PASS**[cite: 1]  |

---

## 5. LỖI GẶP PHẢI VÀ CÁCH KHẮC PHỤC

1. **Lỗi khởi động máy ảo: Paging file / Memory allocation:**
   - _Nguyên nhân:_ Cấp phát RAM 6 GB vượt quá khả năng chịu tải hoặc thiếu dung lượng phân vùng ổ đĩa tạm thời của máy thật[cite: 10].
   - _Khắc phục:_ Chọn Discard trạng thái suspended cũ và điều chỉnh RAM máy ảo về mức 4 GB phù hợp[cite: 10].
2. **Lỗi kẹt kết nối mạng lúc cài Windows 11 (OOBE Network):**
   - _Nguyên nhân:_ Mạng VM đang để Host-only nên không có Internet để đăng nhập tài khoản Microsoft online[cite: 1, 16].
   - _Khắc phục:_ Nhấn `Shift + F10` mở CMD, chạy lệnh `oobe\bypassnro` để khởi động lại và chọn cài đặt bằng Local Account (I don't have internet)[cite: 16].
3. **Mục Settings của VMware bị mờ không bấm được:**
   - _Nguyên nhân:_ Chưa nhấp chọn tab máy ảo hoặc máy ảo chưa được thêm vào Library[cite: 2].
   - _Khắc phục:_ Mở đúng tab máy ảo hoặc tắt nguồn máy ảo trước khi chỉnh sửa thiết lập phần cứng[cite: 2].

---

## 6. DANH MỤC TỆP BẰNG CHỨNG (EVIDENCE)

Toàn bộ bằng chứng log, output lệnh và ảnh chụp (H1 - H11) đã được làm sạch thông tin nhạy cảm và kiểm tra toàn vẹn băm SHA-256[cite: 1]:

- File bảng băm: `evidence_sha256.csv`[cite: 1]
- Các file log: `baseline_os.txt`, `baseline_defender.txt`, `baseline_firewall.txt`, `auth_events_before_rotation.txt`, `sysmon_persistence.txt`, `autoruns_diff.txt`, v.v.[cite: 1]
