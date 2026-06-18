# 🎭 MedConnect: Cypress + JavaScript Framework

![JavaScript](https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black)
![NodeJS](https://img.shields.io/badge/Node.js-18+-339933?style=for-the-badge&logo=nodedotjs&logoColor=white)
![Cypress](https://img.shields.io/badge/Cypress-v13.17-17202C?style=for-the-badge&logo=cypress&logoColor=white)
![Mochawesome](https://img.shields.io/badge/Mochawesome-Report-blue?style=for-the-badge)

[⬅️ Volver al Ecosistema Principal](../README.md)

---

## 🧠 Contexto de Negocio (¿Por qué?)

La validación del estado dinámico del frontend es esencial en las Single Page Applications (SPA) modernas. Decidí implementar esta suite bajo **Cypress** aprovechando su arquitectura única de ejecución "in-browser" (dentro del mismo hilo de ejecución de la aplicación). Esto me permitió superar las limitaciones de la asincronía de React, asegurando una suite capaz de auditar la reactividad pura del DOM y el comportamiento de los componentes de la plataforma de salud sin depender de tiempos de espera estáticos.

## 🎯 Impacto Estratégico (¿Para qué?)

El framework actúa como un auditor directo de la Experiencia de Usuario (UX) y el estado de la interfaz. Fue diseñado para interactuar íntimamente con el DOM, validando que el frontend proteja correctamente la integridad de los datos médicos y proporcionando un feedback visual inmediato y confiable en los pipelines de integración.

## 🛠️ Arquitectura Técnica (¿Cómo?)

* **Reusabilidad Estructural:** Implementación de **Custom Commands** (`cypress/support/commands.js`) para centralizar flujos repetitivos como el `cy.login()`, garantizando la mantenibilidad si la autenticación cambia.
* **Idempotencia y Estado Limpio:** Configuración estricta en el bloque `beforeEach` para destruir rastros ambientales (`cy.clearAllCookies()`, `cy.clearAllLocalStorage()`, `cy.clearAllSessionStorage()`), asegurando que cada test parta desde cero.
* **Intercepción Activa (Ignorar Excepciones Externas):** Implementación de listeners globales (`uncaught:exception`) para evitar que errores inofensivos de compilación en segundo plano o cancelaciones de red de Vercel (`DOMException`) quiebren el hilo principal de las pruebas.

## 🚀 Desafíos de Ingeniería Resueltos

### Flujo E2E 01: Happy Path y Estabilidad con Radix UI

* **Caza de Selectores Complejos:** Automatización del flujo secuencial de agendamiento interactuando con componentes modernos flotantes (Radix UI / Shadcn). En lugar de usar IDs volátiles, construí variables de mapeo robustas (`[role="option"], [data-radix-collection-item]...`) para garantizar clics precisos en menús desplegables.

### Flujo E2E 02: Resiliencia en Single Page Applications (SPA)

* **Sincronización sin URLs:** Resolví el típico bloqueo de las SPA donde la URL no cambia físicamente tras el login. En lugar de esperar enrutamientos estáticos (`cy.url()`), orienté al robot a validar la renderización real en el DOM de elementos clave (`cy.contains('Nueva Cita')`).
* **Regresión de la Tecla Escape:** Validé la inyección de datos y certifiqué la robustez de la UI emulando comandos físicos (`{esc}`) para garantizar que el modal no colapse perdiendo los datos clínicos del paciente.

### Flujo E2E 03: Validación Reactiva de Concurrencia (Enfoque UI)

* En lugar de forzar colisiones en el backend, diseñé una auditoría estricta basada en el **diseño real de la App**. Programé al robot para recorrer el camino de reserva que un Operador A ya había ocupado de forma secuencial.
* **Aserciones Estrictas de Estado:** Certifiqué que el sistema reacciona en tiempo real, validando matemáticamente que la franja horaria ocupada se renderice como inaccesible mediante la aserción estricta del atributo en el nodo de React (`.should('have.attr', 'aria-disabled', 'true')`), evitando falsos positivos sin necesidad de hacer clics inútiles.

---

## ⚖️ Conclusiones Arquitectónicas: El Reto de la Concurrencia

Durante el diseño de este ecosistema Omni-Framework, la prueba de **Concurrencia Multi-Usuario** reveló una diferencia arquitectónica fundamental entre las herramientas líderes del mercado:

A diferencia de **Playwright** (que permite instanciar múltiples `BrowserContext` aislados en el mismo hilo) y de **Selenium WebDriver** (que permite abrir múltiples navegadores orquestados mediante hilos de Java paralelos), **Cypress posee una arquitectura estrictamente *in-browser***. Cypress se ejecuta directamente dentro del bucle de eventos del navegador, lo que significa que **no soporta por diseño la apertura de múltiples navegadores, sesiones o pestañas simultáneas en un mismo test.**

**💡 Conclusión Técnica:** No pudimos recrear una verdadera *Condición de Carrera* (Race Condition) simultánea golpeando el servidor en el mismo milisegundo con Cypress, ya que su arquitectura nos limita a interacciones secuenciales en una sola ventana. 

Sin embargo, adaptamos la estrategia: en lugar de usar Cypress para estresar la base de datos (tarea que delegamos a Selenium y Playwright), lo utilizamos para lo que mejor sabe hacer: **auditar implacablemente la reactividad del DOM**. Comprobamos secuencialmente que, tras la reserva del Operador A, el frontend reacciona aislando el recurso para el Operador B, logrando certificar la seguridad visual de la interfaz.

---

## 📊 Gobierno de Pruebas y Evidencias Visuales

Implementación de un ecosistema de reporte unificado y forense:

* **Mochawesome Reporter:** Generación automática de reportes HTML estáticos y elegantes en cada ejecución (`cypress/reports/report.html`), unificando los resultados de todos los scripts.
* **Video y Screenshots Nativos:** Aprovechamiento de la capacidad nativa de Cypress para grabar en formato `.mp4` el paso a paso completo de la prueba en modo *headless*, y toma de capturas de pantalla automáticas (`.png`) exactamente en el milisegundo donde ocurre una aserción fallida.

## 🏁 Instrucciones de Ejecución

1. Navega al directorio del framework: `cd cypress-js`
2. Instala las dependencias (si es la primera vez): `npm install`
3. Ejecución interactiva (Modo UI para debugging): `npx cypress open`
4. Ejecución en consola (Modo Headless y generación de Reportes HTML): `npx cypress run`
5. Revisa tus evidencias en la carpeta: `cypress/reports/` y `cypress/videos/`