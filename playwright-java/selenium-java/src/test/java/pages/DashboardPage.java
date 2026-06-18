package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class DashboardPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Localizadores existentes
    private By bookAppointmentButton = By.xpath("//button[contains(text(), 'Nueva Cita')]");
    private By patientNameField = By.cssSelector("input[placeholder*='María']");
    private By patientEmailField = By.cssSelector("input[type='email']");
    private By patientPhoneField = By.cssSelector("input[type='tel']");
    private By specialtyDropdown = By.xpath("//button[.//span[contains(text(), 'especialidad')]]");
    private By doctorDropdown = By.xpath("//button[.//span[contains(text(), 'doctor')]]");
    private By dateDropdown = By.xpath("//button[.//span[contains(text(), 'fecha')]]");
    private By timeDropdown = By.xpath("//button[.//span[contains(text(), 'hora')]]");
    private By menuItems = By.cssSelector("div[role='menuitem']");
    private By confirmButton = By.xpath("//button[@type='submit' and contains(., 'Agendar Cita')]");

    // Nuevo selector para verificar la tabla (asumiendo que los datos del paciente aparecen en una fila)
    // Buscamos un elemento que contenga el nombre del paciente en el cuerpo de la página
    private String rowLocator = "//div[contains(text(), '%s') or contains(., '%s')]";

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public void clickNewAppointment() {
        wait.until(ExpectedConditions.elementToBeClickable(bookAppointmentButton)).click();
    }

    public void enterPatientDetails(String name, String email, String phone) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(patientNameField)).sendKeys(name);
        driver.findElement(patientEmailField).sendKeys(email);
        driver.findElement(patientPhoneField).sendKeys(phone);
    }

    private void selectFirstOption(By dropdownLocator) {
        wait.until(ExpectedConditions.elementToBeClickable(dropdownLocator)).click();
        List<WebElement> options = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(menuItems));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", options.get(0));
    }

    public void selectFirstSpecialty() {
        selectFirstOption(specialtyDropdown);
    }

    public void selectFirstDoctor() {
        selectFirstOption(doctorDropdown);
    }

    public void selectFirstAvailableDate() {
        selectFirstOption(dateDropdown);
    }

    public void selectFirstAvailableTimeSlot() {
        selectFirstOption(timeDropdown);
    }

    public void clickConfirmAppointment() {
        WebElement btn = wait.until(ExpectedConditions.presenceOfElementLocated(confirmButton));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", btn);
    }

    // 🎯 NUEVO MÉTODO DE VALIDACIÓN: Busca el nombre en la tabla/dashboard
    public boolean isAppointmentVisible(String patientName) {
        try {
            // Espera a que aparezca un elemento que contenga el nombre del paciente
            By patientRow = By.xpath(String.format(rowLocator, patientName, patientName));
            return wait.until(ExpectedConditions.visibilityOfElementLocated(patientRow)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isModalPresent() {
        // Buscamos si existe al menos un elemento con role='dialog'
        List<WebElement> modals = driver.findElements(By.xpath("//div[@role='dialog']"));

        // Si la lista tiene elementos, verificamos si está visible
        if (!modals.isEmpty()) {
            return modals.get(0).isDisplayed();
        }
        return false;
    }
}