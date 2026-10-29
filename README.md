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
- **Multilingual**: English, Spanish and Portuguese, switchable from the Hub at runtime.

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

### 🔗 Nexo (Light Mode)
*Focus: Service Marketplace*
- **Accent**: Blue (`#1B4FD8`)
- **Vibe**: Professional, reliable, and modern.

### 🛡️ Pericia (Light Mode)
*Focus: Claims Tracking & Insurance*
- **Accent**: Navy (`#0C2D6B`)
- **Vibe**: Trustworthy, clinical, and efficient.

### 📍 Cerka (Light Mode)
*Focus: Local Services Marketplace*
- **Accent**: Violet (`#311B92`)
- **Vibe**: Premium, active, and accessible.

### 🔥 Chispa (Dark Mode)
*Focus: Dating & Social*
- **Accent**: Rose (`#FF4B81`)
- **Vibe**: Vibrant, premium, and engaging.

### 💬 Vortex (Dark Mode)
*Focus: Instant Messaging*
- **Accent**: Cyan (`#00D4FF`) with Purple (`#7C3AED`)
- **Vibe**: Futuristic, fast, and fluid. Includes a conversation list and a chat screen.

### 🧭 Vigía (Light Mode)
*Focus: Child Safety*
- **Accent**: Blue (`#1565C0`)
- **Vibe**: Calm, reassuring, and protective.

### 🚪 Portón (Light Mode)
*Focus: Neighborhood Access*
- **Accent**: Steel Blue (`#1A5276`)
- **Vibe**: Secure, simple, and one-tap.

### 🚕 Movra (Dark Mode)
*Focus: Ride Hailing*
- **Accent**: Electric Lime (`#C6F432`)
- **Vibe**: Nocturnal, fast, and map-first. A full-screen animated night map under a draggable bottom sheet, with a Rider/Driver switch (ride selection, payment chips, incoming-trip countdown, fare and platform-fee breakdown, weekly earnings).

> Brand names for some showcases are localized (e.g. Chispa is Spark in English and Faísca in Portuguese). The code keeps the original names for packages and routes.

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
- `ui/tastique/`, `ui/medicare/`, `ui/drape/`, `ui/nexus/`, `ui/expert/`, `ui/near/`, `ui/spark/`, `ui/vortex/`, `ui/sentry/`, `ui/portal/`, `ui/movra/`: Individual showcase implementations (self-contained logic & theme).
- `navigation/`: Centralized routing logic.
- `ui/theme/`: Global typography and shared color tokens (one accent per showcase).
- `res/values*/`: Strings in English (default), Spanish (`values-es`) and Portuguese (`values-pt`).

See `CLAUDE.md` for the full screen catalog and the checklist for adding a new showcase.

---

## 📝 License
This project is for educational and showcase purposes.
