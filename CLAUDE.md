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

| Screen | Package | Theme type | Accent |
|---|---|---|---|
| Tastique | `ui/tastique/` | Dark | Amber `0xFFFFB300` |
| MediCare | `ui/medicare/` | Light | Teal `0xFF26A69A` |
| Drape | `ui/drape/` | Light | Coral `0xFFFF3D2E` |
| Nexus | `ui/nexus/` | Light | Blue `0xFF1B4FD8` + Orange `0xFFFF6B2C` |
| Expert | `ui/expert/` | Light | Navy `0xFF0C2D6B` |
| Near | `ui/near/` | Light | Violet `0xFF311B92` + Green `0xFF00C853` |
| Spark | `ui/spark/` | Dark | Rose `0xFFFF4B81` + Sunset orange `0xFFFF8C42` |
| Vortex | `ui/vortex/` | Dark | Cyan `0xFF00D4FF` + Purple `0xFF7C3AED` |
| Sentry | `ui/sentry/` | Light | Blue `0xFF1565C0` |
| Portal | `ui/portal/` | Light | Blue `0xFF1565C0` |

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
- **Tool Usage**: Always use `replace_file_content` or `multi_replace_file_content` for surgical edits. NEVER use `run_shell_command` with `sed` or `awk` to modify files.

## New Screen Guidelines

Follow these rules when adding a new showcase screen to the project.

### 1 — Language-agnostic brand names

Choose a name that works in any locale — a made-up brand word that doesn't rely on any specific language for its meaning. Avoid translating or adapting a concept into English, Spanish, or Portuguese and using that translation as the name.

**Good:** Vortex, Sentry, Spark, Tastique, MediCare, Drape  
**Avoid:** ~~Expert/Pericia~~, ~~Near/Cerka~~, ~~Nexus/Nexo~~ (these were legacy screens created before this rule)

The brand name is used for: the package directory (`ui/brandname/`), the route constant in `AppNavigation.kt`, the Hub card title, and the screen's `TopAppBar` title.

### 2 — Use string resources for all UI chrome

All visible text in `@Composable` scope must use `stringResource(R.string.key)`. Provide translations for all three active locales: `values/strings.xml` (English), `values-es/strings.xml` (Spanish), `values-pt/strings.xml` (Portuguese).

**In scope for extraction:** TopAppBar titles, section headers, button labels, filter chip labels, search placeholder text, status badges, empty-state messages, FAB labels.

**Out of scope (leave hardcoded):** Fake data in `private val` lists outside composable scope (names, prices, messages, timestamps, product titles). Data class field values used purely as illustrative content.

**Key constraint:** `stringResource()` can only be called from `@Composable` context. If a list of translatable labels is defined at file level as `private val`, move it inside the composable function body before replacing the literals.

Naming convention for new keys: `screenname_descriptor` (e.g., `vortex_search_placeholder`, `sentry_years_old`). Common cross-screen keys share the `common_` prefix (e.g., `common_back`, `common_search`).

### 3 — UI should be visually distinct from existing screens

The new screen's layout, interaction patterns, and visual identity should bring something new to the showcase. Check the theme table above before committing to a design direction.

**What to vary:** layout structure (card-heavy vs. list-heavy vs. map-centric), color temperature (warm/cool), theme brightness (dark/light), primary interaction (swipe stack, carousels, filter chips, progress tracking), information density.

**What to avoid duplicating:** a second "list + detail" social feed, a second dark-rose color palette, a second horizontal-card carousel as the hero section.

Each screen must define its own private `XxxTheme` composable (or reuse an existing one deliberately) and declare its accent in `ui/theme/Color.kt` as `val XxxAccent = Color(0xFF…)` for the Hub card.

---

## Plan: Pantalla "Pericia" — Seguimiento de Siniestros

### Descripción

Propuesta de UI en español para una app de seguimiento de siniestros ante compañías de seguros. Nombre de marca: **Pericia**. Tema claro profesional con paleta navy/azul.

Cubre los 4 requerimientos del brief:
1. **Gestión de expedientes** — lista de siniestros con estado y progreso
2. **Agenda de entrevistas** — carrusel horizontal de próximas citas
3. **Análisis de documentación** — checklist de documentos del expediente
4. **Evaluación de procedencia** — indicador visual de validez del reclamo

### Tema y colores

