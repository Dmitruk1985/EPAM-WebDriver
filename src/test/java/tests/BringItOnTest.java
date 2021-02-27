package tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class BringItOnTest extends CommonConditions {
    @Test
    public void newPaste() {
        driver.get("https://pastebin.com");
        String code = "git config --global user.name  \"New Sheriff in Town\"\n" +
                "git reset $(git commit-tree HEAD^{tree} -m \"Legacy code\")\n" +
                "git push origin master --force";
        driver.findElement(By.id("postform-text")).sendKeys(code);
       /* ((JavascriptExecutor) driver).executeScript(
                "for (let i = 0; i < 4; i++) { \n" +
                "document.getElementsByClassName(\"col-sm-9 field-wrapper\")[i].style.visibility='visible';\n" +
                "}");*/
        String syntaxValue = "Bash";
        ((JavascriptExecutor) driver).executeScript("document.getElementById(\"postform-format\").style.visibility='visible';");
        Select syntaxHighlighting = new Select(driver.findElement(By.id("postform-format")));
        syntaxHighlighting.selectByVisibleText(syntaxValue);
        ((JavascriptExecutor) driver).executeScript("document.getElementById(\"postform-expiration\").style.visibility='visible';");
        Select pasteExpiration = new Select(driver.findElement(By.id("postform-expiration")));
        pasteExpiration.selectByValue("10M");
        String pasteName = "how to gain dominance among developers";
        driver.findElement(By.id("postform-name")).sendKeys(pasteName);
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.highlighted-code")));
        Assertions.assertTrue(driver.getTitle().contains(pasteName));
        Assertions.assertEquals(syntaxValue, driver.findElement(By.cssSelector("div.left a[class='btn -small h_800']")).getText());
        Assertions.assertEquals(code, driver.findElement(By.cssSelector("div.source")).getText());
    }
}
