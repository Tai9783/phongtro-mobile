# App Tìm Phòng Trọ

Ứng dụng Android hỗ trợ tìm kiếm và đăng tin cho thuê phòng trọ. Dự án được thực hiện với mục đích học tập và xây dựng một quy trình tìm phòng trọ đơn giản trên thiết bị di động.

## Giới thiệu

Ứng dụng cho phép người dùng xem danh sách phòng trọ, tìm kiếm theo nhiều tiêu chí và xem thông tin phòng. Người dùng có quyền chủ trọ có thể tạo phòng, thêm hình ảnh, xác nhận vị trí trên bản đồ và đăng tin cho thuê.

## Chức năng chính

- Xem danh sách phòng trọ nổi bật.
- Lọc nhanh phòng theo thành phố và khoảng giá.
- Tìm kiếm nâng cao theo:
  - Tỉnh/thành phố.
  - Phường/xã.
  - Khoảng giá.
  - Tiện ích phòng trọ.
- Sắp xếp kết quả theo giá tăng dần, giá giảm dần và tin mới nhất.
- Đăng nhập và tự động tải lại thông tin người dùng.
- Đăng tin cho thuê qua quy trình 3 bước:
  1. Nhập thông tin phòng và tiện ích.
  2. Nhập địa chỉ và xác nhận vị trí trên bản đồ.
  3. Chọn hình ảnh, tải lên Cloudinary và đăng tin.
- Xem thông tin cá nhân.
- Xem các tin đăng của chủ trọ.

## Công nghệ sử dụng

- Kotlin
- Android SDK 34
- Minimum SDK 24
- Java 11
- XML Layout và ViewBinding
- MVVM và Repository Pattern
- Kotlin Coroutines
- LiveData và ViewModel
- Retrofit 2 và Gson
- Jetpack Navigation
- RecyclerView và DiffUtil
- Glide
- Material Components
- TrackAsia Android SDK
- Cloudinary
- SharedPreferences

## Kiến trúc thư mục

```text
app/src/main/java/com/example/apptimphongtro/
├── adapter/
│   └── Các RecyclerView Adapter và DiffUtil Callback
├── common/
│   └── Các trạng thái giao diện dùng chung
├── data/
│   ├── api/
│   │   └── Retrofit Service
│   ├── local/
│   │   └── SharedPreferences
│   └── repository/
│       └── Repository xử lý dữ liệu
├── model/
│   ├── dto/
│   │   └── Request và Response Model
│   └── entity/
│       └── Các đối tượng dữ liệu của ứng dụng
├── ui/
│   └── Fragment, Dialog và Bottom Sheet
├── util/
│   └── Các hàm tiện ích và khởi tạo ViewModel
└── viewmodel/
    └── ViewModel và ViewModel Factory
```

## Yêu cầu môi trường

- Android Studio Hedgehog hoặc phiên bản mới hơn.
- JDK 11.
- Android SDK 34.
- Một Android Emulator hoặc thiết bị Android có API 24 trở lên.
- Backend của ứng dụng đang chạy tại địa chỉ mà `RetrofitClient` cấu hình.

Mặc định, ứng dụng sử dụng địa chỉ backend:

```text
http://10.0.2.2:8080/
```

`10.0.2.2` dùng cho trường hợp chạy backend trên máy tính và ứng dụng trên Android Emulator. Nếu chạy trên thiết bị thật, cần thay bằng địa chỉ IP của máy tính trong cùng mạng Wi-Fi.

## Cấu hình API key

Tạo hoặc cập nhật file `local.properties` ở thư mục gốc của dự án:

```properties
sdk.dir=C:\\Users\\<username>\\AppData\\Local\\Android\\Sdk
TRACK_ASIA_KEY=your_track_asia_key
```

API key TrackAsia được đưa vào `BuildConfig` khi build ứng dụng và được sử dụng cho bản đồ và chức năng lấy địa chỉ.

Không đưa file `local.properties` hoặc API key lên repository công khai.

## Cài đặt và chạy dự án

1. Mở thư mục dự án bằng Android Studio.
2. Chờ Android Studio hoàn tất Gradle Sync.
3. Kiểm tra `local.properties` đã có `TRACK_ASIA_KEY`.
4. Khởi động backend tại cổng `8080`.
5. Chọn emulator hoặc thiết bị Android.
6. Nhấn **Run** để chạy ứng dụng.

Có thể build bằng Gradle trên Windows:

```bash
gradlew.bat :app:assembleDebug
```

Hoặc trên macOS/Linux:

```bash
./gradlew :app:assembleDebug
```

## Luồng đăng tin

```text
AddPostFragment
        ↓
ImplementAddPostFragment
        ↓
Step1InforFragment
        ↓
Step2AddressFragment
        ↓
ConfirmMapFragment
        ↓
Step3ImageFragment
        ↓
Cloudinary
        ↓
Lưu phòng và đăng tin
```

## Một số lưu ý

- Ứng dụng cần kết nối Internet để lấy dữ liệu phòng, tải hình ảnh và sử dụng bản đồ.
- Chức năng đăng tin yêu cầu tài khoản có vai trò `landlord`.
- Khi sử dụng Android Emulator, backend chạy trên máy tính phải lắng nghe đúng cổng `8080`.
- Hình ảnh được chọn từ thiết bị và tải lên Cloudinary trước khi thông tin phòng được gửi lên backend.
- Dữ liệu đăng nhập hiện được lưu bằng `SharedPreferences` dưới dạng `userId`.

