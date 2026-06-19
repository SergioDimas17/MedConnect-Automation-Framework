// Archivo: cypress/support/commands.js

Cypress.Commands.add('login', () => {
  cy.visit('/');
  
  // Se ingresa directamente ya que las cookies ya fueron limpiadas en el beforeEach
  cy.get("input[type='email']", { timeout: 10000 }).should('be.visible').type('admin@medconnect.com');
  cy.get("input[type='password']").type('Admin123');
  cy.get("button[type='submit']").click();
  
  // Asegura que el login terminó
  cy.contains('button', 'Nueva Cita', { timeout: 15000 }).should('be.visible');
});