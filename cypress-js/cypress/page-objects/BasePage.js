class BasePage {
  // Navegación base limpia (Cypress tomará la baseUrl de cypress.config.js automáticamente)
  visit(path = '/') {
    cy.visit(path); 
    this.waitForLoadingToDisappear();
  }

  // Sincronización implícita contra la carga inicial de React
  waitForLoadingToDisappear() {
    cy.get('body').should('be.visible');
  }

  // Utilidad global forense para reportes manuales intermedios
  takeManualEvidence(description) {
    cy.log(`📸 Evidencia: ${description}`);
    cy.screenshot(`manual-${description.toLowerCase().replace(/ /g, '-')}`);
  }
}

export default BasePage;