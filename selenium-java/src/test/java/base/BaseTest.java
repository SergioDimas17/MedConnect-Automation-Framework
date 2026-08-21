package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import utils.EvidenceManager;

import java.time.Duration;

public class BaseTest {
    protected WebDriver driver;
    protected String baseUrl = "https://v0-medconnect-booking-app.vercel.app";

    @BeforeEach
    public void setUp() {
        // WebDriverManager descarga automáticamente ChromeDriver compatible
        WebDriverManager.chromedriver().setup();
        
        ChromeOptions options = new ChromeOptions();
        
        // Detecta si está en CI (GitHub Actions)
        boolean isCI = System.getenv("CI") != null;
        
        if (isCI) {
            // Configuración para entornos CI/CD sin interfaz gráfica
            options.addArguments("--headless");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-gpu");
            options.addArguments("--disable-web-resources");
        } else {
            // Modo normal para desarrollo local
            options.addArguments("--disable-blink-features=AutomationControlled");
        }
        
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.navigate().to(baseUrl);
    }

    @AfterEach
    public void tearDown(TestInfo testInfo) {
        if (driver != null) {
            try {
                String nombreMetodo = testInfo.getTestMethod().isPresent()
                        ? testInfo.getTestMethod().get().getName()
                        : "Test_Desconocido";

                EvidenceManager.capturarPantalla(driver, "Fin_Ciclo_" + nombreMetodo);

            } catch (Exception e) {
                System.err.println("❌ Falló la recolección automática de evidencias: " + e.getMessage());
            } finally {
                driver.quit();
            }
        }
    }
}