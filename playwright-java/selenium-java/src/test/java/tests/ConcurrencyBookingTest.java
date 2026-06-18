package tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.DashboardPage;
import pages.LoginPage;
import utils.EvidenceManager;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;

public class ConcurrencyBookingTest {

    @Test
    public void testSimultaneousBookingConflict() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(2);
        boolean[] modalAbiertoResultado = new boolean[2];

        // HILO 1: Operador Clínico Alfa
        Thread operador1 = new Thread(() -> {
            modalAbiertoResultado[0] = ejecutarFlujoOperador("Operador_Alfa", latch);
        });

        // HILO 2: Operador Clínico Beta
        Thread operador2 = new Thread(() -> {
            modalAbiertoResultado[1] = ejecutarFlujoOperador("Operador_Beta", latch);
        });

        operador1.start();
        operador2.start();

        operador1.join();
        operador2.join();

        System.out.println("-> Estado Modal Operador Alfa: " + (modalAbiertoResultado[0] ? "ABIERTO" : "CERRADO"));
        System.out.println("-> Estado Modal Operador Beta: " + (modalAbiertoResultado[1] ? "ABIERTO" : "CERRADO"));

        Assertions.assertTrue(modalAbiertoResultado[0] || modalAbiertoResultado[1],
                "¡BUG DE CONCURRENCIA! Ambos modales se cerraron silenciosamente. Ningún operador vio la alerta 409.");
    }

    private boolean ejecutarFlujoOperador(String nombreOperador, CountDownLatch latch) {
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        boolean modalSiguePresente = false;

        try {
            driver.get("https://v0-medconnect-booking-app.vercel.app");

            LoginPage login = new LoginPage(driver);
            login.enterEmail("admin@medconnect.com");
            login.enterPassword("Admin123");
            login.clickLogin();

            DashboardPage dashboard = new DashboardPage(driver);
            dashboard.clickNewAppointment();
            dashboard.enterPatientDetails(nombreOperador, nombreOperador.toLowerCase() + "@test.com", "999888777");

            dashboard.selectFirstSpecialty();
            dashboard.selectFirstDoctor();
            dashboard.selectFirstAvailableDate();
            dashboard.selectFirstAvailableTimeSlot();

            // Sincronización en la barrera
            latch.countDown();
            latch.await();

            // ¡FUEGO! Disparo del evento al unísono
            dashboard.clickConfirmAppointment();

            // Esperamos los 2 segundos clave para que el frontend renderice la respuesta final
            Thread.sleep(2000);

            // 📸 🎯 EL MOMENTO EXACTO: La UI ya se estabilizó. Capturamos la evidencia visual.
            EvidenceManager.capturarPantalla(driver, nombreOperador + "_Resultado_Concurrencia");

            // Evaluamos el estado lógico final para la aserción
            modalSiguePresente = dashboard.isModalPresent();

        } catch (Exception e) {
            System.err.println("Error en el hilo de " + nombreOperador + ": " + e.getMessage());
        } finally {
            driver.quit();
        }

        return modalSiguePresente;
    }
}