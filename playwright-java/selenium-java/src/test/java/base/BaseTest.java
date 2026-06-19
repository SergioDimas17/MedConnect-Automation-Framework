package base;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import utils.EvidenceManager;

import java.time.Duration;

public class BaseTest {
    protected WebDriver driver;

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
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