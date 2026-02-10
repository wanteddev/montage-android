<div align="center">

# 🎨 Montage Android

**Jetpack Compose로 만든 아름답고 현대적인 Android 디자인 시스템**

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![](https://jitpack.io/v/wanteddev/montage-android.svg)](https://jitpack.io/#wanteddev/montage-android)
[![Android](https://img.shields.io/badge/Platform-Android-green.svg)](https://developer.android.com)
[![Kotlin](https://img.shields.io/badge/Kotlin-2.0-blue.svg)](https://kotlinlang.org)
[![Compose](https://img.shields.io/badge/Jetpack%20Compose-Latest-brightgreen.svg)](https://developer.android.com/jetpack/compose)

[🇰🇷 한국어](#-한국어) | [🇬🇧 English](#-english)

[시작하기](#-빠른-시작) • [문서](https://montage.wanted.co.kr/) • [예제](#-사용-예제) • [기여하기](#-기여하기)

</div>

---

## 🇰🇷 한국어

### ✨ Montage란?

Montage는 아름다운 Android 앱을 더 빠르게 만들 수 있도록 도와주는 **프로덕션 레디 디자인 시스템**입니다. 모든 사람이 더 나답게 일할 수 있는 세상을 꿈꾸는 원티드의 미션에서 탄생한 Montage는 현대적인 디자인 원칙을 따르는 포괄적이고 커스터마이징 가능한 컴포넌트 세트를 제공합니다.

### 🚀 왜 Montage인가요?

- **🎯 프로덕션 레디** - 실제 프로덕션 앱에서 검증된 컴포넌트
- **⚡ Compose 기반** - 순수 Jetpack Compose, XML 없음
- **🎨 커스터마이징 가능** - 브랜드에 맞는 쉬운 테마 및 스타일링
- **📱 모던 디자인** - 최신 UI 패턴과 Material Design 3
- **🔧 개발자 친화적** - 직관적인 Kotlin DSL API
- **📚 완벽한 문서** - [montage.wanted.co.kr](https://montage.wanted.co.kr/)에서 상세한 문서와 예제 제공
- **🌙 다크 모드** - 기본 제공되는 완전한 다크 모드 지원
- **⚡ 가벼움** - 최소한의 의존성, 최대의 성능

### 📦 빠른 시작

#### 1. JitPack 저장소 추가

`settings.gradle.kts`에 추가:

```kotlin
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}
```

#### 2. 의존성 추가

앱의 `build.gradle.kts`에 추가:

```kotlin
dependencies {
    implementation("com.github.wanteddev:montage-android:3.1.0")
}
```

#### 3. 개발 시작!

```kotlin
import androidx.compose.runtime.Composable
import com.wanted.android.designsystem.*

@Composable
fun MyAwesomeScreen() {
    // Montage 컴포넌트로 아름다운 UI 만들기
}
```

이게 전부입니다! 이제 시작할 준비가 되었습니다 🎉

### 💡 사용 예제

시작하기 위한 간단한 예제들:

```kotlin
@Composable
fun ExampleScreen() {
    DesignSystemTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            WantedButton(
                text = "원티드 디자인 시스템",
                variant = ButtonVariant.SOLID,
                type = ButtonType.PRIMARY,
                size = ButtonSize.LARGE,
                onClick = { /* 클릭 이벤트 처리 */ }
            )
        }
    }
}
```

> 📚 **더 많은 예제가 필요하신가요?** [공식 문서](https://montage.wanted.co.kr/)에서 자세한 가이드와 컴포넌트 데모를 확인하세요.

### 🎯 기능

- **풍부한 컴포넌트 라이브러리** - 버튼, 카드, 다이얼로그 등
- **유연한 테마** - 색상, 타이포그래피, 형태 커스터마이징
- **애니메이션** - Lottie 지원으로 부드럽고 매력적인 애니메이션
- **접근성** - 접근성을 고려한 설계
- **반응형** - 휴대폰과 태블릿에서 모두 아름답게 작동
- **타입 안전** - 더 안전한 코드를 위한 Kotlin 타입 시스템 활용

### 📋 요구사항

- **Android API Level 26+** (Android 8.0 Oreo 이상)
- **Kotlin 2.0+**
- **Jetpack Compose**

### 🛠️ 개발

```bash
# 저장소 클론
git clone https://github.com/wanteddev/montage-android.git
cd montage-android

# 라이브러리 빌드
./gradlew :library:build

# 테스트 실행
./gradlew :library:test

# AAR 파일 빌드
./gradlew :library:buildAar

# Maven Local에 배포 (테스트용)
./gradlew :library:publishToMavenLocal
```

### 🤝 기여하기

> **참고:** 이 프로젝트는 현재 원티드에서 내부적으로 관리하고 있습니다. Pull Request는 회사 구성원에 한해 받고 있으며, 외부 기여는 받지 않고 있습니다.

#### 버그를 발견하셨나요?

버그 리포트와 피드백은 언제나 환영합니다! 다음 정보와 함께 [이슈를 열어주세요](https://github.com/wanteddev/montage-android/issues/new):
- 버그 설명
- 재현 단계
- 예상 동작 vs 실제 동작
- 스크린샷 (해당되는 경우)

#### 기능 제안이 있으신가요?

새로운 기능이나 개선 사항에 대한 아이디어가 있다면 [GitHub Discussions](https://github.com/wanteddev/montage-android/discussions)에서 의견을 공유해주세요.

### 📖 문서

- 📚 [공식 문서](https://montage.wanted.co.kr/)
- 🎨 [디자인 가이드라인](https://montage.wanted.co.kr/)
- 💬 [GitHub Discussions](https://github.com/wanteddev/montage-android/discussions)
- 🐛 [이슈 트래커](https://github.com/wanteddev/montage-android/issues)

### 📦 대체 설치 방법

#### 방법 2: Git Submodule

소스를 직접 포함하려면:

```bash
git submodule add https://github.com/wanteddev/montage-android.git
```

`settings.gradle.kts`에 추가:

```kotlin
include(":montage-android:library")
```

앱의 `build.gradle.kts`에 추가:

```kotlin
dependencies {
    implementation(project(":montage-android:library"))
}
```

### 🙏 감사의 말

Montage는 훌륭한 오픈소스 프로젝트들을 기반으로 만들어졌습니다:

- **[Jetpack Compose](https://developer.android.com/jetpack/compose)** - 최신 UI 툴킷
- **[Material Components](https://github.com/material-components/material-components-android)** - Material Design 컴포넌트
- **[Lottie](https://github.com/airbnb/lottie-android)** - Airbnb의 애니메이션 라이브러리
- **[Glide](https://github.com/bumptech/glide)** - 이미지 로딩 라이브러리

전체 라이선스 정보는 [THIRD_PARTY_LICENSES.md](THIRD_PARTY_LICENSES.md)를 참고하세요.

### 📄 라이선스

Montage는 **MIT 라이선스** 하에 배포됩니다. 자세한 내용은 [LICENSE](LICENSE)를 참고하세요.

```
MIT 라이선스 - 상업적 또는 개인 프로젝트에서 자유롭게 사용하실 수 있습니다.
```

### 💬 커뮤니티 & 지원

- 💡 **질문이 있으신가요?** [GitHub Discussions](https://github.com/wanteddev/montage-android/discussions)에서 물어보세요
- 🐛 **버그 리포트:** [GitHub Issues](https://github.com/wanteddev/montage-android/issues)
- 📚 **문서:** [montage.wanted.co.kr](https://montage.wanted.co.kr/)

### ⭐ 응원해주세요

Montage가 도움이 되셨다면 Star를 눌러주세요! 프로젝트가 성장하고 더 많은 개발자들에게 도달하는 데 도움이 됩니다.

[![Star on GitHub](https://img.shields.io/github/stars/wanteddev/montage-android.svg?style=social)](https://github.com/wanteddev/montage-android)

---

<div align="center">

## 🇬🇧 English

</div>

### ✨ What is Montage?

Montage is a **production-ready design system** that helps you build beautiful Android apps faster. Born from Wanted's mission to help people work more authentically, Montage provides a comprehensive set of customizable components that follow modern design principles.

### 🚀 Why Montage?

- **🎯 Production Ready** - Battle-tested components used in production apps
- **⚡ Built with Compose** - Fully native Jetpack Compose, no XML
- **🎨 Customizable** - Easy theming and styling to match your brand
- **📱 Modern Design** - Contemporary UI patterns and Material Design 3
- **🔧 Developer Friendly** - Intuitive API with Kotlin DSL
- **📚 Well Documented** - Comprehensive docs and examples at [montage.wanted.co.kr](https://montage.wanted.co.kr/)
- **🌙 Dark Mode** - Full dark mode support out of the box
- **⚡ Lightweight** - Minimal dependencies, maximum performance

### 📦 Quick Start

#### 1. Add JitPack repository

In your `settings.gradle.kts`:

```kotlin
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}
```

#### 2. Add dependency

In your app's `build.gradle.kts`:

```kotlin
dependencies {
    implementation("com.github.wanteddev:montage-android:3.1.0")
}
```

#### 3. Start building!

```kotlin
import androidx.compose.runtime.Composable
import com.wanted.android.designsystem.*

@Composable
fun MyAwesomeScreen() {
    // Your beautiful UI with Montage components
}
```

That's it! You're ready to go 🎉

### 💡 Usage

Here are some quick examples to get you started:

```kotlin
@Composable
fun ExampleScreen() {
    DesignSystemTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            WantedButton(
                text = "원티드 디자인 시스템",
                variant = ButtonVariant.SOLID,
                type = ButtonType.PRIMARY,
                size = ButtonSize.LARGE,
                onClick = { /* 클릭 이벤트 처리 */ }
            )
        }
    }
}
```

> 📚 **Want more examples?** Check out our [official documentation](https://montage.wanted.co.kr/) for detailed guides and component demos.

### 🎯 Features

- **Rich Component Library** - Buttons, cards, dialogs, and more
- **Flexible Theming** - Customize colors, typography, and shapes
- **Animations** - Smooth, delightful animations with Lottie support
- **Accessibility** - Built with accessibility in mind
- **Responsive** - Works beautifully on phones and tablets
- **Type-Safe** - Leverage Kotlin's type system for safer code

### 📋 Requirements

- **Android API Level 26+** (Android 8.0 Oreo or higher)
- **Kotlin 2.0+**
- **Jetpack Compose**

### 🛠️ Development

```bash
# Clone the repository
git clone https://github.com/wanteddev/montage-android.git
cd montage-android

# Build the library
./gradlew :library:build

# Run tests
./gradlew :library:test

# Build AAR file
./gradlew :library:buildAar

# Publish to Maven Local (for testing)
./gradlew :library:publishToMavenLocal
```

### 🤝 Contributing

> **Note:** This project is currently maintained internally by Wanted. Pull requests are accepted from company members only, and we are not accepting external contributions at this time.

#### Found a bug?

Bug reports and feedback are always welcome! Please [open an issue](https://github.com/wanteddev/montage-android/issues/new) with:
- Description of the bug
- Steps to reproduce
- Expected vs actual behavior
- Screenshots (if applicable)

#### Have a feature suggestion?

If you have ideas for new features or improvements, please share your thoughts in [GitHub Discussions](https://github.com/wanteddev/montage-android/discussions).

### 📖 Documentation

- 📚 [Official Documentation](https://montage.wanted.co.kr/)
- 🎨 [Design Guidelines](https://montage.wanted.co.kr/)
- 💬 [GitHub Discussions](https://github.com/wanteddev/montage-android/discussions)
- 🐛 [Issue Tracker](https://github.com/wanteddev/montage-android/issues)

### 📦 Alternative Installation Methods

#### Option 2: Git Submodule

If you prefer to include the source directly:

```bash
git submodule add https://github.com/wanteddev/montage-android.git
```

In your `settings.gradle.kts`:

```kotlin
include(":montage-android:library")
```

In your app's `build.gradle.kts`:

```kotlin
dependencies {
    implementation(project(":montage-android:library"))
}
```

### 🙏 Acknowledgments

Montage is built on top of amazing open source projects:

- **[Jetpack Compose](https://developer.android.com/jetpack/compose)** - Modern UI toolkit
- **[Material Components](https://github.com/material-components/material-components-android)** - Material Design components
- **[Lottie](https://github.com/airbnb/lottie-android)** - Animation library by Airbnb
- **[Glide](https://github.com/bumptech/glide)** - Image loading library

See [THIRD_PARTY_LICENSES.md](THIRD_PARTY_LICENSES.md) for complete license information.

### 📄 License

Montage is released under the **MIT License**. See [LICENSE](LICENSE) for details.

```
MIT License - you're free to use Montage in your projects, commercial or personal.
```

### 💬 Community & Support

- 💡 **Questions?** Ask in [GitHub Discussions](https://github.com/wanteddev/montage-android/discussions)
- 🐛 **Bug reports:** [GitHub Issues](https://github.com/wanteddev/montage-android/issues)
- 📚 **Documentation:** [montage.wanted.co.kr](https://montage.wanted.co.kr/)

### ⭐ Show Your Support

If you find Montage helpful, please consider giving it a star! It helps the project grow and reach more developers.

[![Star on GitHub](https://img.shields.io/github/stars/wanteddev/montage-android.svg?style=social)](https://github.com/wanteddev/montage-android)

---

<div align="center">

**Made with ❤️ by Wanted**

*Helping people work more authentically*

</div>
