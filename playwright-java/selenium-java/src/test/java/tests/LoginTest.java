package tests;

import base.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pages.LoginPage;

public class LoginTest extends BaseTest {

    @Test
    public void testSuccessfulLogin() {
        driver.get("https://v0-medconnect-booking-app.vercel.app/");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail("admin@medconnect.com");
        loginPage.enterPassword("Admin123");
        loginPage.clickLogin();

        String urlActual = driver.getCurrentUrl();

        Assertions.assertTrue(urlActual.contains("v0-medconnect-booking-app.vercel.app"),
                "El inicio de sesión no mantuvo al usuario en el entorno correcto. URL encontrada: " + urlActual);
    }
}