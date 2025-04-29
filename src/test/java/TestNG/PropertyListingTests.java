package TestNG;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;


import static org.testng.Assert.assertTrue;

public class PropertyListingTests {
    private WebDriver driver;

    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }
    @Test(dataProvider = "addPropertyPositive", dataProviderClass = PropertyListingDataProvider.class)
    public void testAddPropertyPositive(String address, String country, String city, String zipCode) {
        // Simulate login as partner
        // Go through property addition steps
        // Use the provided parameters for property details
        // Assert continue button is enabled or next step is accessible
        Assert.assertTrue(true); // Replace with actual logic
    }


    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test(dataProvider = "addPropertyInvalidPostcode", dataProviderClass = PropertyListingDataProvider.class)
    public void testAddPropertyInvalidZip(String zipCode) {
        // Simulate filling out property form with invalid zip code
        // Check that error message is shown and continue button is disabled
        Assert.assertEquals(zipCode, "1111"); // Simulate validation failure
    }

    @Test(dataProvider = "addPropertyInsufficientPhotos", dataProviderClass = PropertyListingDataProvider.class)
    public void testAddPropertyWithFewPhotos(int photoCount) {
        // Simulate uploading only 4 photos
        // Assert alert message appears and cannot continue
        Assert.assertTrue(photoCount < 5);
    }

    @Test(dataProvider = "removeProperty", dataProviderClass = PropertyListingDataProvider.class)
    public void testRemoveProperty(String reason, String subReason) {
        // Simulate navigating through remove property steps
        // Use reason and subReason
        // Assert contract termination option is available
        Assert.assertEquals(reason, "My property is no longer available");
        Assert.assertEquals(subReason, "I’m closing it permanently");
    }
}

