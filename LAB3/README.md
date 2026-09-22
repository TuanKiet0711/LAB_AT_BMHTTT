# README - BÁO CÁO THỰC HÀNH LAB 3

## 1. THÔNG TIN SINH VIÊN

- **Họ và tên:** Lê Tuấn Kiệt
- **Mã số sinh viên (MSSV):** 1150080022
- **Lớp:** 11_DH_CNPM1
- **Tên bài Lab:** Lab 3: Nhận diện và ứng phó các mối đe dọa đến an toàn thông tin

---

## 2. PHIÊN BẢN MÔI TRƯỜNG

- **Phần mềm ảo hóa:** VMware Workstation Pro 17
- **Hệ điều hành máy ảo (Guest OS):** Windows 11 Pro 64-bit[cite: 14, 16]
- **Cấu hình mạng:** Host-only[cite: 1]
- **Cấu hình phần cứng VM:** 2 vCPU, 4 GB RAM, 64 GB Disk[cite: 1, 10, 15]
- **Các công cụ & dịch vụ tích hợp:**
  - Microsoft Defender Antivirus (Real-time protection: Enabled)
  - Windows PowerShell 5.1
  - Python 3.14.7
  - Wireshark 4.6.8
  - Bộ công cụ Sysinternals: Sysmon v15.22, Autoruns v14.3, Process Explorer v17.14

---

## 3. CÁCH DỰNG MÔI TRƯỜNG

1. **Khởi tạo máy ảo Windows 11 trên VMware:**
   - Tạo máy ảo mới từ file ISO Windows 11 với thiết lập TPM mã hóa phân vùng hỗ trợ[cite: 9, 16].
   - Gán tài nguyên: 2 vCPU, 4 GB RAM, ổ đĩa 64 GB[cite: 1, 10, 15].
   - Cấu hình Network Adapter sang chế độ **Host-only** để cách ly lưu lượng với mạng bên ngoài[cite: 1].
   - Cài đặt Windows 11 bằng tài khoản cục bộ (Local Account) qua cơ chế bypass mạng[cite: 16].
2. **Khởi tạo mốc hệ thống sạch (Snapshot):**
   - Sau khi vào Desktop và xác nhận card mạng Host-only cùng phiên bản hệ điều hành (`winver`), tiến hành tạo snapshot sạch ban đầu có tên: `LAB3_CLEAN_20260914`[cite: 1].
3. **Cấu trúc thư mục bài lab:**
   - Mở PowerShell với quyền Administrator, khởi tạo cây thư mục làm việc tại `C:\LAB3` gồm các nhánh: `Evidence`, `Tools`, `Downloads`, `Assets`[cite: 1].
   - Sao chép gói tài nguyên `LAB3_Threats_Assets.zip` vào `C:\LAB3\Downloads`, đối chiếu mã băm SHA-256 (`96236f95ce59d0cc37f9b7ab8fbb04522f21870cd0b53aad6e52da5a23655439`) trước khi giải nén[cite: 1].
4. **Cài đặt bộ công cụ:**
   - Triển khai bộ ba công cụ Sysinternals (Sysmon, Autoruns, Process Explorer) vào `C:\LAB3\Tools`[cite: 1].
   - Cài đặt Python 3.14.7 và Wireshark 4.6.8 (kèm Npcap)[cite: 1].

---

## 4. CÁC TÌNH HUỐNG ĐÃ THỰC HIỆN VÀ KẾT QUẢ

