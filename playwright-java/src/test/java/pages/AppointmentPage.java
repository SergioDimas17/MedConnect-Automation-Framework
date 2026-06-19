package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class AppointmentPage {
    private Page page;

    // Localizadores del Formulario Estructural
    private String botonNuevaCita = "main button:has-text('Nueva Cita')";
    private String inputNombre = "input[placeholder*='María' i]";
    private String inputCorreo = "input[type='email'], input[placeholder*='correo' i]";
    private String inputTelefono = "input[type='tel'], input[placeholder*='teléfono' i], input[placeholder*='phone' i]";

    //  Localizadores de los Desplegables / Dropdowns Personalizados
    private String comboEspecialidad = "text=Seleccionar especialidad";
    private String comboDoctor = "text=Seleccionar doctor";
    private String comboFecha = "button[data-slot='dropdown-menu-trigger']:has(.lucide-calendar)";
    private String comboHora = "text=Seleccionar hora";

    private String opcionesDropdown = "[data-slot='dropdown-menu-item']";

    // Confirmación y Cierre
    private String botonConfirmarGuardado = "button:has-text('Agendar Cita'), button[type='submit']";
    private String primeraFilaTabla = "table tbody tr";
    private String modalCita = "div[role='dialog']";

    public AppointmentPage(Page page) {
        this.page = page;
    }

    //  ACCIONES SECUENCIALES DEL FLUJO
    public void abrirFormularioNuevaCita() {
        page.click(botonNuevaCita);
    }

    public void ingresarNombrePaciente(String nombre) {
        page.fill(inputNombre, nombre);
    }

    public void ingresarCorreo(String correo) {
        page.fill(inputCorreo, correo);
    }

    public void ingresarTelefono(String telefono) {
        page.fill(inputTelefono, telefono);
    }

    public void seleccionarEspecialidad(String especialidad) {
        page.click(comboEspecialidad);
        page.click("text=" + especialidad);
    }

    public void seleccionarDoctor(String doctor) {
        page.click(comboDoctor);
        page.click("text=" + doctor);
    }

    public void abrirDesplegableFecha() {
        page.click(comboFecha);
    }

    public void abrirDesplegableHora() {
        page.click(comboHora);
    }

    public void seleccionarPrimerElementoDisponible() {
        // Selecciona la primera opción activa del menú abierto (Sea fecha u hora)
        page.locator(opcionesDropdown).first().click();
    }

    public void confirmarGuardado() {
        page.click(botonConfirmarGuardado);
    }

    // LOCALIZADORES DE ASERCIÓN
    public Locator obtenerInputNombrePaciente() {
        return page.locator(inputNombre);
    }

    public Locator obtenerModalCita() {
        return page.locator(modalCita);
    }

    public Locator buscarPacienteEnTabla(String nombrePaciente) {
        // Localiza la fila específica de la tabla que contiene el nombre del paciente ingresado
        return page.locator("table tbody tr:has-text('" + nombrePaciente + "')");
    }

    public void seleccionarPrimerDoctorDisponible() {
        //  Abre el desplegable de doctores correspondiente a la especialidad elegida
        page.click(comboDoctor);

        // Hace clic en el primer médico de la lista dinámicamente renderizada
        page.locator(opcionesDropdown).first().click();
    }

    public com.microsoft.playwright.Locator obtenerMensajeErrorCampo() {
        return page.locator("text=requerido")
                .or(page.locator("text=Seleccione"))
                .first();

    }

}