| Token | Hex | Uso |
|---|---|---|
| `PericiaFondo` | `0xFFF4F7FB` | Fondo general |
| `PericiaPrimario` | `0xFF0C2D6B` | Navy — TopAppBar, encabezados, botones primarios |
| `PericiaAzul` | `0xFF1565C0` | Azul medio — chips activos, progress bar |
| `PericiaAnaranjado` | `0xFFE65100` | Naranja oscuro — pendientes, alertas |
| `PericiaVerde` | `0xFF2E7D32` | Verde — expedientes resueltos / docs recibidos |
| `PericiaRojo` | `0xFFC62828` | Rojo — rechazados / docs faltantes urgentes |
| `PericiaSurface` | `0xFFFFFFFF` | Fondo de tarjetas |
| `PericiaSubtexto` | `0xFF6B7896` | Texto secundario |
| `PericiaOnPrimario` | `0xFFFFFFFF` | Texto sobre fondo navy |

Acento Hub card: `0xFF0C2D6B`

Tema privado: `PericiaTema` wrapping `MaterialTheme` con `lightColorScheme`.

### Enumeraciones y data classes

```kotlin
enum class EstadoExpediente(val etiqueta: String)
// ACTIVO, EN_REVISION, PENDIENTE_DOC, RESUELTO, RECHAZADO

data class Expediente(
    numero: String,       // "EXP-2025-0041"
    asegurado: String,    // "Carlos Mendoza"
    tipo: String,         // "Siniestro vehicular"
    fechaApertura: String,
    estado: EstadoExpediente,
    progreso: Float,      // 0.0f..1.0f
    monto: String,        // "$45,000"
    icono: ImageVector    // Icons.Default.*
)

data class Cita(
    asegurado: String,
    tipoCita: String,     // "Entrevista inicial", "Inspección técnica"
    fecha: String,        // "15 may"
    hora: String,         // "10:30 AM"
    expedienteId: String
)

data class DocumentoExpediente(
    nombre: String,
    recibido: Boolean
)
```

### Layout de la pantalla (LazyColumn de arriba hacia abajo)

```
TopAppBar
  ← ArrowBack   "Pericia"   [Bell icon] [Avatar circle]

1. Tarjeta resumen (3 stats: Total / Activos / Resueltos este mes)
2. FilterChips fila: Todos · Activos · En revisión · Pendientes · Resueltos
3. Sección "Expedientes recientes"
     TarjetaExpediente × 3–4 (estado badge, progress bar, asegurado, monto, tipo)
4. Sección "Próximas citas"
     LazyRow de TarjetaCita × 3 (fecha, hora, asegurado, tipo)
5. Sección "Documentación del expediente activo"
     FilaDocumento × 5 (check/x, nombre del doc)
6. Sección "Evaluación de procedencia"
     Tarjeta con indicador visual (barra de validez, veredicto, observaciones)

FAB  +  "Nuevo expediente"
```

### Composables privados

| Composable | Responsabilidad |
|---|---|
| `TarjetaResumen` | Tres columnas de stats en una Card |
| `BadgeEstado` | Pill de color según `EstadoExpediente` |
| `TarjetaExpediente` | Tarjeta full-width con badge, progress bar, ícono, monto |
| `TarjetaCita` | Tarjeta compacta para el carrusel horizontal |
| `FilaDocumento` | Fila con ícono check/error + nombre del documento |
| `TarjetaEvaluacion` | Tarjeta de validez con barra de porcentaje y veredicto |

### Fake data

- 4 expedientes con distintos tipos (vehicular, hogar, salud, robo) y estados variados
- 3 citas próximas (distintos asegurados y tipos)
- 5 documentos (3 recibidos, 2 pendientes) del expediente activo
- Evaluación hardcoded: 78 % de validez → "Procede con observaciones"

### Archivos a crear/modificar

| Archivo | Acción |
|---|---|
| `ui/pericia/PericiaScreen.kt` | **Crear** — pantalla completa |
| `navigation/AppNavigation.kt` | **Modificar** — agregar ruta `PERICIA = "pericia"` |
| `ui/hub/HubScreen.kt` | **Modificar** — agregar ShowcaseCard para Pericia |
| `ui/theme/Color.kt` | **Modificar** — agregar `val PericiaAccent = Color(0xFF0C2D6B)` |

### Alcance ajustado

Un solo PR. **No se necesitan pantallas de detalle ni navegación adicional.** La pantalla es el main screen (dashboard) de la app con dummy data hardcodeada en el mismo archivo, igual que las otras pantallas del showcase. No se requieren data classes complejas ni lógica de estado más allá del FilterChip seleccionado.

### PR único: PericiaScreen completa

