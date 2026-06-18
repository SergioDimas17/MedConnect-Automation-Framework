package tests;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.RegisterExtension;

import java.io.ByteArrayInputStream;
import java.nio.file.Paths;

public class BaseTest {
    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext context;
    protected Page page;
    protected String baseUrl = "https://v0-medconnect-booking-app.vercel.app";

    @BeforeEach
    public void setUp() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));

        // 🎥 Grabación de video automática para todas las pruebas
        context = browser.newContext(new Browser.NewContextOptions()
                .setRecordVideoDir(Paths.get("target/allure-results/videos/")));

        page = context.newPage();
        page.navigate(baseUrl);
    }

    @AfterEach
    public void tearDown() {
        if (context != null) context.close();
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }

    // 🌟 INTERCEPTOR DE LISTENER: Toma captura de pantalla automática SOLO si el test falla
    @RegisterExtension
    AfterTestExecutionCallback visualEvidenceGuard = new AfterTestExecutionCallback() {
        @Override
        public void afterTestExecution(ExtensionContext context) throws Exception {
            // Verificamos si la prueba terminó con una excepción (Fallo)
            if (context.getExecutionException().isPresent()) {
                if (page != null && !page.isClosed()) {
                    // Capturamos la pantalla completa en formato de bytes
                    byte[] screenshot = page.screenshot(new Page.ScreenshotOptions().setFullPage(true));
                    // Adjuntamos la foto directamente al reporte de Allure
                    Allure.addAttachment("Evidencia_Fallo_" + context.getRequiredTestMethod().getName(),
                            new ByteArrayInputStream(screenshot));
                }
            }
        }
    };
}