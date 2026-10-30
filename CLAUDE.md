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

Android app using **Jetpack Compose** + Material3. Single module (`:app`). Package: `com.softeen.uishowcase`. **Target SDK:** 35, **min SDK:** 24. Dependencies live in `gradle/libs.versions.toml` (Navigation Compose, Coil, AppCompat for per-app locales).

- `MainActivity.kt` — entry point; `enableEdgeToEdge()` + `UIShowcaseTheme { AppNavigation() }`
- `navigation/AppNavigation.kt` — `NavHost` starting at `AppRoutes.HUB`; one `composable(...)` per route (13 routes for 11 screens: Vortex has a list and a chat route)
- `ui/theme/` — `Theme.kt` (root `UIShowcaseTheme`, used only by the Hub), `Color.kt` (one accent token per showcase), `Type.kt`
- `ui/hub/HubScreen.kt` — launcher: a `cards` list (title, industry, accent, icon, route) rendered as `ShowcaseCard`s, plus a `LanguageSelector` (EN/ES/PT) using `AppCompatDelegate.setApplicationLocales`
- `ui/<screen>/` — each showcase is **fully self-contained**: its own private color palette, private `MaterialTheme` wrapper, hardcoded fake data, and all composables in one file
- `res/values/`, `res/values-es/`, `res/values-pt/` — `strings.xml` per locale (English is the default)

### Screen catalog

The **package / file / route / string-key names are legacy** (they predate the rename to brand names), while the **brand name shown to the user is localized** through `<key>_title`. Always look a screen up by package or route, not by brand name.

| Package | Route(s) | Entry composable | Brand EN / ES / PT | Industry | Theme | Accent (`Color.kt`) |
|---|---|---|---|---|---|---|
| `ui/tastique/` | `tastique` | `TastiqueScreen` | Tastique | Food delivery | Dark | `TastiqueAmber` `0xFFFFB300` |
| `ui/medicare/` | `medicare` | `MediCareScreen` | MediCare | Healthcare | Light | `MediCareTeal` `0xFF26A69A` |
| `ui/drape/` | `drape` | `DrapeScreen` | drape¹ | Fashion retail | Light | `DrapeCoral` `0xFFFF3D2E` |
| `ui/nexus/` | `nexus` | `NexusScreen` | Nexus / Nexo / Nexo | Service marketplace | Light | `NexoBlue` `0xFF1B4FD8` (+ orange `0xFFFF6B2C`) |
| `ui/expert/` | `expert` | `ExpertScreen` | Expert / Pericia / Perícia | Claims tracking | Light | `PericiaNavy` `0xFF0C2D6B` |
| `ui/near/` | `near` | `NearScreen` | Near / Cerka / Cerka | Service marketplace (Canvas map) | Light | `CerkaViolet` `0xFF311B92` (+ green `0xFF00C853`) |
| `ui/spark/` | `spark` | `SparkScreen` | Spark / Chispa / Faísca | Dating | Dark | `ChispaRose` `0xFFFF4B81` (+ orange `0xFFFF8C42`) |
| `ui/vortex/` | `vortex_list`, `vortex_chat` | `VortexListScreen`, `VortexChatScreen` | Vortex / Vórtex / Vórtex | Instant messaging | Dark | `VortexCyan` `0xFF00D4FF` (+ purple `0xFF7C3AED`) |
| `ui/sentry/` | `sentry` | `SentryScreen` | Sentry / Vigía / Sentinela | Child safety | Light | `VigiaBlue` `0xFF1565C0` |
| `ui/portal/` | `portal` | `PortalHomeScreen` | Portal / Portón / Portão | Neighborhood access | Light | `PortonBlue` `0xFF1A5276` |
| `ui/movra/` | `movra` | `MovraScreen` | Movra | Ride hailing (Canvas night map, rider/driver modes) | Dark | `MovraAccent` `0xFFA6D80F` (in-screen lime `0xFFC6F432`) |

¹ The `drape_title` string is currently lowercase `drape` in all locales.

Private theme wrappers inside screens are named inconsistently (`TastiqueTheme`, `MediCareTheme`, `DrapeTheme`, `NexoTheme`, `PericiaTema`, `CerkaTema`, `ChispaTema`, `VortexTema`, `VigiaTema`, `PortonTema`, `MovraTheme`); new screens use `XxxTheme`.

### Theme architecture

Each showcase screen defines its own private `darkColorScheme`/`lightColorScheme` and a private theme composable wrapping its `MaterialTheme`. The root `UIShowcaseTheme` is used only for the Hub screen.

### Internationalization

