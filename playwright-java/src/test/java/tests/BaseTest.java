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
        // 1. Lee tu configuración local como lo construimos en el Paso 38
        boolean modoHeadlessLocal = Boolean.parseBoolean(propiedades.getProperty("browser.headless"));

        // 2. Detecta automáticamente si el código está corriendo en la nube de GitHub
        boolean isCI = System.getenv("CI") != null;

        // 3. Lógica maestra: Si está en la nube, obligar a true. Si es local, respeta tu properties.
        boolean modoHeadlessFinal = isCI || modoHeadlessLocal;

        // 4. Configuración robusta para entornos Linux sin interfaz
        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions()
                .setHeadless(modoHeadlessFinal)
                .setArgs(Arrays.asList(
                        "--disable-gpu",
                        "--no-sandbox",
                        "--disable-dev-shm-usage"
                ));

        browser = playwright.chromium().launch(launchOptions);
        context = browser.newContext();
        page = context.newPage();
    }

    @AfterEach
    public void tearDown() {
        if (context != null) context.close();
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }

    //  Toma captura de pantalla automática SOLO si el test falla
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