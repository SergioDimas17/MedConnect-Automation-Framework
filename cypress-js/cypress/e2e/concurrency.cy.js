// Archivo: cypress/e2e/concurrency.cy.js

// Ignorar excepciones internas de React/Vercel que hacen crashear a Cypress
Cypress.on('uncaught:exception', (err, runnable) => {
  return false;
});

describe('Prueba de Reactividad UI y UX - MedConnect', () => {
  beforeEach(() => {
    // Se limpia todo rastro de sesiones anteriores
    cy.clearAllCookies();
    cy.clearAllLocalStorage();
    cy.clearAllSessionStorage();
    
    // Autenticación segura unificada
    cy.login();
  });

  it('EDP-7: Camino A - UX: El frontend debe deshabilitar el horario reservado por el Operador A', () => {
    
    const opciones = '[role="option"], [data-radix-collection-item], [data-cmdk-item]';

    // Se agenda la cita del operador A
    cy.contains('button', 'Nueva Cita').click();
    cy.get('input[placeholder*="María"]').type('Carlos Gómez');
    cy.get('input[type="email"]').type('carlos.gomez@test.com');
    cy.get('input[type="tel"]').type('3000000000');

    cy.contains('button', 'Seleccionar especialidad').click();
    cy.get(opciones).first().click();

    cy.contains('button', 'Seleccionar doctor').should('not.be.disabled').click();
    cy.get(opciones).first().click();

    cy.contains('button', 'Seleccionar fecha').should('not.be.disabled').click();
    cy.get(opciones).first().click();

    cy.contains('button', 'Seleccionar hora').should('not.be.disabled').click();
    cy.get(opciones).first().click(); // Operador A toma la primera hora disponible
    
    cy.contains('button', 'Agendar Cita').click();
    cy.contains('Carlos Gómez', { timeout: 10000 }).should('be.visible');

//  El operador B agenda el mismo horario
cy.contains('button', 'Nueva Cita').click();
cy.get('input[placeholder*="María"]').type('Paciente Observador');
cy.get('input[type="email"]').type('observador@test.com');
cy.get('input[type="tel"]').type('3009999999');

// Operador B recorre el mismo camino exacto
cy.contains('button', 'Seleccionar especialidad').click();
cy.get(opciones).first().click();

cy.contains('button', 'Seleccionar doctor').should('not.be.disabled').click();
cy.get(opciones).first().click();

cy.contains('button', 'Seleccionar fecha').should('not.be.disabled').click();
cy.get(opciones).first().click();

// El robot verifica el bloqueo
// Abrimos el menú de horas
cy.contains('button', 'Seleccionar hora').should('not.be.disabled').click();

//Se valida que el atributo indique que está desactivado.
// Radix UI usa 'aria-disabled' o 'data-disabled' para bloquear elementos.
cy.get(opciones).first()
  .should('exist') 
  .and('have.attr', 'aria-disabled', 'true'); 

cy.log('✅ Éxito: La aplicación cumplió su diseño y desactivó la hora correctamente.');
  });
});