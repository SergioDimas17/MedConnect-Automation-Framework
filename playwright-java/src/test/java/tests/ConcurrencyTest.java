package tests;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import pages.AppointmentPage;
import pages.LoginPage;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class ConcurrencyTest extends BaseTest {

    @Test
    @Disabled("JIRA-MED-03: Desactivado temporalmente hasta que el equipo de desarrollo corrija el cierre abrupto del modal en colisiones")
    public void flujoE2E_03_ControlDeConcurrenciaMultiUsuario() {
        // CONFIGURACIÓN DE OPERADORES
        LoginPage loginPage1 = new LoginPage(page);
        AppointmentPage appointmentPage1 = new AppointmentPage(page);

        BrowserContext context2 = browser.newContext();
        Page page2 = context2.newPage();
        LoginPage loginPage2 = new LoginPage(page2);
        AppointmentPage appointmentPage2 = new AppointmentPage(page2);

        //  INICIO DE SESIÓN SIMULTÁNEO
        loginPage1.iniciarSesion("admin@medconnect.com", "Admin123");

        page2.navigate(baseUrl);
        loginPage2.iniciarSesion("admin@medconnect.com", "Admin123");

        // APERTURA DE MODALES
        appointmentPage1.abrirFormularioNuevaCita();
        appointmentPage2.abrirFormularioNuevaCita();

        // OPERADOR 1 PREPARA SU CITA
        appointmentPage1.ingresarNombrePaciente("Paciente Concurrente Uno");
        appointmentPage1.ingresarCorreo("operador1@email.com");
        appointmentPage1.ingresarTelefono("5559998881");
        appointmentPage1.seleccionarEspecialidad("Cardiología");
        appointmentPage1.seleccionarPrimerDoctorDisponible();
        appointmentPage1.abrirDesplegableFecha();
        appointmentPage1.seleccionarPrimerElementoDisponible();
        appointmentPage1.abrirDesplegableHora();
        appointmentPage1.seleccionarPrimerElementoDisponible();

        //  OPERADOR 2 SELECCIONA EL MISMO HORARIO CRÍTICO
        appointmentPage2.ingresarNombrePaciente("Paciente Concurrente Dos");
        appointmentPage2.ingresarCorreo("operador2@email.com");
        appointmentPage2.ingresarTelefono("5559998882");
        appointmentPage2.seleccionarEspecialidad("Cardiología");
        // Selecciona exactamente los mismos elementos para forzar la colisión
        appointmentPage2.seleccionarPrimerDoctorDisponible();
        appointmentPage2.abrirDesplegableFecha();
        appointmentPage2.seleccionarPrimerElementoDisponible();
        appointmentPage2.abrirDesplegableHora();
        appointmentPage2.seleccionarPrimerElementoDisponible();

        //  LA CARRERA DE REQUISICIONES
        // El Operador 1 consolida primero su cita
        appointmentPage1.confirmarGuardado();

        // El Operador 2 intenta procesar inmediatamente después
        appointmentPage2.confirmarGuardado();

        //  ASERCIONES DE CONCURRENCIA
        //  Verificamos que el Operador 1 ve a su paciente exitosamente en la tabla
        assertThat(appointmentPage1.buscarPacienteEnTabla("Paciente Concurrente Uno")).isVisible();

        //  Verificamos que el Operador 2 recibe un mensaje restrictivo de "Horario ya reservado"
        // o que el modal de la segunda página NO se cerró debido al bloqueo por Overbooking
        assertThat(appointmentPage2.obtenerModalCita()).isVisible();

        // Limpieza de hilos de memoria
        context2.close();
    }
}