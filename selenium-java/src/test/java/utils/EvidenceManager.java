package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EvidenceManager {

    /**
     * Captura la pantalla del navegador actual y la guarda en la carpeta target/evidencias
     *
     * @param driver        Instancia activa de WebDriver
     * @param nombreArchivo Prefijo descriptivo para identificar la captura
     */
    public static void capturarPantalla(WebDriver driver, String nombreArchivo) {
        if (driver instanceof TakesScreenshot) {
            // 1. Forzamos el casteo del driver a la interfaz de captura
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

            // 2. Generamos una marca de tiempo para evitar que las capturas se sobreescriban
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS").format(new Date());

            // 3. Definimos el directorio raíz de evidencias dentro del directorio 'target' de Maven
            File folderDestino = new File("target/evidencias");
            if (!folderDestino.exists()) {
                folderDestino.mkdirs(); // Crea la estructura de carpetas si no existe
            }

            // 4. Construimos el archivo final con formato PNG
            File destFile = new File(folderDestino, nombreArchivo + "_" + timestamp + ".png");

            try {
                // 5. Copiamos el archivo temporal generado por Selenium al destino definitivo
                Files.copy(srcFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                System.out.println("📸 Evidencia guardada con éxito: " + destFile.getAbsolutePath());
            } catch (IOException e) {
                System.err.println("❌ Error crítico al escribir la evidencia visual en disco: " + e.getMessage());
            }
        } else {
            System.err.println("⚠️ El Driver proporcionado no soporta capturas de pantalla.");
        }
    }
}