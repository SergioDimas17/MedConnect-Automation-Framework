package pages;

import com.microsoft.playwright.Page;

public class LoginPage {

    private Page page;

    // 📍 Selectores de inputs estándar
    private String inputCorreo = "input[type='email']";
    private String inputContrasena = "input[type='password']";

    // 🔑 SOLUCIÓN 1: Si el botón no tiene type='submit', lo clickeamos buscando su texto exacto
    private String botonIniciarSesion = "button:has-text('Iniciar Sesión')";

    // 🔑 SOLUCIÓN 2: Agregamos el prefijo 'text=' indispensable para que Playwright no busque un tag CSS
    private String botonAgendarCita = "main button:has-text('Nueva Cita')";
    
    public LoginPage(Page page) {
        this.page = page;
    }

    public void iniciarSesion(String correo, String password) {
        page.fill(inputCorreo, correo);
        page.fill(inputContrasena, password);
        page.click(botonIniciarSesion); // Ejecuta el clic robusto
    }

    public com.microsoft.playwright.Locator obtenerBotonAgendarCita() {
        return page.locator(botonAgendarCita); // Retorna el localizador corregido
    }
}