- `ui/pericia/PericiaScreen.kt` — pantalla completa autocontenida (tema, colores, fake data, todos los composables)
- `navigation/AppNavigation.kt` — agregar ruta `PERICIA = "pericia"`
- `ui/hub/HubScreen.kt` — agregar ShowcaseCard para Pericia
- `ui/theme/Color.kt` — agregar `val PericiaNavy = Color(0xFF0C2D6B)`

---

## Plan: Pantalla "Cerka" — Marketplace de Servicios

### Descripción

Propuesta de UI en español para un marketplace de servicios a domicilio, estilo Uber/Glovo. Nombre de marca: **Cerka** (de "cerca" — servicios locales a tu alrededor). Tema claro premium con paleta violeta profundo + verde activo.

Cubre los 3 requerimientos del brief:
1. **Geolocalización** — mapa simulado con Canvas (cuadrícula de calles, pins de proveedores, ruta activa)
2. **Múltiples perfiles** — tarjetas de proveedor con rating, distancia y ETA; pedido activo con tracking
3. **Marketplace escalable** — categorías de servicio, proveedores cercanos, historial de pedidos

### Tema y colores

| Token | Hex | Uso |
|---|---|---|
| `CerkaFondo` | `0xFFF8F9FA` | Fondo general |
| `CerkaPrimario` | `0xFF311B92` | Violeta profundo — TopAppBar, botones, badges |
| `CerkaAcento` | `0xFF00C853` | Verde — estado activo/disponible, CTA secundarios |
| `CerkaAmbar` | `0xFFFFB300` | Ámbar — rating stars, destacados |
| `CerkaRojo` | `0xFFD50000` | Rojo — cancelado, no disponible |
| `CerkaSurface` | `0xFFFFFFFF` | Fondo de tarjetas |
| `CerkaSubtexto` | `0xFF757575` | Texto secundario |
| `CerkaOnPrimario` | `0xFFFFFFFF` | Texto sobre violeta |

Acento Hub card: `0xFF311B92`

Tema privado: `CerkaTema` wrapping `MaterialTheme` con `lightColorScheme`.

### Layout de la pantalla (LazyColumn de arriba hacia abajo)

```
TopAppBar
  ← ArrowBack   "Cerka"   [🔔 Bell] [Avatar circle]
  📍 "Colonia Del Valle, CDMX"  (subtítulo)

1. MapaSimulado — Box altura fija (200dp) con Canvas:
     cuadrícula de calles, pin usuario (centro), 3–4 pins de proveedores, línea de ruta activa

2. TarjetaPedidoActivo
     Proveedor asignado, avatar, ⭐ rating
     "Llega en 8 min" + LinearProgressIndicator (estado del pedido)
     Botones: Llamar / Ver detalle

3. Categorías de servicio (LazyRow de ChipCategoria)
     Mensajería · Mudanza · Limpieza · Plomería · Electricidad · Más

4. Sección "Proveedores cercanos"
     LazyRow de TarjetaProveedor:
       avatar, nombre, especialidad, ⭐ rating, distancia, ETA, precio base

5. Sección "Mis pedidos recientes"
     FilaPedido × 3: ícono servicio, proveedor, fecha, monto, badge estado
```

### Composables privados

| Composable | Responsabilidad |
|---|---|
| `MapaSimulado` | Box + Canvas: cuadrícula de calles, pins, ruta |
| `TarjetaPedidoActivo` | Proveedor asignado, ETA, progress bar, botones |
| `ChipCategoria` | Ícono + label para LazyRow de categorías |
| `TarjetaProveedor` | Avatar, rating, distancia, ETA, precio base |
| `FilaPedido` | Fila compacta de historial con badge de estado |

### Fake data

- 4 proveedores cercanos (Mensajería, Limpieza, Mudanza, Plomería — distintos ratings, distancias, ETAs)
- 1 pedido activo en progreso (proveedor asignado, 8 min ETA, 60% progreso)
- 3 pedidos recientes con distintos estados (Completado, Cancelado, En camino)
- 5 categorías de servicio con `Icons.Default.*`

### Archivos a crear/modificar

| Archivo | Acción |
|---|---|
| `ui/cerka/CerkaScreen.kt` | **Crear** — pantalla completa autocontenida |
| `navigation/AppNavigation.kt` | **Modificar** — agregar ruta `CERKA = "cerka"` |
| `ui/hub/HubScreen.kt` | **Modificar** — agregar ShowcaseCard para Cerka |
| `ui/theme/Color.kt` | **Modificar** — agregar `val CerkaAccent = Color(0xFF311B92)` |

