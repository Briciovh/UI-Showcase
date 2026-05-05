# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build & Run Commands

```bash
# Build debug APK
./gradlew assembleDebug

# Build release APK
./gradlew assembleRelease

# Install on connected device/emulator
./gradlew installDebug

# Run unit tests
./gradlew test

# Run a single unit test class
./gradlew test --tests "com.softeen.uishowcase.ExampleUnitTest"

# Run instrumented (on-device) tests
./gradlew connectedAndroidTest

# Lint
./gradlew lint
```

## Architecture & Structure

Android app using **Jetpack Compose** + Material3. Single module (`:app`). Package: `com.softeen.uishowcase`. **Target SDK:** 35, **min SDK:** 24.

- `MainActivity.kt` — entry point; `enableEdgeToEdge()` + `UIShowcaseTheme { AppNavigation() }`
- `navigation/AppNavigation.kt` — `NavHost` with 4 routes (hub, tastique, medicare, drape) via `AppRoutes` object
- `ui/theme/` — `Theme.kt` (root `UIShowcaseTheme`), `Color.kt` (3 shared accent tokens), `Type.kt`
- `ui/hub/` — main launcher screen with 3 cards navigating to each showcase
- `ui/tastique/`, `ui/medicare/`, `ui/drape/` — each showcase screen is **fully self-contained**: its own private color palette, private `MaterialTheme` override, hardcoded fake data, and all composables in one file

### Theme architecture

Each showcase screen defines its own private `darkColorScheme`/`lightColorScheme` and a private `XxxTheme` composable wrapping its `MaterialTheme`. The root `UIShowcaseTheme` is used only for the Hub screen.

| Screen | Theme type | Accent |
|---|---|---|
| Tastique | Dark | Amber `0xFFFFB300` |
| MediCare | Light | Teal `0xFF26A69A` |
| Drape | Light | Coral `0xFFFF3D2E` |
| Nexo  | Light | Blue `0xFF1B4FD8` + Orange accent `0xFFFF6B2C` |

### Image loading

Coil (`AsyncImage`) is used for image loading. Version pinned in `gradle/libs.versions.toml`.

## Key Technical Notes

- **Back nav**: `navController.popBackStack()` in each showcase screen's ArrowBack `onClick`
- **Back icon**: `Icons.AutoMirrored.Filled.ArrowBack` (`import androidx.compose.material.icons.automirrored.filled.ArrowBack`)
- **Chip state**: `var selectedIndex by remember { mutableStateOf(0) }` — needs `getValue`/`setValue` imports
- **`LazyColumn` padding**: use `contentPadding = innerPadding` (not `Modifier.padding`) so background extends under the status bar
- **No `material-icons-extended`**: only `Icons.Default.*` from core Material3 bundle to avoid APK bloat
- **Product grid in Drape**: uses `Column { products.chunked(2).forEach { row -> Row { ... weight(1f) } } }` to avoid nested lazy layouts
- **`BorderStroke`**: `import androidx.compose.foundation.BorderStroke`
- **`Brush` gradients**: `import androidx.compose.ui.graphics.Brush`
