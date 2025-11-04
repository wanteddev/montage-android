# Montage Android Design System Library

montage-android 디자인 시스템 라이브러리

## 소개

원티드 앱에서 사용하는 공통 디자인 컴포넌트와 스타일을 제공합니다.

## 설치

### Gradle (Submodule)

메인 프로젝트의 `settings.gradle.kts`:

```kotlin
include(":core:montage-android:library")
```

`build.gradle.kts`:

```kotlin
dependencies {
    implementation(project(":core:montage-android:library"))
}
```

## 개발

```bash
# 빌드
./gradlew :library:build

# 테스트
./gradlew :library:test
```

## 문서

자세한 문서는 [document](./library/document/) 디렉토리를 참고하세요.
