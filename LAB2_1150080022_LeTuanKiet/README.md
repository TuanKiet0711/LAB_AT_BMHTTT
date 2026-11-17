# LAB 2 - MÃ HÓA ĐỐI XỨNG HIỆN ĐẠI

- **Họ và tên:** Lê Tuấn Kiệt
- **MSSV:** 1150080022
- **Học phần:** An toàn và Bảo mật Thông tin

## Cấu trúc thư mục

- `LAB2_MaHoaHienDai/`: Mã nguồn dự án Java Maven NetBeans (bao gồm `pom.xml`, các lớp xử lý thuật toán, DAO và các JFrame Form `.java` + `.form` trong package `week_02`).
- `SQL/`: Các tệp SQL khởi tạo CSDL, tạo tài khoản `lab2_user` và kiểm tra dữ liệu (`01_Tao_CSDL.sql`, `02_Tao_Tai_Khoan_SQL.sql`, `03_Kiem_Tra_Du_Lieu.sql`).
- `LAB2_Python/`: Chương trình nâng cao 02 `aes_cbc_gui.py` (AES-256-CBC bằng Python Tkinter).
- `images/`: Chứa hình ảnh kết quả thực hiện các bài thực hành.
- `BaoCao_LAB2_1150080022_LeTuanKiet.docx`: Báo cáo Word tổng hợp bài làm.

## Hướng dẫn cài đặt và chạy chương trình

### 1. Chuẩn bị SQL Server
1. Đảm bảo dịch vụ **SQL Server (SQLEXPRESS)** đang chạy, bật **TCP/IP** cổng `14333` và chế độ **SQL Server and Windows Authentication mode**.
2. Mở SSMS, chạy lần lượt `SQL/01_Tao_CSDL.sql` và `SQL/02_Tao_Tai_Khoan_SQL.sql`.
3. Kiểm tra đăng nhập với tài khoản `lab2_user`, mật khẩu `LabUser@2026`, Server `127.0.0.1,14333`.

### 2. Chạy ứng dụng Java (NetBeans / Maven)
1. Mở NetBeans -> **File -> Open Project...** -> chọn thư mục `LAB2_MaHoaHienDai`.
2. Nhấp phải project -> **Clean and Build** (hoặc chạy `mvn clean package` trong thư mục `LAB2_MaHoaHienDai`).
3. Nhấp phải project -> **Run** (F6) để mở cửa sổ chính `frm_Menu`.
4. Chọn **Kết nối SQL Server**, nhập mật khẩu `LabUser@2026` và bấm **Test Connection** trước khi dùng bài **2.3 AES + Login** và **Xem lịch sử**.

### 3. Chạy chương trình Python (Bài nâng cao 02 - AES-CBC)
1. Cài thư viện (chỉ làm 1 lần):
   ```bash
   python -m pip install cryptography
   ```
2. Chạy kiểm thử tự động:
   ```bash
   cd LAB2_Python
   python aes_cbc_gui.py --self-test
   ```
3. Chạy giao diện:
   ```bash
   python aes_cbc_gui.py
   ```
