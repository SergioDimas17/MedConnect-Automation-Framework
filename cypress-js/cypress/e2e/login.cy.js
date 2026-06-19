import LoginPage from '../page-objects/LoginPage';

describe('US-01 & US-02: Portal de Autenticación - MedConnect (POM Architecture)', () => {
  
  beforeEach(() => {
    // Inicialización limpia en cada caso de prueba
    LoginPage.visitLogin();
  });

  it('EDP-2: Debería iniciar sesión exitosamente con credenciales administrativas', () => {
    // Ejecución del Camino Feliz (Happy Path) del Flujo E2E 01
    LoginPage.login('admin@medconnect.com', 'Admin123');

    //Se valida el despliegue del Dashboard confirmando la presencia del botón core
    cy.contains('button', 'Nueva Cita').should('be.visible');
  });

  it('EDP-3: Debería desplegar alerta restrictiva ante credenciales inválidas', () => {
    // Ejecución del Camino Infeliz (Negative Path)
    LoginPage.login('fake@medconnect.com', 'WrongPassword123');

    // Se Comprueba el manejo de errores visuales en la UX de MedConnect
    cy.get('.bg-destructive, .text-destructive, [role="alert"]')
      .should('be.visible');
  });
});