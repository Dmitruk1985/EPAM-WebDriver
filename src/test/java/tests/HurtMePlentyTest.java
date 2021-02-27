package tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HurtMePlentyTest extends CommonConditions {
    @Test
    public void googleCloud() {
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
        driver.findElement(By.cssSelector("div[class='tab-holder compute']")).click();
        // 6. Заполнить форму следующими данными:
        //* Number of instances: 4
        driver.findElement(By.id("input_62")).sendKeys("4");
        /*
         * What are these instances for?: оставить пустым
         * Operating System / Software: Free: Debian, CentOS, CoreOS, Ubuntu, or other User Provided OS - Установлено по умолчанию
         * VM Class: Regular - Установлено по умолчанию
         */
        //* Instance type: n1-standard-8 (vCPUs: 8, RAM: 30 GB)
        driver.findElement(By.id("select_86")).click();
        driver.findElement(By.id("select_option_187")).click();
        driver.findElement(By.id("select_value_label_59")).click();
        driver.findElement(By.id("select_option_359")).click();
        //* Выбрать Add GPUs
        driver.findElement(By.xpath("//div[contains(text(), 'Add GPUs.')]/preceding-sibling::div")).click();
        //* Number of GPUs: 1
        driver.findElement(By.id("select_393")).click();
        driver.findElement(By.id("select_option_398")).click();
        //* GPU type: NVIDIA Tesla V100
        driver.findElement(By.id("select_395")).click();
        driver.findElement(By.id("select_option_405")).click();
        // * Local SSD: 2x375 Gb
        driver.findElement(By.id("select_value_label_100")).click();
        driver.findElement(By.id("select_option_116")).click();
        // * Datacenter location: Frankfurt (europe-west3) - Этот параметр некорректно выбирается
        driver.findElement(By.id("select_value_label_101")).click();
        driver.findElement(By.id("select_option_257")).click();
//        driver.findElement(By.xpath("//div[text() = 'Frankfurt (europe-west3)']/parent::md-option")).click();
        //* Commited usage: 1 Year
        driver.findElement(By.id("select_value_label_102")).click();
        driver.findElement(By.id("select_option_122")).click();
        //7. Нажать Add to Estimate
        driver.findElement(By.cssSelector("button[class*='md-raised']")).click();
        //8. Проверить соответствие данных следующих полей: VM Class, Instance type, Region, local SSD, commitment term
        Assertions.assertTrue(driver.findElement(By.xpath("//div[contains(text(), 'VM class')]")).getText().contains("regular"));
        Assertions.assertTrue(driver.findElement(By.xpath("//div[contains(text(), 'Instance type')]")).getText().contains("n1-standard-8"));
       //9. Проверить что сумма аренды в месяц совпадает с суммой получаемой при ручном прохождении теста.
    }
}
