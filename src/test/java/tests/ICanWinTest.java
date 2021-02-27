package tests;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.Select;
import service.TestDataReader;

public class ICanWinTest extends CommonConditions {

    @Test
    public void newPaste() {
        driver.get("https://pastebin.com");
        driver.findElement(By.id("postform-text")).sendKeys(TestDataReader.getTestData("testdata.message"));
        ((JavascriptExecutor) driver).executeScript("document.getElementById(\"postform-expiration\").style.visibility='visible';");
        Select pasteExpiration = new Select(driver.findElement(By.id("postform-expiration")));
        pasteExpiration.selectByValue("10M");
        driver.findElement(By.id("postform-name")).sendKeys("helloweb");
        driver.findElement(By.cssSelector("button[type='submit']")).click();
    }
}