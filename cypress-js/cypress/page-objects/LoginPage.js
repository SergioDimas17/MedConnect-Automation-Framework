import BasePage from './BasePage';

class LoginPage extends BasePage {
  // Selector Elements
  get emailInput() { return cy.get('input[type="email"]'); }
  get passwordInput() { return cy.get('input[type="password"]'); }
  get submitButton() { return cy.get('button[type="submit"]'); }

  // Navegación a la plataforma
  visitLogin() {
    // Al enviar '/' le decimos a BasePage que vaya exactamente a la raíz de la baseUrl
    this.visit('/'); 
  }

  // Flujo semántico de autenticación
  login(email, password) {
    this.emailInput.type(email);
    this.passwordInput.type(password);
    this.submitButton.click();
  }
}

export default new LoginPage();