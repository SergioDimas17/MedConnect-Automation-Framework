Markdown
# 🏥 MedConnect Automation Ecosystem: Suite Omni-Framework

[![E2E Pipeline](https://github.com/SergioDimas17/MedConnect-Automation-Framework/actions/workflows/e2e-tests.yml/badge.svg)](https://github.com/SergioDimas17/MedConnect-Automation-Framework/actions)
![Java](https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=java)
![JavaScript](https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black)
![Maven](https://img.shields.io/badge/Maven-3.9+-blue?style=for-the-badge&logo=apache-maven)
![NodeJS](https://img.shields.io/badge/Node.js-20+-339933?style=for-the-badge&logo=nodedotjs&logoColor=white)
![Selenium](https://img.shields.io/badge/Selenium-v4+-red?style=for-the-badge&logo=selenium)
![Playwright](https://img.shields.io/badge/Playwright-v1.49+-green?style=for-the-badge&logo=playwright)
![Cypress](https://img.shields.io/badge/Cypress-v13+-17202C?style=for-the-badge&logo=cypress&logoColor=white)

¡Bienvenido a mi ecosistema avanzado de **QA Automation**! Este repositorio modular tipo *Monorepo* centraliza la estrategia de pruebas de punta a punta (E2E) para **MedConnect**, una plataforma web moderna de agendamiento de citas médicas estructurada como una *Single Page Application* (SPA) basada en React y Radix UI.

La particularidad estratégica de este ecosistema es que replica exactamente los mismos flujos críticos de negocio bajo las tres herramientas líderes del mercado: **Playwright, Selenium WebDriver y Cypress**. Esto me permite evaluar y comparar de forma científica la velocidad de ejecución, la estabilidad de los selectores, los flujos de sincronización asíncrona y el consumo de infraestructura de cada solución bajo un patrón de arquitectura común y un flujo de Integración Continua (CI/CD) completamente automatizado.

---

## 🏛️ Arquitectura Global del Monorepo

El repositorio está organizado como un *Hub* centralizado donde cada subproyecto opera de forma independiente con sus propias dependencias, runtime y configuraciones de compilación:

```text
MedConnect-Automation-Framework/
├── .github/
│   └── workflows/
│       └── e2e-tests.yml        # Pipeline Unificado de CI/CD (GitHub Actions)
├── playwright-java/             # Suite E2E & Concurrencia con Playwright + Java 17
├── selenium-java/               # Suite E2E & Resiliencia con Selenium WebDriver 4 + Java 17
├── cypress-js/                  # Suite E2E & Reportabilidad con Cypress + JavaScript
├── .gitignore                   # Exclusiones de Git a nivel Monorepo
└── README.md                    # Hub de Arquitectura Global
📂 Estructura y Navegación de los Módulos
Cada suite cuenta con su propia documentación técnica interna y bitácora de arquitectura:

🎭 Módulo Playwright + Java: Framework de alta velocidad enfocado en conexiones WebSocket nativas, aislamiento ultra-ligero de contextos de incógnito paralelos (BrowserContext), detección dinámica del entorno CI para ejecución Headless y reportería automatizada integrada con Allure Reports.

🌐 Módulo Selenium WebDriver + Java: Framework corporativo adaptado a Selenium 4 con WebDriverManager, control de sincronización de SPA mediante barreras lógicas explícitas (WebDriverWait) y estrategias de resiliencia frente a eventos nativos del DOM.

⚡ Módulo Cypress + JavaScript: Framework de ejecución in-browser diseñado para auditar la reactividad del DOM en tiempo real, intercepción de red nativa (cy.intercept) para auditoría de contratos de API (HTTP 409 Conflict) y generación forense de reportes HTML autónomos con Mochawesome.

⚙️ Integración Continua (CI/CD Pipeline con GitHub Actions)
La suite cuenta con una compuerta de calidad automatizada que se ejecuta en servidores virtuales de Linux de GitHub Actions ante cualquier evento de push o pull_request hacia las ramas principales (main o master).

🔄 Orquestación del Workflow (e2e-tests.yml)
El pipeline está dividido en jobs independientes que desacoplan los entornos de ejecución para JavaScript y Java:

Job cypress-tests:

Prepara un runner en ubuntu-latest.

Configura el entorno de Node.js 20.

Ejecuta la instalación limpia de paquetes (npm ci) dentro del directorio ./cypress-js.

Lanza la suite en modo invisible (headless) compilando los resultados de Mochawesome.

Job java-tests:

Prepara un runner paralelo en ubuntu-latest.

Configura el entorno JDK 17 (Temurin) con caché nativo para Apache Maven.

Ejecuta secuencialmente las suites de playwright-java y selenium-java invocando mvn clean test.

🛡️ Manejo Dinámico de Entorno y Datos Sensibles
Variables de Entorno y Secrets: El pipeline inyecta dinámicamente las credenciales y URLs mediante GitHub Secrets (secrets.BASE_URL, secrets.ADMIN_USER, secrets.ADMIN_PASSWORD), manteniendo respaldos por defecto (fallbacks) para la ejecución local.

Inteligencia de Entorno (Headless en CI): En Playwright Java, la arquitectura detecta automáticamente si el código se ejecuta dentro del entorno de integración continua inspeccionando la variable System.getenv("CI"). Esto fuerza la activación del modo headless con flags avanzadas de Chromium (--no-sandbox, --disable-gpu, --disable-dev-shm-usage), evitando fallos por falta de servidor gráfico ($DISPLAY / X11).

🧠 Patrones de Ingeniería y Buenas Prácticas Applied
Automatización de Login Independiente: Aislamiento total de la autenticación. Encapsulado mediante Custom Commands (cy.login()) en Cypress y mediante métodos reutilizables en el LoginPage para Playwright y Selenium.

Page Object Model (POM) Puro: Separación estricta de responsabilidades:

Page Objects: Mapean localizadores y orquestan acciones sobre el DOM. No contienen aserciones.

Test Classes (*.cy.js / *Test.java): Contienen exclusivamente la lógica de negocio y las aserciones de prueba (assertThat, should, assertEquals).

Control de Concurrencia y Resiliencia (Race Conditions): Evaluación de condiciones de carrera en el frontend/backend simulando colisiones de agendamiento sobre un mismo slot horario (validando la respuesta HTTP 409 Conflict y la no persistencia del dato duplicado en UI).

🚀 Instrucciones de Ejecución de Pruebas
1. Ejecución Automatizada en la Nube (CI/CD)
No se requiere interacción manual. Al subir cualquier cambio al repositorio:

Bash
git add .
git commit -m "ci: actualiza tests del ecosistema e2e"
git push origin main
Puedes monitorear el progreso, logs de consola y estados en la pestaña Actions de tu repositorio en GitHub.

2. Ejecución Local (Paso a Paso)
Clona el repositorio e ingresa a la carpeta raíz:

Bash
git clone [https://github.com/SergioDimas17/MedConnect-Automation-Framework.git](https://github.com/SergioDimas17/MedConnect-Automation-Framework.git)
cd MedConnect-Automation-Framework
🟢 Módulo 1: Cypress (JavaScript)
Bash
cd cypress-js
npm install

# Modo Interactivo (UI Runner)
npx cypress open

# Modo Headless + Generación de Reporte Mochawesome
npm run test:regression
Los reportes HTML generados quedarán disponibles en cypress-js/cypress/reports/report.html.

☕ Módulo 2: Playwright (Java 17)
Bash
cd playwright-java

# Ejecución de la suite completa con Maven
mvn clean test

# Compilación y apertura del Dashboard de Allure (Opcional)
mvn allure:report
mvn allure:serve
🌐 Módulo 3: Selenium WebDriver (Java 17)
Bash
cd selenium-java

# Ejecución de la suite de resiliencia con Maven
mvn clean test
📊 Gobierno de Pruebas y Evidencias Visuales
Playwright Java: Registra automáticamente trazas, grabaciones en video de las sesiones de prueba y screenshots de página completa (setFullPage(true)) adjuntadas al ciclo de vida de JUnit 5.

Selenium Java: Capturas de pantalla inmutables con estampado de tiempo milimétrico ante fallos almacenadas en selenium-java/target/evidencias/.

Cypress JS: Generación de archivos JSON de ejecución unificados en un único Dashboard HTML interactivo con mochawesome-merge e inlineAssets habilitados para fácil auditoría.