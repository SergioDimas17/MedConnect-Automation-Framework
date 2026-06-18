package tests;

import org.junit.jupiter.api.Test;
import pages.AppointmentPage;
import pages.LoginPage;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class AppointmentTest extends BaseTest {

    @Test
    public void flujoE2E_01_CicloCompletoAgendamientoExitoso() {
        LoginPage loginPage = new LoginPage(page);
        AppointmentPage appointmentPage = new AppointmentPage(page);

        // 1. AUTENTICACIÓN: Acceso seguro al sistema (EDP-2: TC1)
        loginPage.iniciarSesion("admin@medconnect.com", "Admin123");

        // 2. APERTURA: Despliegue del formulario (EDP-4: TC1)
        appointmentPage.abrirFormularioNuevaCita();
        assertThat(appointmentPage.obtenerInputNombrePaciente()).isVisible();

        // 3. SECUENCIA OBLIGATORIA DE ENTRADA DE DATOS (Manejo de Estados Dependientes)
        appointmentPage.ingresarNombrePaciente("Carlos Pérez");
        appointmentPage.ingresarCorreo("carlos.perez@email.com");
        appointmentPage.ingresarTelefono("5551234567");

        appointmentPage.seleccionarEspecialidad("Cardiología");
        appointmentPage.seleccionarPrimerDoctorDisponible(); // Seleccionamos el doctor disponible

        // Sincronización de Fecha (abrir + seleccionar)
        appointmentPage.abrirDesplegableFecha();
        appointmentPage.seleccionarPrimerElementoDisponible();

        // Sincronización de Hora (abrir + seleccionar)
        appointmentPage.abrirDesplegableHora();
        appointmentPage.seleccionarPrimerElementoDisponible();

        // 4. CAPA DE RENDIMIENTO (NF): Captura de estampa de tiempo Unix inicial
        long tiempoInicio = System.currentTimeMillis();

        // 5. ACCIÓN CRÍTICA: Confirmar el guardado de la cita médica (EDP-5: TC1)
        appointmentPage.confirmarGuardado();

        // 6. ASERCIONES E2E DE CONTROL DE CALIDAD: Cierre del modal y verificación en tabla Dashboard
        assertThat(appointmentPage.obtenerModalCita()).isHidden();
        assertThat(appointmentPage.buscarPacienteEnTabla("Carlos Pérez")).isVisible();

        // 7. CÁLCULO DE PERFORMANCE: Validación de renderizado menor a 4 segundos (4000 ms)
        long tiempoFin = System.currentTimeMillis();
        long tiempoTotalProcesamiento = tiempoFin - tiempoInicio;

        System.out.println("\n⏱️ [PERFORMANCE REPORT] Tiempo de renderizado y persistencia de UI: " + tiempoTotalProcesamiento + " ms\n");
        assert tiempoTotalProcesamiento < 4000 : "Error de Regresión: El guardado y renderizado visual de la cita tardó más de 2000 ms.";
    }
}