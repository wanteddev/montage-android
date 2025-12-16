# Montage Android Design System

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Android](https://img.shields.io/badge/Platform-Android-green.svg)](https://developer.android.com)
[![Kotlin](https://img.shields.io/badge/Kotlin-2.0-blue.svg)](https://kotlinlang.org)
[![Compose](https://img.shields.io/badge/Jetpack%20Compose-Latest-brightgreen.svg)](https://developer.android.com/jetpack/compose)

[한국어](#한국어) | [English](#english)

---

## English

### Overview

Montage Android is a modern design system library for Android applications, built with Jetpack
Compose.

At Wanted, we dream of a world where every working person can work more authentically. To take one
step closer to that dream, we created this design system.

### Features

- 🎭 **Material Design 3** - Built on top of Material Design 3 guidelines
- 🚀 **Jetpack Compose** - Fully built with Jetpack Compose
- 📱 **Android Native** - Optimized for Android platform

### Requirements

- Android API Level 26 (Android 8.0) or higher
- Kotlin 2.0 or higher
- Jetpack Compose

### Installation

#### Option 1: GitHub Packages (Recommended)

Add the GitHub Packages repository to your `settings.gradle.kts`:

```kotlin
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://maven.pkg.github.com/wanteddev/montage-android")
            credentials {
                username = System.getenv("GITHUB_ACTOR") ?: project.findProperty("gpr.user") as String?
                password = System.getenv("GITHUB_TOKEN") ?: project.findProperty("gpr.token") as String?
            }
        }
    }
}
```

Add the dependency to your app's `build.gradle.kts`:

```kotlin
dependencies {
    implementation("com.wanted.android:montage-android:1.0.0")
}
```

#### Option 2: Git Submodule

Add this repository as a submodule to your project:

```bash
git submodule add https://github.com/wanteddev/montage-android.git
```

Include the module in your `settings.gradle.kts`:

```kotlin
include(":montage-android:library")
```

Add the dependency to your app's `build.gradle.kts`:

```kotlin
dependencies {
    implementation(project(":montage-android:library"))
}
```

### Usage

```kotlin
@Composable
fun MyScreen() {
    // Use Montage components here
}
```

### Documentation

📚 **[Official Documentation](https://montage.wanted.co.kr/)**

For detailed documentation, component examples, and design guidelines, please visit our official
documentation site.

### Development

```bash
# Build the library
./gradlew :library:build

# Run tests
./gradlew :library:test

# Build AAR file
./gradlew buildAar
```

### Third-Party Libraries

This project uses the following open source libraries:

- **[Jetpack Compose](https://developer.android.com/jetpack/compose)** - Modern UI toolkit (Apache
  2.0)
- **[Material Components](https://github.com/material-components/material-components-android)** -
  Material Design components (Apache 2.0)
- **[Lottie](https://github.com/airbnb/lottie-android)** - Animation library by Airbnb (Apache 2.0)
- **[Glide](https://github.com/bumptech/glide)** - Image loading library (BSD, MIT, Apache 2.0)
- **[Accompanist](https://github.com/google/accompanist)** - Compose utilities by Google (Apache
  2.0)

For complete license information, see [THIRD_PARTY_LICENSES.md](THIRD_PARTY_LICENSES.md).

### License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

### Issues & Support

- **Documentation**: [https://montage.wanted.co.kr/](https://montage.wanted.co.kr/)
- **Issues**: [GitHub Issues](https://github.com/wanteddev/montage-android/issues)

> **Note:** This project is currently maintained internally by Wanted. We welcome issue reports and
> feedback, but we are not accepting external contributions at this time.

---

## 한국어

### 개요

Montage Android는 Jetpack Compose로 구축된 최신 Android 디자인 시스템 라이브러리입니다.

원티드가 꿈꿔온 세상은, 모든 일하는 사람이 더 나답게 일할 수 있는 세상입니다. 그 꿈에 한 걸음 더 다가가기 위해, 우리는 디자인 시스템을 만들었습니다.

### 주요 기능

- 🎭 **Material Design 3** - Material Design 3 가이드라인 기반
- 🚀 **Jetpack Compose** - Jetpack Compose로 완전히 구축
- 📱 **Android 네이티브** - Android 플랫폼에 최적화

### 요구사항

- Android API Level 26 (Android 8.0) 이상
- Kotlin 2.0 이상
- Jetpack Compose

### 설치 방법

#### 방법 1: GitHub Packages (권장)

`settings.gradle.kts`에 GitHub Packages 저장소를 추가합니다:

```kotlin
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://maven.pkg.github.com/wanteddev/montage-android")
            credentials {
                username = System.getenv("GITHUB_ACTOR") ?: project.findProperty("gpr.user") as String?
                password = System.getenv("GITHUB_TOKEN") ?: project.findProperty("gpr.token") as String?
            }
        }
    }
}
```

앱의 `build.gradle.kts`에 의존성을 추가합니다:

```kotlin
dependencies {
    implementation("com.wanted.android:montage-android:1.0.0")
}
```

#### 방법 2: Git Submodule

프로젝트에 서브모듈로 추가합니다:

```bash
git submodule add https://github.com/wanteddev/montage-android.git
```

`settings.gradle.kts`에 모듈을 포함합니다:

```kotlin
include(":montage-android:library")
```

앱의 `build.gradle.kts`에 의존성을 추가합니다:

```kotlin
dependencies {
    implementation(project(":montage-android:library"))
}
```

### 사용 방법

```kotlin
@Composable
fun MyScreen() {
    // Montage 컴포넌트 사용
}
```

### 문서

📚 **[공식 문서](https://montage.wanted.co.kr/)**

자세한 문서, 컴포넌트 예제 및 디자인 가이드라인은 공식 문서 사이트를 방문해주세요.

### 개발

```bash
# 라이브러리 빌드
./gradlew :library:build

# 테스트 실행
./gradlew :library:test

# AAR 파일 빌드
./gradlew buildAar
```

### 사용 중인 서드파티 라이브러리

이 프로젝트는 다음 오픈소스 라이브러리를 사용합니다:

- **[Jetpack Compose](https://developer.android.com/jetpack/compose)** - 최신 UI 툴킷 (Apache 2.0)
- **[Material Components](https://github.com/material-components/material-components-android)** -
  Material Design 컴포넌트 (Apache 2.0)
- **[Lottie](https://github.com/airbnb/lottie-android)** - Airbnb의 애니메이션 라이브러리 (Apache 2.0)
- **[Glide](https://github.com/bumptech/glide)** - 이미지 로딩 라이브러리 (BSD, MIT, Apache 2.0)
- **[Accompanist](https://github.com/google/accompanist)** - Google의 Compose 유틸리티 (Apache 2.0)

전체 라이선스 정보는 [THIRD_PARTY_LICENSES.md](THIRD_PARTY_LICENSES.md)를 참고하세요.

### 라이선스

이 프로젝트는 MIT 라이선스 하에 배포됩니다 - 자세한 내용은 [LICENSE](LICENSE) 파일을 참고하세요.

### 지원

- 📚 문서: [https://montage.wanted.co.kr/](https://montage.wanted.co.kr/)
- 📫 이슈: [GitHub Issues](https://github.com/wanteddev/montage-android/issues)
- 💬 토론: [GitHub Discussions](https://github.com/wanteddev/montage-android/discussions)

---

**Made with ❤️ by Wanted**
