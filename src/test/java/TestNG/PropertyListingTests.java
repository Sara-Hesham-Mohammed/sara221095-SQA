package TestNG;

//selenium imports

import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;

//TestNG Imports
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

import static org.testng.Assert.assertTrue;

public class PropertyListingTests {

    private WebDriver driver;
    private WebDriverWait wait;
    private HelperClass helper;

    @BeforeMethod
    public void setup() {
        String PARTNER_EMAIL = "saraelradwan@gmail.com";
        String PARTNER_PASSWORD = "Raindrop02??";
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        this.helper = new HelperClass(driver);
        WebDriverManager.chromedriver().setup();

        driver.get("https://join.booking.com/?label=gen173nr-1BCAEoggI46AdIM1gEaEOIAQGYAQm4ARjIAQ_YAQHoAQGIAgGoAgS4ArjE678GwAIB0gIkYjIxNzljN2ItOTYyOS00MzUxLWJlMWEtMWVkMmU4ODAyYzdm2AIF4AIB&sid=326076d58e597afd437860a170d81a0f&aid=304142&lang=en-gb&utm_medium=frontend&utm_source=topbar");

        //login
        helper.loginAsPartner(PARTNER_EMAIL, PARTNER_PASSWORD);
        //shows the smth went wrong page, refresh to get rid of it
    }

    // Positive and Negative test cases for adding a property: TC_1 & TC_1.1
    @Test(priority = 1, dataProvider = "propertyData", dataProviderClass = PropertyListingDataProvider.class)
    public void testAddProperty(String propertyName, String zipCode, String country, String city, String finalZipCode, boolean expectContinueEnabled) {
        helper.addPropertySteps(propertyName, zipCode, country, city, finalZipCode);
        WebElement continueBtn = driver.findElement(By.xpath("//*[@id=\"automation_id_screen_container_Address\"]/div[1]/div[2]/div/div/div[2]/button"));

        if (expectContinueEnabled) {
            Assert.assertTrue(continueBtn.isEnabled(), "Continue button should be enabled with valid details");
        } else {
            WebElement errorMessage = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//div[contains(text(),'The postcode format is invalid. Enter a valid format')]")));
            Assert.assertTrue(errorMessage.isDisplayed(), "Error message should be displayed for invalid zip code");
            Assert.assertFalse(continueBtn.isEnabled(), "Continue button should be disabled with invalid details");
        }
    }


    // First complete all valid steps until photos section
    @Test(priority = 2, dependsOnMethods = "testAddProperty")
    public void testAddPropertyWithInsufficientPhotos() {

        // Navigate to photos section (assuming this is done by clicking continue from previous steps)
        helper.clickBtn("");

        // Step 1: Navigate to Photos section


        // Step 2: Click Upload photos button
        WebElement uploadBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'Upload photos')]")));
        uploadBtn.click();

        // Step 3: Add 4 photos (implementation depends on how file upload works in the application)
        // This is a simplified version - actual implementation would need to handle file uploads
        for (int i = 1; i <= 4; i++) {
            // Assuming there's a file input field
            WebElement fileInput = driver.findElement(By.xpath("//input[@type='file']"));
            fileInput.sendKeys("/path/to/photo" + i + ".jpg");
        }

        WebElement continueBtn = driver.findElement(By.id("continue-button"));
        if (!continueBtn.isEnabled()) {
            // Verify alert message and continue button disabled (post-condition)
            WebElement alertMessage = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(text(),'Upload at least 1 more photo to continue')]")));
            Assert.assertTrue(alertMessage.isDisplayed(), "Alert message about insufficient photos should be displayed");

            Assert.assertFalse(continueBtn.isEnabled(), "Continue button should be disabled with insufficient photos");

        } else {
            System.out.println("BUTTON ENABLED. Test failed, it shouldn't be enabled. :(");
        }
    }


    @Test(priority = 3)
    public void testRemoveProperty() {
        String removalURL = "https://join.booking.com/become-a-host/host-profile.html?token=f236a3eba9084fdfebb9ea5bf48987bedef0dfa1&lang=en&waypoint_id=351178264&unit_id=865684635";
        // Go to a different URL for this specific test
        //idk if i should use .get() wla dh?
        driver.navigate().to(removalURL);

        // Step 1: Click profile icon
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[1]/div[1]/header/div/nav/div[2]/div[3]/span/div[1]"))).click();

        // Step 2: Choose property portfolio management
        wait.until(ExpectedConditions.elementToBeClickable(By.id("account-badge-item-view-properties"))).click();

        // Step 3: Click remove button on property
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'Remove')]"))).click();

        // Step 4: Click Contact us button
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'Contact us')]"))).click();

        // Step 5: Click Terminate Contract button
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'Terminate Contract')]"))).click();

        // Step 6 click on the dropdown menu
        helper.click("/html/body/div[19]/div/div[1]/aside/div/div[2]/div/div[1]/div/div");

        // Step 7: Select termination reason
        helper.click("/html/body/div[19]/div/div[1]/aside/div/div[2]/div/div[1]/div/div/select/option[2]");
        // click next
        helper.clickBtn("/html/body/div[19]/div/div[1]/aside/div/div[3]/button");

        // Step 7: Select reason for not being available
        //click drp down menu again
        helper.click("//*[@id=\"terminate_contract_subcategory_select\"]");
        //choose option
        helper.click("/html/body/div[19]/div/div[1]/aside/div/div[2]/div/div[1]/div/div/select/option[6]");

        // Step 8: confirm terms and conditions
        helper.click("/html/body/div[19]/div/div[1]/aside/div/div[2]/div/div/label/span");

        // Step 9: Click Terminate Contract button
        WebElement terminateBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[19]/div/div[1]/aside/div/div[3]/button[2]")));

        // Verify success message (post-condition)
        WebElement successMessage = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(text(),'Your request has been submitted')]")));
        if (terminateBtn.isEnabled()) {
            terminateBtn.click();
            Assert.assertTrue(successMessage.isDisplayed(), "Property removal request should be submitted successfully");
        } else {
            System.out.println("Test fail, cant remove property");
        }
    }


    @Test(priority = 4)
    public void deleteUnfinishedRegistration() throws InterruptedException {
        String accountXPath = "/html/body/div[1]/div[1]/header/div/nav/div[2]/div[3]/span/div[1]";
        String viewPropertiesBtnXPath = "//*[@id=\"account-badge-item-view-properties\"]";

        // Step 1: Click on the profile icon
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(accountXPath))).click();
        // Step 2: click on the view properties button
        driver.findElement(By.xpath(viewPropertiesBtnXPath)).click();

        //loop delete the all unfinished registrations
        for (int i = 1; i <= 7; i++) {
            System.out.println(i);
            String delBtnXPath = "/html/body/div[1]/div/div[2]/div/main/div/div[3]/div[2]/div[3]/div/table/tbody/tr[" + i + "]/td[4]/button";
            String confirmDelBtnXPath = "/html/body/div[3]/div/div/div/div/div[2]/div/button[1]";
            String popUpXPath = "/html/body/div[3]/div/div/div/div";


            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(delBtnXPath))).click();
            //wait till pop up is showing
            WebElement popUp = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(popUpXPath)));


            if (popUp.isDisplayed()) {
                System.out.println("Popup displayed, confirming delete.");
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath(confirmDelBtnXPath))).click();

                // Wait for popup to disappear
                wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(popUpXPath)));
                // Optional small wait for table to update
                Thread.sleep(1000);
            }
            else{
                System.out.println("Delete button is not displayed");
            }

            // do the assertions and condition checker hna
        }
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

}

