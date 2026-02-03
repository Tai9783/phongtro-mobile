import java.util.Properties
import java.io.FileInputStream
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-parcelize")
    alias(libs.plugins.navigation.safe.args)

}
val localProps = Properties().apply {
    val f = rootProject.file("local.properties")
    if (f.exists()) load(FileInputStream(f))
}

android {
    namespace = "com.example.apptimphongtro"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.apptimphongtro"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true
        buildConfigField(
            "String",
            "TRACK_ASIA_KEY",
            "\"${localProps.getProperty("TRACK_ASIA_KEY", "")}\""
        )
    }

    buildTypes {

        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures{
        viewBinding= true
        buildConfig = true
    }
}

dependencies {
    // Thư viện mạng chính: Retrofit
    implementation(libs.retrofit)

    // Thư viện chuyển đổi JSON (Gson)
    implementation(libs.converter.gson)

    // Coroutines (Quản lý các tác vụ bất đồng bộ)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    // Glide: thư viện tải ảnh từ internet
    implementation(libs.glide)
    //Thư viện bản đồ của TrackAsia
    implementation(libs.track.asia.android.sdk)
    // Thư viện lấy tọa độ và vị trí (cho bước sắp xếp gần tôi nhất)
    implementation(libs.play.services.location)

    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}