### Alcance

Un solo PR. Pantalla completamente autocontenida con dummy data hardcodeada en el mismo archivo. El `MapaSimulado` usa solo Canvas de Compose — sin Google Maps ni SDKs externos. No se requiere lógica de estado más allá del scroll.

---

## Plan: Pantalla "Chispa" — App de Citas

### Descripción

Propuesta de UI en español para una app de citas tipo Tinder enfocada en Mallorca. Nombre de marca: **Chispa** (spark — metáfora del flechazo). Tema **oscuro premium** con paleta rosa/coral + naranja atardecer, para que las fotos de perfil destaquen al máximo sobre fondo profundo.

Cubre los requerimientos del brief MVP:
1. **Perfil visual** — tarjeta de perfil con foto grande, nombre, edad, ciudad, verificación e intereses
2. **Sistema de matching swipe** — stack de tarjetas apiladas con botones Like / No / Super Like
3. **Matches recientes** — carrusel horizontal con borde degradado y badge "NUEVO"
4. **Chat / mensajes** — lista de conversaciones con estado online y no-leídos
5. **Sección Premium** — preview borroso de "quién te dio Like" con CTA de suscripción

### Tema y colores

| Token | Hex | Uso |
|---|---|---|
| `ChispaFondo` | `0xFF0F0F1A` | Fondo oscuro profundo |
| `ChispaPrimario` | `0xFFFF4B81` | Rosa-coral — botón Like, badges activos, acentos |
| `ChispaAcento` | `0xFFFF8C42` | Naranja atardecer — degradados, highlights |
| `ChispaSuperLike` | `0xFF00CFFD` | Cyan brillante — botón Super Like |
| `ChispaSurface` | `0xFF1C1C2E` | Fondo de tarjetas y secciones |
| `ChispaSurface2` | `0xFF2A2A3E` | Fondo alternativo (filas de mensajes) |
| `ChispaSubtexto` | `0xFF9E9EBE` | Texto secundario |
| `ChispaOnline` | `0xFF4CAF50` | Dot de estado online |
| `ChispaOnPrimario` | `0xFFFFFFFF` | Texto sobre primario |

Acento Hub card: `0xFFFF4B81`

Tema privado: `ChispaTema` wrapping `MaterialTheme` con `darkColorScheme`.

### Data classes

```kotlin
data class PerfilCitas(
    val nombre: String,
    val edad: Int,
    val ciudad: String,
    val distanciaKm: String,
    val descripcion: String,
    val intereses: List<String>,
    val verificado: Boolean,
    val fotoRes: Int?   // R.drawable.* o null → fallback inicial
)

data class MatchCitas(
    val nombre: String,
    val fotoRes: Int?,
    val esNuevo: Boolean
)

data class ConversacionCitas(
    val nombre: String,
    val fotoRes: Int?,
    val ultimoMensaje: String,
    val hora: String,
    val noLeidos: Int,
    val online: Boolean
)
```

### Layout de la pantalla (LazyColumn de arriba hacia abajo)

```
TopAppBar (fondo ChispaSurface, sin sombra)
  ← ArrowBack   🔥 "Chispa"   [🔔 Bell] [Tune filtros]

1. StackTarjetasPerfil — Box apilado altura fija ~520dp:
     Tarjeta 3 (atrás): rotada +6°, escala 0.88, opacidad 0.55
     Tarjeta 2 (medio): rotada -3°, escala 0.94, opacidad 0.80
     Tarjeta 1 (frente): foto full-width, gradient oscuro en la parte inferior
       ├── Badge distancia (top-right): "1.2 km"
       ├── Badge verificado (top-left): ícono check + "Verificada"
       ├── Nombre + edad: "Elena García, 26"
       ├── Ciudad: "📍 Palma de Mallorca"
       └── Chips de intereses: "🏄 Surf"  "📸 Foto"  "🍷 Gastro"

2. BotonesAccion — Row centrado (espaciado proporcional):
     CircleButton 56dp gris-oscuro: ✕  (No me interesa)
     CircleButton 44dp cyan:        ⭐  (Super Like)
     CircleButton 56dp rosa-coral:  ♥  (Me gusta)

3. Sección "Nuevos matches ✨"
     LazyRow de BurbujaMatch × 6:
       foto circular 72dp + borde degradado rosa→naranja
       nombre debajo (12sp)
       badge "NUEVO" en forma de pill en los 2 más recientes

4. Sección "Mensajes"
     FilaMensaje × 4:
       avatar 52dp + dot online (verde o gris)
       columna: nombre (bold) + último mensaje truncado
       hora + badge count no-leídos si > 0

5. Sección "¿Quién te gustó? 🔒" (Premium)
     Row de 3 avatares 72dp con Modifier.blur(8.dp) + ícono candado encima
     Card ChispaSurface con texto "Desbloquea con Premium"
     Botón filled rosa: "Ver quién te dio Like"
```

