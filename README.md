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
- Hilt (Dependency Injection)
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

Dự án tổ chức theo **MVVM + package-by-feature**: mỗi tính năng có thư mục riêng chứa đủ tầng UI/ViewModel/Data của chính nó, thay vì gom chung theo loại code như trước. Phần dùng chung từ 2 tính năng trở lên được tách ra `core/`.

```text
app/src/main/java/com/example/apptimphongtro/
├── feature/
│   ├── home/ui/                 Danh sách phòng nổi bật (Fragment, Adapter)
│   ├── search/{ui,viewmodel,data}/    Tìm kiếm, lọc theo tỉnh/phường/giá, sắp xếp
│   ├── addpost/{ui,viewmodel,data}/   Quy trình đăng tin, tải ảnh Cloudinary, RoomPost
│   ├── mypost/{ui,viewmodel,data}/    Danh sách tin đăng của chủ trọ
│   ├── profile/ui/              Đăng nhập và trang cá nhân
│   └── favorite/ui/             Màn hình yêu thích
├── core/
│   ├── di/
│   │   └── NetworkModule — khai báo Hilt Module cung cấp các ApiService dùng chung
│   ├── model/                   Entity/DTO dùng chung nhiều feature (User, RentalRoom, Ward, CityRoomCount, Amenity...)
│   ├── room/{viewmodel,data}/   Logic phòng trọ dùng chung giữa Home và AddPost
│   └── user/{viewmodel,data}/   Logic tài khoản/phiên đăng nhập dùng chung toàn app
├── common/
│   └── Sealed class trạng thái giao diện dùng chung (RoomUIState, RoomPostUiState)
├── data/
│   ├── api/
│   │   └── RetrofitClient — điểm khởi tạo Retrofit dùng chung cho mọi feature
│   └── local/
│       └── SharedPreferences
└── util/
    └── Các hàm tiện ích dùng chung (FormatMoney...)
```

Bên trong mỗi `feature/<tên>/` theo đúng 3 lớp MVVM: `ui/` (Fragment, Adapter, DiffUtil Callback), `viewmodel/` (ViewModel), `data/` (Repository, ApiService, DTO). Feature nào chỉ có UI thuần, không có state/data riêng (`home`, `profile`, `favorite`) thì dùng lại ViewModel/Repository từ `core/`.

Dependency injection dùng **Hilt**: Repository khai báo `@Inject constructor`, ViewModel dùng `@HiltViewModel`, Fragment/Activity cần `@AndroidEntryPoint` để nhận ViewModel qua `by viewModels()`/`by activityViewModels()`. Các `ApiService` (interface, không tự khởi tạo được bằng `@Inject`) được cung cấp qua `core/di/NetworkModule`, ủy quyền lại cho `RetrofitClient` sẵn có.

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

