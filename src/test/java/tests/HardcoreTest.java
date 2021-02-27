package tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.Iterator;
import java.util.Set;

public class HardcoreTest extends CommonConditions {
    @Test
    public void googleCloud() throws InterruptedException {
        //1. Открыть https://cloud.google.com/
        driver.get("https://cloud.google.com/");
        //2. Нажав кнопку поиска по порталу вверху страницы, ввести в поле поиска"Google Cloud Platform Pricing Calculator"
        driver.findElement(By.name("q")).sendKeys("Google Cloud Platform Pricing Calculator");
        //3. Запустить поиск, нажав кнопку поиска. - Кнопка поиска не работает при введенном значении, реализовал через клавишу ввода.
        driver.findElement(By.name("q")).sendKeys(Keys.ENTER);
        //4. В результатах поиска кликнуть "Google Cloud Platform Pricing Calculator" и перейти на страницу калькулятора.
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//b[text()='Google Cloud Platform Pricing Calculator']/parent::a"))).click();
        //5. Активировать раздел COMPUTE ENGINE вверху страницы
        String iframeName = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("iframe[src*='/products/calculator/']"))).getAttribute("name");
        driver.switchTo().frame(iframeName).switchTo().frame("myFrame");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div[class='tab-holder compute']"))).click();
        // 6. Заполнить форму следующими данными:
        //* Number of instances: 4
        driver.findElement(By.xpath("//input[@name='quantity' and contains(@ng-model, 'quantity')]")).sendKeys("4");

        //  * What are these instances for?: оставить пустым
        //  * Operating System / Software: Free: Debian, CentOS, CoreOS, Ubuntu, or other User Provided OS - Установлено по умолчанию
        //   * VM Class: Regular - Установлено по умолчанию
        //Элементы на странице имеют id, но они периодически изменяются, поэтому реализованы другие типы локаторов

        //* Instance type: n1-standard-8 (vCPUs: 8, RAM: 30 GB)
        driver.findElement(By.xpath("//label[text()='Series']/following-sibling::md-select")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("md-option[value='n1']"))).click();
        driver.findElement(By.xpath("//label[text()='Machine type']/following-sibling::md-select")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("md-option[value='CP-COMPUTEENGINE-VMIMAGE-N1-STANDARD-8']"))).click();
        //* Выбрать Add GPUs
        driver.findElement(By.xpath("//div[contains(text(), 'Add GPUs.')]/preceding-sibling::div")).click();
        //* Number of GPUs: 1
        driver.findElement(By.xpath("//label[text()='Number of GPUs']/following-sibling::md-select")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//md-option[@value='1' and starts-with(@ng-disabled, 'item.value')]"))).click();
        //* GPU type: NVIDIA Tesla V100
        driver.findElement(By.xpath("//label[text()='GPU type']/following-sibling::md-select")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("md-option[value='NVIDIA_TESLA_V100']"))).click();
        // * Local SSD: 2x375 Gb
        driver.findElement(By.xpath("//label[text()='Local SSD']/following-sibling::md-select")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//md-option[@value='2' and @ng-repeat='item in listingCtrl.supportedSsd']"))).click();
        // * Datacenter location: Frankfurt (europe-west3)
        driver.findElement(By.xpath("//label[text()='Datacenter location']/following-sibling::md-select")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@class, 'clickable')]//md-option[@value='europe-west3']"))).click();
        //* Commited usage: 1 Year
        driver.findElement(By.xpath("//label[text()='Committed usage']/following-sibling::md-select")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@class, 'clickable')]//md-option[@value='1']"))).click();
        //7. Нажать Add to Estimate
        //!!! В приложении баг? При нажатии на данную кнопку значения полей изменяется.
        driver.findElement(By.cssSelector("button[class*='md-raised']")).click();
        String cost = driver.findElement(By.xpath("//b[contains(text(), 'Total Estimated Cost')]")).getText();
        int indexPer = cost.indexOf("per");
        int indexUsd = cost.indexOf("USD");
        cost = cost.substring(indexUsd + 4, indexPer - 1);
        //8. Выбрать пункт EMAIL ESTIMATE
        driver.findElement(By.id("email_quote")).click();
        //9. В новой вкладке открыть https://10minutemail.com или аналогичный сервис для генерации временных email'ов
        String mainWindow = driver.getWindowHandle();
        Set<String> oldWindows = driver.getWindowHandles();
        ((JavascriptExecutor) driver).executeScript("window.open()");
        String newWindow = wait.until((WebDriver d) -> {
            Set<String> newWindows = d.getWindowHandles();
            String window = "";
            Iterator<String> it = newWindows.iterator();
            while (it.hasNext()) {
                String currentWindow = it.next();
                if (!oldWindows.contains(currentWindow)) {
                    window = currentWindow;
                    break;
                }
            }
            return window;
        });
        driver.switchTo().window(newWindow);
        driver.get("https://10minutemail.com");
        //10. Скопировать почтовый адрес сгенерированный в 10minutemail
        wait.until(ExpectedConditions.attributeToBeNotEmpty(driver.findElement(By.id("mail_address")), "value"));
        String email = driver.findElement(By.id("mail_address")).getAttribute("value");
        //11. Вернуться в калькулятор, в поле Email ввести адрес из предыдущего пункта
        driver.switchTo().window(mainWindow);
        driver.switchTo().frame(iframeName).switchTo().frame("myFrame");
        driver.findElement(By.cssSelector("input[ng-model='emailQuote.user.email']")).sendKeys(email);
        //12. Нажать SEND EMAIL
        driver.findElement(By.cssSelector("button[aria-label='Send Email']")).click();
        //13. Дождаться письма с рассчетом стоимости и проверить что Total Estimated Monthly Cost в письме совпадает с тем, что отображается в калькуляторе
        driver.switchTo().window(newWindow);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='Google Cloud Platform Price Estimate']"))).click();
        String costMail = driver.findElement(By.xpath("//h3[contains(text(), 'USD')]")).getText();
        costMail = costMail.substring(4);
        System.out.println(costMail);
        Assertions.assertEquals(cost, costMail);

    }
}
