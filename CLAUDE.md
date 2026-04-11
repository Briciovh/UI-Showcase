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
./gradlew test --tests "com.example.uishowcase.ExampleUnitTest"

# Run instrumented (on-device) tests
./gradlew connectedAndroidTest

# Lint
./gradlew lint
```

## Architecture & Structure

Android app using **Jetpack Compose** + Material3. Single module (`:app`).

- `MainActivity.kt` — entry point; `enableEdgeToEdge()` + `UIShowcaseTheme { AppNavigation() }`
- `navigation/AppNavigation.kt` — `NavHost` with 4 routes (hub, tastique, medicare, drape)
- `ui/theme/` — `Theme.kt` (root `UIShowcaseTheme`), `Color.kt` (shared tokens + 3 accent colors), `Type.kt`
- `ui/hub/` — main launcher screen
- `ui/tastique/`, `ui/medicare/`, `ui/drape/` — each showcase screen is **fully self-contained**: its own private color palette, private `MaterialTheme` override, fake data, and all composables live in one file

**Target SDK:** 36, **min SDK:** 24, **Language:** Kotlin, **UI:** Compose only (no XML layouts).

### Theme architecture
Each showcase screen defines its own private `darkColorScheme`/`lightColorScheme` and a private `XxxTheme` composable that wraps its `MaterialTheme`. The root `UIShowcaseTheme` is only used for the Hub screen (inherited via `MainActivity`).

---

## Showcase App Plan

This project is a Fiverr portfolio showcase — 3 industry UI screens + a hub launcher. No animations, no real interactivity, hardcoded fake data throughout.

### PR Plan

| PR | Branch | Files | Description |
|---|---|---|---|
| PR 1 | `feat/navigation-setup` | `libs.versions.toml`, `app/build.gradle.kts`, `Color.kt`, `AppNavigation.kt`, `MainActivity.kt` | Navigation Compose dep + 3 accent tokens + NavHost + MainActivity update |
| PR 2 | `feat/hub-screen` | `ui/hub/HubScreen.kt` | Main launcher with 3 tinted showcase cards |
| PR 3 | `feat/tastique-screen` | `ui/tastique/TastiqueScreen.kt` | Dark luxury food delivery screen |
| PR 4 | `feat/medicare-screen` | `ui/medicare/MediCareScreen.kt` | Clean light healthcare screen |
| PR 5 | `feat/drape-screen` | `ui/drape/DrapeScreen.kt` | Bold fashion retail screen |

### File structure target

```
com/example/uishowcase/
├── MainActivity.kt
├── navigation/AppNavigation.kt
├── ui/theme/{Color,Theme,Type}.kt
├── ui/hub/HubScreen.kt
├── ui/tastique/TastiqueScreen.kt
├── ui/medicare/MediCareScreen.kt
└── ui/drape/DrapeScreen.kt
```

### PR 1: Navigation Setup

**`gradle/libs.versions.toml`** — add:
```
navigationCompose = "2.8.9"
androidx-navigation-compose = { group = "androidx.navigation", name = "navigation-compose", version.ref = "navigationCompose" }
```
**`app/build.gradle.kts`** — add: `implementation(libs.androidx.navigation.compose)`

**`Color.kt`** — append:
```kotlin
val TastiqueAmber = Color(0xFFFFB300)
val MediCareTeal  = Color(0xFF26A69A)
val DrapeCoral    = Color(0xFFFF3D2E)
```

**`AppNavigation.kt`**:
```kotlin
object AppRoutes { const val HUB="hub"; const val TASTIQUE="tastique"; const val MEDICARE="medicare"; const val DRAPE="drape" }

