# Persistencia local: Room y SQLite

## Alcance
Persistencia local para los países favoritos del listado Home.
Se completa la pantalla Favorites incluida en el menú de la aplicación base.
Proyecto base: https://github.com/luischang/AppUESAN202602
Versión de referencia: 69ab353d45485711026d55fa8935269fcda1ffe6.

## Qué se agregó
- Guardar y quitar países del listado existente Home; consultar Favorites.
- Tabla `favorite_countries` con nombre único, ranking y URL de bandera.
- Room 2.8.4 sobre SQLite: entidad, DAO y AppDatabase (archivo privado `appuesan.db`).
- DAO suspend para escritura y Flow para actualización del listado.
- Base singleton con applicationContext y ViewModel compartido entre ambas pantallas.
- Nombre único: guardar otra vez actualiza sin duplicados.
- Sin allowMainThreadQueries ni borrado destructivo de la base al abrirla.
- Es almacenamiento de este dispositivo, no sincronización ni separación por cuenta.
- El listado original es de demostración: no se afirma que el ranking sea actual.

Room es una capa sobre SQLite; no hacen falta dos bases de datos para demostrar ambas.
Se conservan las versiones de SDK/Gradle/Kotlin/Firebase del proyecto del profesor.
Se agregó KSP 2.3.11 para generar el DAO y lifecycle-viewmodel-compose 2.6.1,
igual que lifecycle-runtime-ktx de la base. La resolución completa de dependencias
queda pendiente de compilar en el entorno Android del curso.

## Prueba manual de aceptación (pendiente)
1. Abrir en Android Studio, sincronizar Gradle y configurar el SDK 37 como la base.
   Usar el JDK compatible con Gradle 9.6 y la configuración Firebase/API del curso;
   `google-services.json` y `local.properties` no se generan ni se inventan aquí.
2. Ejecutar en un emulador o celular con API 34 o superior y entrar con una cuenta del curso.
3. En Home guardar Perú y Brasil. En Favorites deben verse ambos una sola vez.
4. Forzar detención desde Ajustes y abrir la app otra vez: deben seguir guardados.
   Si el login vuelve a pedir acceso, entrar con la misma cuenta. No desinstalar ni borrar datos.
5. Quitar Perú, cerrar y reabrir: debe quedar Brasil.
6. Sin internet, los registros deben seguir disponibles; imágenes remotas pueden no cargar.
7. Opcional: App Inspection → Database Inspector → appuesan.db → favorite_countries.

## Pruebas de persistencia instrumentadas (preparadas, NO ejecutadas)
`FavoritesPersistenceTest` crea una base de prueba con nombre UUID y verifica:
- dato guardado tras cerrar y reabrir la conexión real de Room;
- actualización de un nombre sin duplicarlo;
- borrado de un favorito sin perder los demás tras reabrir.
Sólo elimina su propia base de prueba al terminar.

Desde Android Studio con dispositivo conectado, o desde terminal con JDK/SDK configurados:

```sh
./gradlew :app:assembleDebug :app:testDebugUnitTest
./gradlew :app:connectedDebugAndroidTest -Pandroid.testInstrumentationRunnerArguments.class=dev.lchang.appuesan.FavoritesPersistenceTest
```

Estado: código candidato. No se acredita build, pruebas Android ni funcionamiento real
hasta ejecutar esos pasos.

Fuentes oficiales:
- https://developer.android.com/training/data-storage/room
- https://developer.android.com/training/data-storage/room/accessing-data
- https://developer.android.com/jetpack/androidx/releases/room
- https://github.com/google/ksp/releases/tag/2.3.11
