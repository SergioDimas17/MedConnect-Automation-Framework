# 🌐 MedConnect: Selenium WebDriver + Java Framework

![Java](https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=java)
![Maven](https://img.shields.io/badge/Maven-3.9+-blue?style=for-the-badge&logo=apache-maven)
![Selenium](https://img.shields.io/badge/Selenium-v4.18-red?style=for-the-badge&logo=selenium)
![JUnit5](https://img.shields.io/badge/JUnit-5.10.2-purple?style=for-the-badge&logo=junit5)

[⬅️ Volver al Ecosistema Principal](../README.md)

---

## 🧠 Contexto de Negocio (¿Por qué?)

Para auditar una plataforma médica corporativa, es mandatorio evaluar su rendimiento bajo los estándares más puros e
implementados de la industria. Decidí construir este módulo usando **Selenium WebDriver 4** para certificar que el
ecosistema de calidad de MedConnect sea robusto e inmune a los desajustes tradicionales de hardware, red o tiempos de
renderizado asíncronos propios de arquitecturas SPA complejas.

## 🎯 Impacto Estratégico (¿Para qué?)

El framework erradica los falsos positivos de sincronización mediante la implementación de barreras explícitas dinámicas
que observan los cambios del DOM en tiempo real, garantizando ejecuciones deterministas y proveyendo un mecanismo nativo
de captura de evidencias inmutables directamente en el disco duro.

## 🛠️ Arquitectura Técnica (¿Cómo?)

* **Patrón de Diseño:** Page Object Model (POM), encapsulando selectores XPath e inyecciones de código.
* **Gestión de Drivers In-Memory:** Uso nativo de **Selenium Manager** (Selenium 4) para descargar y emparejar de forma
  transparente los binarios locales del navegador sin dependencias de librerías externas.
* **Sincronización:** Uso estricto de la clase `WebDriverWait` enlazada a `ExpectedConditions`, prohibiendo el uso de
  pausas ciegas invasivas (`Thread.sleep`).

## 🚀 Desafíos de Ingeniería Resueltos

### Flujo E2E 01: Ciclo Crítico de Reserva Base (Happy Path)

* **Resolución de ElementClickInterceptedException:** Ante elementos flotantes asíncronos de Radix UI que bloqueaban los
  clics físicos tradicionales, implementé la inyección directa al bucle de eventos del navegador mediante *
  *`JavascriptExecutor`** (`js.executeScript("arguments[0].click();", element)`), perforando capas de diseño
  transparentes de forma limpia.

### Flujo E2E 02: Suite de Resiliencia de UI y Control Forense

* **Simulación Física de Eventos:** Uso de la API **`Actions`** para disparar eventos físicos nativos del teclado (
  `sendKeys(Keys.ESCAPE)`), auditando activamente que el modal retenga los datos clínicos del formulario frente a
  interrupciones accidentales del usuario.
* **Estrategia Anti-Excepciones del DOM:** Para validar la presencia de modales de error sin interrumpir el hilo de
  JUnit, diseñé el método seguro `isModalPresent()` empleando **`findElements` (en plural)**. Al evaluar el tamaño de la
  lista (`!modals.isEmpty()`), el framework decide de forma segura la existencia del elemento en el árbol de nodos,
  erradicando por completo el temido `NoSuchElementException`.

### Flujo E2E 03: Concurrencia Multi-Hilo Avanzada (Barrera de Sincronización)

* Para solucionar el desfase de hardware que impide simular condiciones de carrera (*Race Conditions*) reales en
  Selenium, diseñé una arquitectura multi-hilo nativa utilizando la clase **`Thread`** de Java coordinada por un cerrojo
  **`CountDownLatch(2)`**.
* Ambos robots llenan de forma independiente sus respectivos formularios. Al llegar al botón de confirmación final, la
  barrera los detiene. Cuando el segundo operador está listo, el cerrojo se libera y ambos disparan la petición de
  submit en paralelo con una diferencia de **apenas 25 milisegundos**.
* **Resultado:** El framework demostró que el servidor defiende la base de datos de duplicados (HTTP 409) y que el
  frontend de la aplicación responde de forma resiliente mostrando los mensajes de alerta en pantalla sin cierres
  inesperados.

## 📊 Gobierno de Pruebas y Evidencias Visuales

El sistema gestiona la captura pasiva de artefactos visuales mediante el ciclo de vida `@AfterEach` combinando el objeto
`TestInfo`:

* **Capturas Estructuradas:** El framework realiza un casteo seguro a la interfaz `TakesScreenshot` para capturar la UI
  en formato PNG exactamente antes de ejecutar el `driver.quit()`.
* **Trazabilidad Organizacional:** Las evidencias inmutables son indexadas de forma cronológica en disco dentro de la
  estructura de Maven:
  `selenium-java/target/evidencias/`

## 🏁 Instrucciones de Ejecución

1. Navega al directorio: `cd selenium-java`
2. Ejecuta la suite: `mvn clean test`
3. Consulta los archivos PNG generados directamente en: `target/evidencias/`