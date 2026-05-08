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
| Pericia | Light | Navy `0xFF0C2D6B` |
| Cerka | Light | Violet `0xFF311B92` + Green accent `0xFF00C853` |

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
