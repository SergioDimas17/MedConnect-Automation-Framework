package tests;

import org.junit.jupiter.api.Test;
import pages.LoginPage;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class LoginTest extends BaseTest {

    @Test
    public void loginExitoso() {
        LoginPage loginPage = new LoginPage(page);

        //  Iniciar sesión con las credenciales
        loginPage.iniciarSesion("admin@medconnect.com", "Admin123");

        //  Aserción Senior: Validamos que el botón del Dashboard sea visible en pantalla
        assertThat(loginPage.obtenerBotonAgendarCita()).isVisible();
    }
}