### Composables privados

| Composable | Responsabilidad |
|---|---|
| `StackTarjetasPerfil` | Box con 3 `TarjetaPerfil` apiladas con offset/rotate/scale |
| `TarjetaPerfil` | Foto (o fallback color+inicial), gradient overlay, badges, info, chips |
| `AvatarConFallback` | `Image(painterResource)` si fotoRes != null; círculo coloreado + inicial si null |
| `BotonesAccion` | Row de 3 `CircleButton` (No / SuperLike / Like) con tamaños y colores distintos |
| `BurbujaMatch` | Avatar circular con borde `Brush` gradiente + nombre + badge "NUEVO" opcional |
| `FilaMensaje` | Avatar + dot online + nombre + preview mensaje + hora + badge no-leídos |
| `SeccionPremiumBlur` | 3 avatares con `blur` + candado + CTA card |

### Fake data

**Stack de perfiles (3 tarjetas)**
- Elena García, 26, Palma, 1.2 km — verificada — intereses: Surf, Fotografía, Gastronomía, Buceo — foto: `R.drawable.chispa_elena`
- Sofía Ruiz, 29, Inca, 8.5 km — intereses: Cocina, Yoga, Viajes — foto: `R.drawable.chispa_sofia`
- Ana Torres, 24, Alcúdia, 15.3 km — intereses: Buceo, Música, Arte — foto: `R.drawable.chispa_ana`

**Matches recientes (6)**
- Elena → esNuevo = true, foto: `chispa_elena`
- Martina → esNuevo = true, foto: `chispa_martina`
- Camila → foto: `chispa_camila`
- Sara → foto: `chispa_sara`
- Laura → foto: null (fallback inicial "L")
- Isabel → foto: null (fallback inicial "I")

**Conversaciones (4)**
1. Martina — "¿Te gustan los atardeceres en Cap Formentor? 🌅" — 5 min — 2 no leídos — online
2. Camila — "¡Claro que sí! ¿Cuándo quedamos? 😊" — 1h — 0 no leídos — offline
3. Sara — "Hola! Vi que también te gusta el buceo 🤿" — 3h — 1 no leído — online
4. Camila R. — "Genial conocerte!" — ayer — 0 no leídos — offline

### Recursos de imagen necesarios

Agregar en `res/drawable/` antes de implementar (o dejar `fotoRes = null` para usar fallback de iniciales):
- `chispa_elena.jpg` — perfil frontal del stack
- `chispa_sofia.jpg` — perfil medio del stack
- `chispa_ana.jpg` — perfil trasero del stack
- `chispa_martina.jpg`, `chispa_camila.jpg`, `chispa_sara.jpg` — matches/conversaciones

La pantalla **funciona sin imágenes** gracias al composable `AvatarConFallback` (muestra un círculo con color generado desde el nombre + inicial centrada).

### Archivos a crear/modificar

| Archivo | Acción |
|---|---|
| `ui/chispa/ChispaScreen.kt` | **Crear** — pantalla completa autocontenida |
| `navigation/AppNavigation.kt` | **Modificar** — agregar ruta `CHISPA = "chispa"` |
| `ui/hub/HubScreen.kt` | **Modificar** — agregar ShowcaseCard para Chispa |
| `ui/theme/Color.kt` | **Modificar** — agregar `val ChispaAccent = Color(0xFFFF4B81)` |
| `res/drawable/` | **Agregar** (opcional) — fotos de perfil para máximo impacto visual |

### Alcance

Un solo PR. Pantalla completamente autocontenida con dummy data hardcodeada. No se requieren SDKs externos ni animaciones de swipe reales — el efecto de stack se logra con `graphicsLayer { rotationZ = ...; scaleX = ...; scaleY = ... }`. La sección Premium usa `Modifier.blur()` disponible en Compose desde API 31 (min SDK del proyecto es 24, por lo que se aplica solo en >= API 31 con un `if (Build.VERSION.SDK_INT >= 31)` fallback).