Three active locales: English (`values/`), Spanish (`values-es/`), Portuguese (`values-pt/`). The Hub's language buttons call `AppCompatDelegate.setApplicationLocales`. Screen chrome uses `stringResource`; fake data stays hardcoded (see guideline 2 below). Hub-level keys: `hub_title`, `hub_subtitle`, `industry_*`, `<screen>_title`; shared keys use the `common_` prefix.

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
- **`Modifier.blur()`**: only effective on API 31+; guard with `Build.VERSION.SDK_INT >= 31` (min SDK is 24)
- **Simulated maps/charts**: draw with Compose `Canvas`; no Google Maps or other external SDKs
- **Tool Usage**: Always use `replace_file_content` or `multi_replace_file_content` for surgical edits. NEVER use `run_shell_command` with `sed` or `awk` to modify files.

## Adding a New Screen — Checklist

Every new showcase touches the same files. Do all of them:

| File | Change |
|---|---|
| `ui/<brand>/<Brand>Screen.kt` | **Create** — self-contained screen (private theme, palette, fake data, composables) |
| `navigation/AppNavigation.kt` | Add `const val BRAND = "brand"` to `AppRoutes` and a `composable(AppRoutes.BRAND) { BrandScreen(navController) }` entry |
| `ui/hub/HubScreen.kt` | Add a `Triple(...)` to the `cards` list (title, industry string, accent to (icon to route)) and import the accent color |
| `ui/theme/Color.kt` | Add `val BrandAccent = Color(0xFF…)` |
| `res/values/strings.xml`, `values-es/`, `values-pt/` | Add `brand_title`, a new `industry_*` key if none fits, and every `brand_*` key used by the screen — in all three locales |
| `CLAUDE.md`, `README.md` | Add the screen to the catalog / showcase list |

Open a single PR per screen. No detail screens or extra navigation unless the brief asks for it (Vortex is the only multi-route showcase).

## New Screen Guidelines

Follow these rules when adding a new showcase screen to the project.

### 1 — Language-agnostic brand names

Choose a name that works in any locale — a made-up brand word that doesn't rely on any specific language for its meaning. Avoid translating or adapting a concept into English, Spanish, or Portuguese and using that translation as the name.

**Good:** Vortex, Sentry, Spark, Tastique, MediCare, Drape  
**Avoid:** ~~Expert/Pericia~~, ~~Near/Cerka~~, ~~Nexus/Nexo~~, ~~Spark/Chispa~~, ~~Sentry/Vigía~~, ~~Portal/Portón~~ (legacy screens whose brand name is translated per locale)

The brand name is used for: the package directory (`ui/brandname/`), the route constant in `AppNavigation.kt`, the Hub card title, and the screen's `TopAppBar` title. Use the same name for the string key prefix (`brandname_*`) and the accent token (`BrandnameAccent`).

### 2 — Use string resources for all UI chrome

All visible text in `@Composable` scope must use `stringResource(R.string.key)`. Provide translations for all three active locales: `values/strings.xml` (English), `values-es/strings.xml` (Spanish), `values-pt/strings.xml` (Portuguese).

**In scope for extraction:** TopAppBar titles, section headers, button labels, filter chip labels, search placeholder text, status badges, empty-state messages, FAB labels.

**Out of scope (leave hardcoded):** Fake data in `private val` lists outside composable scope (names, prices, messages, timestamps, product titles). Data class field values used purely as illustrative content.

**Key constraint:** `stringResource()` can only be called from `@Composable` context. If a list of translatable labels is defined at file level as `private val`, move it inside the composable function body before replacing the literals.

Naming convention for new keys: `screenname_descriptor` (e.g., `vortex_search_placeholder`, `sentry_years_old`). Common cross-screen keys share the `common_` prefix (e.g., `common_back`, `common_search`). Spanish copy uses neutral Mexican Spanish (tuteo), never voseo.

### 3 — UI should be visually distinct from existing screens

The new screen's layout, interaction patterns, and visual identity should bring something new to the showcase. Check the screen catalog above before committing to a design direction.

**What to vary:** layout structure (card-heavy vs. list-heavy vs. map-centric), color temperature (warm/cool), theme brightness (dark/light), primary interaction (swipe stack, carousels, filter chips, progress tracking), information density.

**What to avoid duplicating:** a second "list + detail" social feed, a second dark-rose color palette, a second horizontal-card carousel as the hero section.

Each screen must define its own private `XxxTheme` composable (or reuse an existing one deliberately) and declare its accent in `ui/theme/Color.kt` as `val XxxAccent = Color(0xFF…)` for the Hub card.

### 4 — Self-contained and dependency-free

Fake data hardcoded in the same file, state limited to what the UI needs (selected chip, toggles). No new libraries, no network, no ViewModel. Images are optional: provide a fallback (initial in a colored circle) or use Coil with placeholder URLs.
