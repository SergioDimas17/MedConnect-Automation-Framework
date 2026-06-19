package tests;

import base.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import pages.DashboardPage;
import pages.LoginPage;

public class NegativeBookingTest extends BaseTest {

    @Test
    public void testEscapeKeyDoesNotCloseModal() {
        driver.get("https://v0-medconnect-booking-app.vercel.app");

        // Login
        LoginPage login = new LoginPage(driver);
        login.enterEmail("admin@medconnect.com");
        login.enterPassword("Admin123");
        login.clickLogin();

        DashboardPage dashboard = new DashboardPage(driver);
        dashboard.clickNewAppointment();

        // Acción: Simular presionar Escape
        new Actions(driver).sendKeys(Keys.ESCAPE).perform();

        // Auditoría: El modal DEBE seguir presente
        boolean isModalVisible = dashboard.isModalPresent();
        Assertions.assertTrue(isModalVisible, "¡Error! El modal se cerró incorrectamente al presionar Escape.");
    }


    @Test
    public void testSimultaneousBookingConflict() {
        driver.get("https://v0-medconnect-booking-app.vercel.app");

        //  Login
        LoginPage login = new LoginPage(driver);
        login.enterEmail("admin@medconnect.com");
        login.enterPassword("Admin123");
        login.clickLogin();

        //  Llenar formulario
        DashboardPage dashboard = new DashboardPage(driver);
        dashboard.clickNewAppointment();
        dashboard.enterPatientDetails("Conflicto Paciente", "conflicto@test.com", "123456789");
        dashboard.selectFirstSpecialty();
        dashboard.selectFirstDoctor();
        dashboard.selectFirstAvailableDate();
        dashboard.selectFirstAvailableTimeSlot();

        // Confirmar Cita
        dashboard.clickConfirmAppointment();

        // el modal DEBE quedarse abierto.
        boolean isModalPresent = dashboard.isModalPresent();

        // Si el modal desaparece, el test falla.
        Assertions.assertTrue(isModalPresent, "Bug de Concurrencia: El modal se cerró tras el intento de agendamiento.");
    }
}