| Tình huống (Scenario)                  | Mục tiêu thực hiện & Kỹ thuật chính                                                                                                                                                                                                                       |      Kết quả      |
| :------------------------------------- | :-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | :---------------: |
| **TH1: Risk Register & Nguồn đe dọa**  | Xây dựng ma trận rủi ro (Risk Register) cho 5 tài sản quan trọng; Phân loại 5 nhóm nguồn đe dọa (Hành động vô ý, Cố ý, Thảm họa tự nhiên, Lỗi kỹ thuật, Lỗi quản lý)[cite: 1].                                                                            | **PASS**[cite: 1] |
| **TH2: Mã độc (EICAR Verification)**   | Tạo chuỗi kiểm thử EICAR an toàn; Ghi nhận Microsoft Defender phát hiện thời gian thực, chặn thực thi và cách ly vào Protection history[cite: 1].                                                                                                         | **PASS**[cite: 1] |
| **TH3: Tấn công mật khẩu & Keylogger** | Bật Audit Logon Events; Mô phỏng xác thực đúng/sai với tài khoản `lab3user`; Trích xuất Event ID 4624 (thành công) và 4625 (thất bại); Thực hiện xoay vòng mật khẩu (credential rotation)[cite: 1].                                                       | **PASS**[cite: 1] |
| **TH4: Backdoor & Persistence**        | Triển khai Sysmon giám sát Process Creation (Event ID 1); Cấu hình cơ chế duy trì persistence qua Run Key registry (`LAB3_Run_Demo`) và Scheduled Task; Chạy listener HTTP cổng 8080 trên loopback; Nhận diện bằng Autoruns và Process Explorer[cite: 1]. | **PASS**[cite: 1] |
| **TH5: Sniffing: HTTP vs HTTPS**       | Bắt gói tin trên loopback bằng Wireshark; Phân tích dữ liệu văn bản rõ (plaintext) của HTTP; So sánh với lưu lượng TLS/HTTPS mã hóa an toàn[cite: 1].                                                                                                     | **PASS**[cite: 1] |
| **TH6: DoS, DDoS & Mail Bombing**      | Chạy `local_load_test.py` trên loopback đo lường ảnh hưởng tài nguyên; Phân tích mẫu phân tán trong `ddos_sample.csv` và dữ liệu log thư rác trong `mailbomb_sample.csv`[cite: 1].                                                                        | **PASS**[cite: 1] |
| **TH7: Social Engineering & Phishing** | Nhận diện 5 dấu hiệu nhận biết lừa đảo trong file `phishing_email.txt`; Phân loại và đề xuất biện pháp phòng ngừa cho 6 kịch bản trong `social_engineering_cases.csv`[cite: 1].                                                                           | **PASS**[cite: 1] |
| **Phục hồi & Dọn dẹp (Cleanup)**       | Xóa Run key, Scheduled task, dừng listener, xóa tài khoản lab; Chạy script xác nhận hệ thống trở về baseline sạch; Xuất bảng mã băm `evidence_sha256.csv`[cite: 1].                                                                                       | **PASS**[cite: 1] |

---

## 5. LỖI GẶP PHẢI VÀ CÁCH KHẮC PHỤC

1. **Lỗi cấp phát bộ nhớ ảo (Could not create anonymous paging file for 6144 MB):**
   - _Nguyên nhân:_ Bộ nhớ RAM gán cho máy ảo (6 GB) vượt quá dung lượng paging file/RAM thực tế còn trống của máy vật lý khi resume từ trạng thái suspended[cite: 10].
   - _Cách khắc phục:_ Nhấn **Discard** để hủy phiên lưu tạm bị lỗi, sau đó vào VM Settings hạ mức RAM cấp phát xuống **4096 MB (4 GB)** vừa đủ theo khuyến nghị để máy ảo khởi động ổn định[cite: 10].

2. **Lỗi khởi động mạng EFI (EFI Network... Time out):**
   - _Nguyên nhân:_ Máy ảo không tìm thấy phân vùng khởi động trên ổ cứng và chưa kịp nạp bộ cài từ ổ CD/DVD ảo[cite: 11].
   - _Cách khắc phục:_ Vào Settings của máy ảo kiểm tra mục CD/DVD đã tích chọn _Connected_, sau đó vào menu Boot Manager chọn thủ công dòng `EFI VMware Virtual SATA CDROM Drive` và nhấn phím cách (Spacebar) liên tục để boot vào bộ cài[cite: 11, 12, 13].

3. **Lỗi kẹt màn hình yêu cầu kết nối mạng khi cài đặt Windows 11 (OOBE Network):**
   - _Nguyên nhân:_ Máy ảo đặt mạng Host-only nên không có kết nối Internet ra ngoài, Windows 11 chặn không cho bấm Next để buộc đăng nhập tài khoản Microsoft online[cite: 1, 16].
   - _Cách khắc phục:_ Nhấn tổ hợp phím `Shift + F10` (hoặc `Shift + Fn + F10`) để mở Command Prompt, gõ lệnh `oobe\bypassnro` để máy tự khởi động lại, sau đó chọn dòng **I don't have internet** > **Continue with limited setup** để tạo tài khoản cục bộ bình thường[cite: 16].
