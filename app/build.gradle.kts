plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.gms.google.services)
}


android {
    namespace = "com.andrea.belotti.brorkout"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.andrea.belotti.brorkout"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "/META-INF/gradle/incremental.annotation.processors"

        }
    }
    
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.jackson.core)
    implementation(libs.gson)
    implementation(libs.java.utils)
    implementation(libs.sdp)
    implementation(libs.circled.image)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)

    implementation(libs.activity)

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // # FIREBASE #
    implementation(libs.firebase.database)
    implementation(libs.firebase.auth)

    // # LOMBOK #
    implementation(libs.lombok)
    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)

}


