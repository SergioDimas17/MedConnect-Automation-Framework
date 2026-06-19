const { defineConfig } = require("cypress");

module.exports = defineConfig({
  e2e: {
    baseUrl: 'https://v0-medconnect-booking-app.vercel.app/',
    setupNodeEvents(on, config) {
      // Configuración de eventos lógicos
    },
    
   
    screenshotOnRunFailure: true,
    video: true,
    trashAssetsBeforeRuns: true,
    screenshotsFolder: 'cypress/screenshots',
    videosFolder: 'cypress/videos',

    reporter: 'mochawesome',
    reporterOptions: {
      reportDir: 'cypress/reports/mocha', // Carpeta temporal para los archivos crudos
      overwrite: false,
      html: false, // Desactivamos el HTML individual para no saturar de archivos el proyecto
      json: true   // Activamos el JSON para poder fusionarlos al final
    }
  },
});