# 📱 UI Showcase

A polished collection of distinct UI design systems implemented in Jetpack Compose. This project demonstrates high-fidelity mobile interfaces, each with its own self-contained theme, color palette, and component style.

![Kotlin](https://img.shields.io/badge/kotlin-v2.2.10-blue.svg?logo=kotlin)
![Compose](https://img.shields.io/badge/Jetpack_Compose-2026.02.01-green.svg?logo=jetpackcompose)
![Target SDK](https://img.shields.io/badge/Target_SDK-35-orange.svg)

---

## ✨ Features

- **Multi-Theme Architecture**: Every showcase is a mini-app with its own private `MaterialTheme` override.
- **Edge-to-Edge Experience**: Modern immersive UI implementation using `enableEdgeToEdge()`.
- **Performance Optimized**: Built with standard Material 3 icons and efficient layout strategies (no `material-icons-extended` bloat).
- **Navigation Driven**: Centralized hub connecting disparate design paradigms.

---

## 🎨 The Showcases

### 🥧 Tastique (Dark Mode)
*Focus: Gastronomy & Bakeries*
- **Accent**: Amber (`#FFB300`)
- **Vibe**: Warm, sophisticated, and appetizing.

### 🏥 MediCare (Light Mode)
*Focus: Healthcare & Wellness*
- **Accent**: Teal (`#26A69A`)
- **Vibe**: Clean, trustworthy, and clinical.

### 👗 Drape (Light Mode)
*Focus: High-Fashion E-commerce*
- **Accent**: Coral (`#FF3D2E`)
- **Vibe**: Bold, minimalist, and editorial.

---

## 🛠 Tech Stack

- **UI Framework**: [Jetpack Compose](https://developer.android.com/jetpack/compose) (Material 3)
- **Navigation**: [Compose Navigation](https://developer.android.com/jetpack/compose/navigation)
- **Image Loading**: [Coil](https://coil-kt.github.io/coil/)
- **Build System**: Kotlin DSL (build.gradle.kts)
- **Minimum SDK**: 24 (Android 7.0)
- **Target SDK**: 35 (Android 15)

---

## 🚀 Getting Started

### Prerequisites
- Android Studio Ladybug (or newer)
- JDK 17+

### Build & Run
```bash
# Clone the repository
git clone https://github.com/your-username/UIShowcase.git

# Build the project
./gradlew assembleDebug

# Install on a device
./gradlew installDebug
```

---

## 🏗 Project Structure

- `ui/hub/`: The gateway screen for all showcases.
- `ui/tastique/`, `ui/medicare/`, `ui/drape/`: Individual showcase implementations (self-contained logic & theme).
- `navigation/`: Centralized routing logic.
- `theme/`: Global typography and shared color tokens.

---

## 📝 License
This project is for educational and showcase purposes.
