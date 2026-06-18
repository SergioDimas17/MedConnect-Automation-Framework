import './commands';
import addContext from 'mochawesome/addContext';

// Escuchador global forense del Gobierno de Pruebas
Cypress.on('test:after:run', (test, runnable) => {
  if (test.state === 'failed') {
    // Se construye la ruta relativa exacta donde Cypress guarda la captura del fallo
    const specName = Cypress.spec.name;
    const parentTitle = runnable.parent.title;
    const testTitle = test.title;
    
    // Formato nativo de Cypress: "NombreSpec.cy.js/Nombre Suite -- Nombre Test (failed).png"
    const screenshotPath = `../screenshots/${specName}/${parentTitle} -- ${testTitle} (failed).png`;
    
    // Se inyecta la ruta de la imagen directamente en el contexto de Mochawesome
    addContext({ test }, screenshotPath);
  }
});