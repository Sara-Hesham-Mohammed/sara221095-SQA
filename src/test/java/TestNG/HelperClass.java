package TestNG;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class HelperClass {
    protected WebDriverWait wait;
    protected WebDriver driver;

    public HelperClass(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Helper methods
    void loginAsPartner(String email, String password) {
        // 40 secs bc 60 is too long and there's 30 just for the manual captcha
        WebDriverWait passwordWait = new WebDriverWait(driver, Duration.ofSeconds(40));

        try {

            handleOverlay();

            // Click on "Sign in"
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[1]/div[1]/header/div/nav/div[2]/a"))).click();

            // Wait for email field and enter credentials
            WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"loginname\"]")));
            emailField.clear();
            emailField.sendKeys(email);

            // Click continue button
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"root\"]/div/div/div[2]/div[1]/div/div/div/div/div/div/div/form/div[2]/button"))).click();

            // Wait for password field and enter password
            WebElement passwordField = passwordWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"password\"]")));
            passwordField.clear();
            passwordField.sendKeys(password);

            // Click sign in button
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"root\"]/div/div/div[2]/div[1]/div/div/div/div/div/div/div/form/div/button[1]"))).click();

            //handle the captcha that'll pop up
            manualCaptcha();

            smsVerification();
            //get rid of the overlay that shows up
            handleOverlay();

            // Wait for dashboard to load
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"automation_id_hero_container\"]")));


        } catch (Exception e) {
            Assert.fail("Login failed: " + e.getMessage());
        }
    }

    private void handleOverlay(){
        //handling the overlay screen that pops up to tell you to scroll
        String path = "//*[@id=\"automation_id_overlay\"]";
        try {
            // Click anywhere on the overlay to dismiss it
            WebElement overlay = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath(path)));
            if(overlay.isDisplayed()){
                System.out.println("Overlay found, clicking to dismiss.");
            }
            // lw dh msht8lsh try the js scroller ting
            overlay.click();
            Thread.sleep(1000); // Small delay to ensure overlay is dismissed
        } catch (Exception e) {
            System.out.println("No initial overlay found or could not dismiss it: " + e.getMessage());
        }
    }

    private void manualCaptcha(){
        System.out.println("Complete the CAPTCHA manually within 30 seconds...(idk how to automate it :))");

        try {
            Thread.sleep(30000); // Pause for manual CAPTCHA completion
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void smsVerification(){
        System.out.println("Complete the sms verification steps within 30 seconds...(idk how to automate it :))");

        try {
            Thread.sleep(30000); // Pause for manual CAPTCHA completion
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }



    void selectDropdownOption(String id, String optionText) {
        WebElement dropdown = wait.until(ExpectedConditions.presenceOfElementLocated(By.id(id)));
        Select select = new Select(dropdown);
        select.selectByVisibleText(optionText);
    }

    void enterText(String elementId, String text, boolean enter) {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.id(elementId)));
        element.clear();
        element.sendKeys(text);
        if(enter){
            element.sendKeys(Keys.RETURN);
        }else{
            System.out.println("Enter key not clicked manage text another way");
        }


    }

    void clickBtn(String xpath) {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
        btn.click();
    }

    void click(String xpath){
        // click on whatever gets passed
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath))).click();

    }
    
    void addPropertySteps(String propertyName, String zipCode, String country, String city, String finalZipCode){
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"create_new_listing\"]/div/button/span/div"))).click();

        click("//*[@id=\"automation_id_choose_category_apt\"]/div");
        click("//*[@id=\"automation_id_choose_listing_type_entire_place\"]/div");
        clickBtn("//*[@id=\"automation_id_screen_container_Listing\"]/div/div[2]/div/div/div[2]/button");

        click("//*[@id=\"automation_id_property_type_228\"]/div");
        clickBtn("//*[@id=\"automation_id_screen_container_Property\"]/div/div[2]/div/div/div[2]/button");

        click("//*[@id=\"automation_id_choose_owner_type_single\"]/div");
        clickBtn("//*[@id=\"automation_id_screen_container_Owner\"]/div/div[2]/div/div/div[2]/button");

        clickBtn("//*[@id=\"automation_id_screen_container_FeedbackLoop\"]/div/div[2]/div/div/div/div[2]/button");

        click("//*[@id=\"automation_id_screen_container_OtaQuestion\"]/div/div[1]/div[2]/div/div/div[2]/div[5]/label");
        clickBtn("//*[@id=\"automation_id_screen_container_OtaQuestion\"]/div/div[2]/div/div/div[2]/button");

        enterText(":rl:", propertyName, true);
        enterText(":rs:", zipCode, false);
        click("/html/body/div[1]/div/div[2]/div[5]/div[1]/div/div[1]/div[2]/div/div/div[2]/button");

        // THE ISSUE HERE IS THAT FREAKING BOOKING CHANGES THE ID OF THE FIELDS WITH EVERY RUN????
        // IDK WHY THESE SPECIFIC FIELDS?
        // can i do a wait then get the field ids and cont running??
        selectDropdownOption(":r18:", country);
        enterText(":r1a:", city, true);
        enterText(":r2b:", finalZipCode, true);

    }
}