@Composable fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = AppRoutes.HUB) {
        composable(AppRoutes.HUB)      { HubScreen(navController) }
        composable(AppRoutes.TASTIQUE) { TastiqueScreen(navController) }
        composable(AppRoutes.MEDICARE) { MediCareScreen(navController) }
        composable(AppRoutes.DRAPE)    { DrapeScreen(navController) }
    }
}
```

**`MainActivity.kt`** — replace body (remove `Greeting` + `@Preview`):
```kotlin
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent { UIShowcaseTheme { AppNavigation() } }
}
```

### PR 2: Hub Screen

`Scaffold` (no topBar) → `Column(spacedBy=16.dp, padding=24.dp)`:
- Header: title "UI Showcase" + subtitle "Select a demo"
- 3× private `ShowcaseCard(title, industry, accentColor, route)`:
  - `Card(height=120.dp, containerColor=accentColor.copy(0.18f))`
  - Row: 56.dp `CircleShape` icon box (accent 0.3f) + Column(title+label)
  - Icons: `ShoppingCart` / `Favorite` / `Star` (all `Icons.Default`)
  - `onClick = { navController.navigate(route) }`

### PR 3: Tastique — Dark Luxury Food Delivery

**Palette** (private vals): `Bg=0xFF0D0D0D`, `Card=0xFF222222`, `AmberL=0xFFFFB300`, `OnBg=0xFFF5F5F5`, `Subtext=0xFF9E9E9E`

**Local theme**: `darkColorScheme(primary=AmberL, background=Bg, ...)` wrapped in private `TastiqueTheme`.

**Fake data**: 7 category strings + 5 `MenuItem(name, price, rating, placeholderColor)`.

**Layout** (`LazyColumn(contentPadding=innerPadding)`):

| Section | Details |
|---|---|
| `TopAppBar` | dark bg, ArrowBack, `LocationOn`+"Mayfair District", `ShoppingCart` action |
| Hero card | `Box(220.dp)` with `Brush.linearGradient` bg, bottom gradient text overlay (dish name), amber badge top-right |
| Category row | `LazyRow` of `FilterChip` — amber selected |
| Menu items | `items()` of `MenuItemCard`: 80×80dp colored `Box` placeholder, name, amber star+price |
| FAB | `BadgedBox(Badge("3")) { FloatingActionButton(amber) { ShoppingCart } }` |

### PR 4: MediCare — Clean Light Healthcare

**Palette** (private vals): `Bg=0xFFF8FFFE`, `TealPrimary=0xFF26A69A`, `TealLight=0xFFB2DFDB`, `OnBg=0xFF1A2C2B`, `Subtext=0xFF607D8B`

**Local theme**: `lightColorScheme(primary=TealPrimary, ...)` wrapped in private `MediCareTheme`.

**Fake data**: 6 specialties + 4 `Doctor(name, specialty, rating, experience, avatarColor)` + 5 date slots `Triple(day,date,isSelected)` + 5 time strings.

**Layout** (`LazyColumn(contentPadding=innerPadding)`):

| Section | Details |
|---|---|
| `TopAppBar` | light bg, ArrowBack, greeting Column ("Good morning,"+"Alex"), `Notifications` teal |
| Search | `OutlinedTextField(readOnly=true)`, 28.dp rounded, teal border |
| Specialties | `LazyRow` of `FilterChip` |
| Doctors | `LazyRow` of `Card(160.dp wide)`: 72.dp `CircleShape` avatar+initials, name, specialty, rating, experience |
| Date selector | `LazyRow` of `Card(56.dp)`: selected=teal bg+white, unselected=white+teal `BorderStroke` |
| Time slots | `LazyRow` of `FilterChip` (one selected) |
| Summary card | `Card(teal bg)`: white text rows + `Button(white bg, teal text)` "Confirm Booking" |

### PR 5: Drape — Bold Fashion Retail

**Palette** (private vals): `Bg=0xFFFAF8F5`, `CoralPrimary=0xFFFF3D2E`, `CoralLight=0xFFFFCDD2`, `OnBg=0xFF1C1C1E`, `Subtext=0xFF8E8E93`

**Local theme**: `lightColorScheme(primary=CoralPrimary, ...)` wrapped in private `DrapeTheme`.

**Fake data**: 6 filter strings + 6 `Product(name, price, rating, placeholderColor, isFavorite)`.

**Layout** (`LazyColumn(contentPadding=innerPadding)`):

| Section | Details |
|---|---|
| `TopAppBar` | off-white bg, ArrowBack, italic bold "drape" title, `Search`+`BadgedBox`(cart,"2") |
| Search | `OutlinedTextField(readOnly=true)`, coral border |
| Filters | `LazyRow` of `FilterChip` — selected = black bg+white text |
| Product grid | `Column { products.chunked(2).forEach { row -> Row(spacedBy=12.dp) { ... weight(1f) } } }` (avoids nested lazy) |
| `ProductCard` | `Box(180.dp)` placeholder+`Favorite`/`FavoriteBorder` at TopEnd; name, coral price, star rating below |

---

## Key Technical Notes

- **Back nav**: `navController.popBackStack()` in each showcase screen's ArrowBack `onClick`
- **Back icon**: `Icons.AutoMirrored.Filled.ArrowBack` (`import androidx.compose.material.icons.automirrored.filled.ArrowBack`); fall back to `Icons.Default.ArrowBack`
- **Chip state**: `var selectedIndex by remember { mutableStateOf(0) }` — needs `getValue`/`setValue` imports
- **`LazyColumn` padding**: `contentPadding = innerPadding` (not `Modifier.padding`) so bg extends under status bar
- **No `material-icons-extended`**: only `Icons.Default.*` from core Material3 bundle to avoid APK bloat
- **`BorderStroke`**: `import androidx.compose.foundation.BorderStroke`
- **`Brush` gradients**: `import androidx.compose.ui.graphics.Brush`
