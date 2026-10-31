# AppUESAN202602 — Persistencia local

Aplicación Android con Jetpack Compose y favoritos persistentes mediante Room sobre SQLite.
Basada en [AppUESAN202602](https://github.com/luischang/AppUESAN202602).

## Funcionalidad
- Guardar países desde Home.
- Consultar y quitar favoritos en Favorites.
- Almacenamiento local en `appuesan.db`, sin duplicar nombres.
- Tres pruebas instrumentadas de persistencia, actualización y eliminación.

## Ejecutar
Abrir el proyecto en Android Studio y configurar el SDK 37 y un JDK compatible con
Gradle 9.6. Usar un dispositivo o emulador con Android API 34 o superior.
Completar `local.properties` con la configuración API que corresponda al proyecto base.

El código está preparado. **Compilación y pruebas en dispositivo pendientes.**
Ver [TAREA_ROOM.md](TAREA_ROOM.md) y [PRUEBAS.md](PRUEBAS.md) para el detalle.
