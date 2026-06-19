package tests;

import org.junit.jupiter.api.Test;
import pages.AppointmentPage;
import pages.LoginPage;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class ResilienceTest extends BaseTest {

    @Test
    public void flujoE2E_02_A_ResilienciaYValidacionDeDatos() {
        LoginPage loginPage = new LoginPage(page);
        AppointmentPage appointmentPage = new AppointmentPage(page);

        // 🛑 CASO 1: Login tolerante a fallos de formato (EDP-2)
        loginPage.iniciarSesion("   ADMIN@MEDCONNECT.COM   ", "Admin123");

        // Apertura limpia del formulario
        appointmentPage.abrirFormularioNuevaCita();

        // 🛑 CASO 3: Validación de campos obligatorios ante envío vacío (EDP-5)
        appointmentPage.confirmarGuardado();
        assertThat(appointmentPage.obtenerMensajeErrorCampo()).isVisible();

        // 🛑 CASO 4: Normalización de Datos de Entrada (Trimming & Case-Insensitive)
        appointmentPage.ingresarNombrePaciente("   cArLoS pÉrEz   ");
        appointmentPage.ingresarCorreo("  carlos.perez@email.com  ");
        appointmentPage.ingresarTelefono("5551234567");

        appointmentPage.seleccionarEspecialidad("Cardiología");
        appointmentPage.seleccionarPrimerDoctorDisponible();
        appointmentPage.abrirDesplegableFecha();
        appointmentPage.seleccionarPrimerElementoDisponible();
        appointmentPage.abrirDesplegableHora();
        appointmentPage.seleccionarPrimerElementoDisponible();

        appointmentPage.confirmarGuardado();

        // 🛑 CASO 5: Mitigación del Doble Clic Rápido (Debounce / Double-Submit Protection)
        page.click("button:has-text('Agendar Cita'), button[type='submit']", new com.microsoft.playwright.Page.ClickOptions().setForce(true));

        // Aserción final: Verificación de persistencia correcta sin espacios
        assertThat(appointmentPage.buscarPacienteEnTabla("Carlos Pérez")).isVisible();
    }

    @Test
    public void flujoE2E_02_B_BugRegresion_CierreModalEscape() {
        LoginPage loginPage = new LoginPage(page);
        AppointmentPage appointmentPage = new AppointmentPage(page);

        loginPage.iniciarSesion("admin@medconnect.com", "Admin123");
        appointmentPage.abrirFormularioNuevaCita();

        // 🛑 CASO 2: Protección del Modal contra cierres accidentales (EDP-4: TC2)
        page.keyboard().press("Escape");

        // Esta aserción fallará de forma controlada registrando el Bug en el reporte
        assertThat(appointmentPage.obtenerModalCita()).isVisible();
    }
}