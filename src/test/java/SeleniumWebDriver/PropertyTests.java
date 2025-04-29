package SeleniumWebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class PropertyTests {

    public static void main(String[] args) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "D:\\ChromeDriver\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        WebDriver driver = new ChromeDriver(options);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.get("https://join.booking.com/");
        driver.manage().window().maximize();

        // LOGIN
        driver.findElement(By.name("email")).sendKeys("saraelradwan@gmail.com");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.name("password"))).sendKeys("Raindrop02??");
        driver.findElement(By.xpath("//button[@type='submit']")).click();

        // REFRESH TO GET RID OF ERROR PAGE IF NEEDED
        Thread.sleep(3000);
        driver.navigate().refresh();

        // TC_1: ADD VALID PROPERTY
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"create_new_listing\"]/div/button/span/div"))).click();
        driver.findElement(By.xpath("//*[@id=\"automation_id_choose_category_apt\"]/div")).click();
        driver.findElement(By.xpath("//*[@id=\"automation_id_choose_listing_type_entire_place\"]/div")).click();
        driver.findElement(By.xpath("//*[@id=\"automation_id_screen_container_Listing\"]/div/div[2]/div/div/div[2]/button")).click();

        driver.findElement(By.xpath("//*[@id=\"automation_id_property_type_228\"]/div")).click();
        driver.findElement(By.xpath("//*[@id=\"automation_id_screen_container_Property\"]/div/div[2]/div/div/div[2]/button")).click();

        driver.findElement(By.xpath("//*[@id=\"automation_id_choose_owner_type_single\"]/div")).click();
        driver.findElement(By.xpath("//*[@id=\"automation_id_screen_container_Owner\"]/div/div[2]/div/div/div[2]/button")).click();

        driver.findElement(By.xpath("//*[@id=\"automation_id_screen_container_FeedbackLoop\"]/div/div[2]/div/div/div/div[2]/button")).click();

        driver.findElement(By.xpath("//*[@id=\"automation_id_screen_container_OtaQuestion\"]/div/div[1]/div[2]/div/div/div[2]/div[5]/label")).click();
        driver.findElement(By.xpath("//*[@id=\"automation_id_screen_container_OtaQuestion\"]/div/div[2]/div/div/div[2]/button")).click();

        driver.findElement(By.xpath("//input[@name='propertyName']")).sendKeys("Test Property");
        driver.findElement(By.xpath("//input[@name='zip']")).sendKeys("12345");
        driver.findElement(By.xpath("//*[@id=\":r2h:\"]/div/div/button")).click();
        driver.findElement(By.name("country")).sendKeys("Egypt");
        driver.findElement(By.name("city")).sendKeys("Cairo");
        driver.findElement(By.name("postcode")).sendKeys("12345");

        WebElement continueBtn = driver.findElement(By.xpath("//*[@id=\"automation_id_screen_container_Address\"]/div[1]/div[2]/div/div/div[2]/button"));
        if (continueBtn.isEnabled()) {
            System.out.println("Continue button is enabled. Property added successfully.");
        } else {
            System.out.println("Continue button is disabled. Check input values.");
        }

        // TC_1.2: ADD INVALID PROPERTY
        continueBtn.click(); // Move to photos section
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'Upload photos')]"))).click();

        for (int i = 1; i <= 4; i++) {
            WebElement fileInput = driver.findElement(By.xpath("//input[@type='file']"));
            fileInput.sendKeys("D:\\Photos\\photo" + i + ".jpg");
        }

        WebElement photoContinueBtn = driver.findElement(By.id("continue-button"));
        if (!photoContinueBtn.isEnabled()) {
            System.out.println("Insufficient photos case passed.");
        } else {
            System.out.println("Test failed, continue button should not be enabled.");
        }

        // TC_2: REMOVE PROPERTY
        String removalURL = "https://join.booking.com/become-a-host/host-profile.html?token=f236a3eba9084fdfebb9ea5bf48987bedef0dfa1&lang=en&waypoint_id=351178264&unit_id=865684635";
        driver.navigate().to(removalURL);

        // Click profile icon
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[1]/div[1]/header/div/nav/div[2]/div[3]/span/div[1]"))).click();

        // Click Property Portfolio
        wait.until(ExpectedConditions.elementToBeClickable(By.id("account-badge-item-view-properties"))).click();

        // Click Remove
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'Remove')]"))).click();

        // Click Contact Us
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'Contact us')]"))).click();

        // Click Terminate Contract
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'Terminate Contract')]"))).click();

        // Select reason from first dropdown
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[19]/div/div[1]/aside/div/div[2]/div/div[1]/div/div"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[19]/div/div[1]/aside/div/div[2]/div/div[1]/div/div/select/option[2]"))).click();
        driver.findElement(By.xpath("/html/body/div[19]/div/div[1]/aside/div/div[3]/button")).click();

        // Select reason from second dropdown
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"terminate_contract_subcategory_select\"]"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[19]/div/div[1]/aside/div/div[2]/div/div[1]/div/div/select/option[6]"))).click();

        // Agree to terms
        driver.findElement(By.xpath("/html/body/div[19]/div/div[1]/aside/div/div[2]/div/div/label/span")).click();

        // Click Terminate button
        WebElement terminateBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[19]/div/div[1]/aside/div/div[3]/button[2]")));
        terminateBtn.click();

        // Verify confirmation message
        WebElement successMsg = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(text(),'Your request has been submitted')]")));
        if (successMsg.isDisplayed()) {
            System.out.println("Property removal request submitted successfully.");
        } else {
            System.out.println("Property removal failed.");
        }


        // EXTRA TEST CASE: delete unfinished properties
        driver.findElement(By.xpath("/html/body/div[1]/div[1]/header/div/nav/div[2]/div[3]/span/div[1]")).click();
        driver.findElement(By.xpath("//*[@id=\"account-badge-item-view-properties\"]")).click();

        for (int i = 1; i <= 7; i++) {
            String delXPath = "/html/body/div[1]/div/div[2]/div/main/div/div[3]/div[2]/div[3]/div/table/tbody/tr[" + i + "]/td[4]/button";
            try {
                WebElement deleteBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(delXPath)));
                deleteBtn.click();
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[3]/div/div/div/div/div[2]/div/button[1]"))).click();
                wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("/html/body/div[3]/div/div/div/div")));
                Thread.sleep(1000);
                System.out.println("Deleted property at row " + i);
            } catch (Exception e) {
                System.out.println("ℹRow " + i + " not found or already deleted.");
            }
        }

        driver.quit();
    }
}

