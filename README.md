# 🎨 Montage Android

**Jetpack Compose로 구축된 현대적인 Android 디자인 시스템**

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![](https://jitpack.io/v/wanteddev/montage-android.svg)](https://jitpack.io/#wanteddev/montage-android)
[![Android](https://img.shields.io/badge/Platform-Android-green.svg)](https://developer.android.com)
[![Kotlin](https://img.shields.io/badge/Kotlin-2.0-blue.svg)](https://kotlinlang.org)
[![Compose](https://img.shields.io/badge/Jetpack%20Compose-Latest-brightgreen.svg)](https://developer.android.com/jetpack/compose)

---

## ✨ 소개

**Montage Android**는 Wanted에서 개발한 **Jetpack Compose 기반 디자인 시스템 라이브러리**입니다.
일관된 UI/UX와 생산성을 높이기 위해 설계되었으며, 실제 프로덕션 환경에서 사용되는 컴포넌트들을 제공합니다.

> Wanted의 미션 —
> *모든 일하는 사람이 더 나답게 일할 수 있는 세상*
>
> Montage는 이 비전을 기술적으로 구현하기 위한 디자인 시스템입니다.

---

## 🚀 특징

* 🎯 **Production Ready** — 실제 서비스에서 검증된 컴포넌트
* ⚡ **Jetpack Compose 기반** — XML 없이 Compose로만 구현
* 🎨 **유연한 테마 시스템** — 브랜드 맞춤 커스터마이징
* 📱 **모던 UI 패턴** — Material Design 3 기반
* 🧩 **확장 가능한 구조** — 디자인 시스템 확장에 최적화
* 🌙 **다크 모드 지원**
* 🧪 **테스트 가능 구조**

---

## 📋 요구사항

* Android API Level 26+
* Kotlin 2.0+
* Jetpack Compose

---

## 📦 설치

Montage Android는 **JitPack + Git tag 기반**으로 배포됩니다.

### 1️⃣ JitPack 저장소 추가

`settings.gradle.kts`

```kotlin
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}
```

### 2️⃣ 의존성 추가

`build.gradle.kts`

```kotlin
dependencies {
    implementation("com.github.wanteddev:montage-android:latestVersion")
}
```

> 💡 버전은 Git tag 기준입니다.

---

## 🧩 사용법

```kotlin
import androidx.compose.runtime.Composable
import com.wanted.android.designsystem.*

@Composable
fun ExampleScreen() {
    DesignSystemTheme {
        WantedButton(
            text = "Montage Design System",
            onClick = {}
        )
    }
}
```

---

## 🛠️ 개발

```bash
# 저장소 클론
git clone https://github.com/wanteddev/montage-android.git
cd montage-android

# 빌드
./gradlew :library:build

# 테스트
./gradlew :library:test

# AAR 빌드
./gradlew :library:buildAar

# Maven Local 배포 (테스트용)
./gradlew :library:publishToMavenLocal
```

---

## 🏷️ 버전 정책 (Versioning)

Montage는 **Git tag 기반 버전 관리**를 사용합니다.

* `MAJOR.MINOR.PATCH` (Semantic Versioning)
* 예: `3.1.0`

### 예시

| 변경 유형           | 버전 증가 |
| --------------- | ----- |
| Breaking change | MAJOR |
| 기능 추가           | MINOR |
| 버그 수정           | PATCH |

---

## 📚 문서

* 공식 문서: [https://montage.wanted.co.kr/](https://montage.wanted.co.kr/)

---

## 🤝 기여 정책

이 프로젝트는 Wanted에서 관리하는 오픈소스입니다.

* 외부 Pull Request는 현재 받지 않습니다.
* 버그 리포트 및 개선 제안은 GitHub Issues를 통해 가능합니다.

---

## 📄 라이선스

이 프로젝트는 **MIT License** 하에 배포됩니다.

자세한 내용은 [LICENSE](LICENSE)를 참고하세요.

---

## 🙏 오픈소스 사용

Montage는 다음 오픈소스를 기반으로 합니다:

* Jetpack Compose (Apache 2.0)
* Material Components (Apache 2.0)
* Lottie (Apache 2.0)
* Glide (BSD / MIT / Apache 2.0)

자세한 내용은 [THIRD_PARTY_LICENSES.md](THIRD_PARTY_LICENSES.md)를 참고하세요.

---

**Made with ❤️ by Wanted**
