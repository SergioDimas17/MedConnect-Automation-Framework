package tests;

import base.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pages.DashboardPage;
import pages.LoginPage;

public class BookingTest extends BaseTest {

    @Test
    public void testSuccessfulAppointmentBooking() {
        driver.get("https://v0-medconnect-booking-app.vercel.app");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail("admin@medconnect.com");
        loginPage.enterPassword("Admin123");
        loginPage.clickLogin();

        DashboardPage dashboardPage = new DashboardPage(driver);
        dashboardPage.clickNewAppointment();

        dashboardPage.enterPatientDetails("María García López", "maria@ejemplo.com", "+34 612 345 678");
        dashboardPage.selectFirstSpecialty();
        dashboardPage.selectFirstDoctor();
        dashboardPage.selectFirstAvailableDate();
        dashboardPage.selectFirstAvailableTimeSlot();
        dashboardPage.clickConfirmAppointment();

        // Se verifica que la cita existe en la tabla
        boolean citaCreada = dashboardPage.isAppointmentVisible("María García López");

        Assertions.assertTrue(citaCreada, "La cita no aparece en el Dashboard después de confirmar.");
    }
}