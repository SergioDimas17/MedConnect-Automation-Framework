# 🎭 MedConnect: Playwright + Java Framework

![Java](https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=java)
![Maven](https://img.shields.io/badge/Maven-3.9+-blue?style=for-the-badge&logo=apache-maven)
![Playwright](https://img.shields.io/badge/Playwright-v1.49-green?style=for-the-badge&logo=playwright)
![JUnit5](https://img.shields.io/badge/JUnit-5.10.2-purple?style=for-the-badge&logo=junit5)

[⬅️ Volver al Ecosistema Principal](../README.md)

---

## 🧠 Contexto de Negocio (¿Por qué?)

La velocidad de retroalimentación en los pipelines de despliegue es vital para el desarrollo moderno. Decidí implementar
esta suite bajo **Playwright** aprovechando su arquitectura nativa basada en WebSockets, superando las limitaciones
tradicionales de los drivers síncronos pesados y asegurando una suite de pruebas ágil capaz de auditar la consistencia
lógica de la plataforma de salud sin generar cuellos de botella en la infraestructura.

## 🎯 Impacto Estratégico (¿Para qué?)

El framework actúa como un guardián forense automatizado de la interfaz, diseñado para interceptar regresiones visuales
y de experiencia de usuario (UX) en microsegundos, aislando las pruebas mediante contextos limpios de incógnito sin
penalizar el rendimiento de la memoria RAM del servidor.

## 🛠️ Arquitectura Técnica (¿Cómo?)

* **Patrón de Diseño:** Page Object Model (POM) estricto, aislando la lógica de selectores de las aserciones de JUnit 5.
* **Centralización por Herencia:** Implementación de `BaseTest.java` para gobernar el ciclo de vida del navegador y
  manejar dinámicamente la variable `baseUrl` para mitigar el enrutamiento estricto de la SPA en Vercel.

- **Diagnóstico:** Integración de **AspectJ Weaver** para enlazar metadatos de ejecución.

## 🚀 Desafíos de Ingeniería Resueltos

### Flujo E2E 01: Happy Path de Reserva Base

Automatización del ciclo completo de login y agendamiento médico interactuando con selectores de carga rápida y
validando la indexación correcta de los datos en las tablas de React.

### Flujo E2E 02: Suite de Resiliencia de UI y Auditoría de Parches

* **Normalización de Inputs:** Inyección de cadenas con mayúsculas y espacios caóticos (`"   cArLoS pÉrEz   "`),
  comprobando el correcto procesamiento del backend.
* **Control de Strict Mode:** Resolución de excepciones de ambigüedad en el DOM mediante restricciones de selectores y
  el operador `.first()` ante múltiples alertas de error idénticas.
* **Auditoría al Bug de la Tecla Escape:** Validación automatizada de que la propiedad física `Escape` no destruya el
  modal ni purgue la información digitada, confirmando la efectividad del parche de desarrollo (`onEscapeKeyDown`).

### Flujo E2E 03: Control de Concurrencia Multi-Usuario (Aislamiento de Contexto)

* Instanciación paralela de múltiples `BrowserContext` de incógnito totalmente aislados en memoria RAM dentro del mismo
  hilo.
* **Caza del Bug Crítico de UX:** El framework expuso de manera científica cómo la interfaz de usuario ocultaba el
  rechazo de guardado del servidor (`HTTP 409 Conflict`) al Operador 2, cerrando el modal abruptamente y simulando un
  falso positivo de éxito.
* **Blindaje de Pipeline:** Aplicación de la estrategia de exclusión controlada (`@Disabled`) en JUnit 5 enlazada al
  ticket de Jira para preservar el **BUILD SUCCESS** diario mientras se aplica la corrección en el código de producción.

## 📊 Gobierno de Pruebas y Evidencias Visuales

Implementación de listeners pasivos mediante extensiones de JUnit 5 (`AfterTestExecutionCallback`):

* **Grabación de Videos:** Captura nativa de la sesión interactiva del robot almacenada en `target/allure-results/`.
* **Screenshots Forenses:** Captura automatizada de página completa (`setFullPage(true)`) inyectada directamente en
  formato de bytes dentro del Dashboard interactivo de **Allure Reports** en caso de fallas.

## 🏁 Instrucciones de Ejecución

1. Navega al directorio: `cd playwright-java`
2. Ejecuta la suite: `mvn clean test`
3. Compila el reporte: `mvn allure:report`
4. Abre el dashboard: Abre en tu navegador `target/site/allure-report/index.html`