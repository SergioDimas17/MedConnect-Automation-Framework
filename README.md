# 🏥 MedConnect Automation Ecosystem: Suite Omni-Framework

![Java](https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=java)
![JavaScript](https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black)
![Maven](https://img.shields.io/badge/Maven-3.9+-blue?style=for-the-badge&logo=apache-maven)
![NodeJS](https://img.shields.io/badge/Node.js-18+-339933?style=for-the-badge&logo=nodedotjs&logoColor=white)
![Selenium](https://img.shields.io/badge/Selenium-v4.18-red?style=for-the-badge&logo=selenium)
![Playwright](https://img.shields.io/badge/Playwright-v1.49-green?style=for-the-badge&logo=playwright)
![Cypress](https://img.shields.io/badge/Cypress-v13+-17202C?style=for-the-badge&logo=cypress&logoColor=white)

¡Bienvenido a mi ecosistema avanzado de **QA Automation**! Este repositorio modular tipo *Monorepo* centraliza la
estrategia de pruebas de punta a punta (E2E) para **MedConnect**, una plataforma web moderna de agendamiento de citas
médicas estructurada como una *Single Page Application* (SPA) basada en React y Radix UI.

La particularidad estratégica de este ecosistema es que replica exactamente los mismos flujos críticos de negocio bajo
las tres herramientas líderes del mercado: **Playwright, Selenium WebDriver y Cypress**. Esto me permite evaluar y
comparar de forma científica la velocidad de ejecución, estabilidad de los selectores, flujos de sincronización
asíncrona y consumo de infraestructura de cada solución bajo un patrón de arquitectura común.

¡Bienvenido a mi ecosistema avanzado de **QA Automation**! Este repositorio modular tipo *Monorepo* centraliza la estrategia de pruebas de punta a punta (E2E) para **MedConnect**, una plataforma web moderna de agendamiento de citas médicas estructurada como una *Single Page Application* (SPA) basada en React y Radix UI.

La particularidad estratégica de este ecosistema es que replica exactamente los mismos flujos críticos de negocio bajo las tres herramientas líderes del mercado: **Playwright, Selenium WebDriver y Cypress**. Esto me permite evaluar y comparar de forma científica la velocidad de ejecución, estabilidad de los selectores, flujos de sincronización asíncrona y consumo de infraestructura de cada solución bajo un patrón de arquitectura común.

---

## 📂 Estructura y Navegación del Ecosistema

El Monorepo está organizado de forma totalmente independiente por módulos de compilación. Cada suite cuenta con sus propias dependencias, configuraciones de ejecución y bitácoras técnicas detalladas:

* **🎭 [Módulo Playwright + Java](./playwright-java/README.md):** Framework de alta velocidad enfocado en conexiones WebSocket nativas, aislamiento ultra-ligero de contextos de incógnito paralelos y reportería automatizada integrada con Allure. *(Fase 1 - Completada)*

* **🌐 [Módulo Selenium WebDriver + Java](./selenium-java/README.md):** Framework corporativo robusto adaptado a Selenium 4, control de sincronización de SPA mediante barreras lógicas explícitas, inyección en el bucle de eventos del DOM y concurrencia multi-hilo nativa de Java con hilos coordinados. *(Fase 2 - Completada)*

* **⚡ [Módulo Cypress + JavaScript](./cypress-js/README.md):** Framework de ejecución *in-browser* diseñado para auditar la reactividad del DOM en tiempo real, utilizando intercepción de red nativa (`cy.intercept`) para la validación de contratos de API, aserciones estrictas de estado y generación forense de reportes HTML con Mochawesome. *(Fase 3 - Completada)*

---

## 🧠 Enfoque de Negocio e Impacto Estratégico

Este ecosistema no fue construido para validar clics superficiales; fue diseñado como una compuerta de calidad indestructible para mitigar los riesgos más críticos en plataformas de salud digital:

1. **Protección Contra Pérdida de Datos:** Asegurar que los componentes del frontend y las validaciones de limpieza de texto (*Trimming*) procesen datos limpios de manera infalible y respondan de forma segura ante eventos de hardware (ej. intercepción de la tecla Escape).
2. **Prevención de Falsos Positivos de UX:** Cazar bugs lógicos silenciosos donde la interfaz oculta fallos críticos del servidor (como errores HTTP 409) engañando al operador clínico.
3. **Simulación de Estrés Operativo Real:** Evaluar si el sistema soporta colisiones multi-usuario paralelas sobre los mismos recursos de agenda médica sin provocar *Overbooking*.

---

## 🚀 Instrucciones de Despliegue

Cada módulo cuenta con su propio entorno de ejecución. Te invito a navegar a las carpetas correspondientes (`playwright-java`, `selenium-java` o `cypress-js`) y leer su archivo `README.md` específico, donde encontrarás los comandos de instalación, ejecución interactiva (Modo UI) y despliegue en consola (Modo Headless) para la generación de reportes.

